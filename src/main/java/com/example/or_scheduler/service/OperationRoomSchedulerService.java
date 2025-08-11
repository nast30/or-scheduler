package com.example.or_scheduler.service;

import com.example.or_scheduler.entity.*;
import com.example.or_scheduler.enums.Machines;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static com.example.or_scheduler.enums.Machines.CT;
import static com.example.or_scheduler.enums.SurgeryType.BRAIN_SURGERY;

@Service
public class OperationRoomSchedulerService {

    private static final LocalTime WORK_START = LocalTime.of(10, 0);
    private static final LocalTime WORK_END = LocalTime.of(18, 0);
    private static final int MAX_DAYS_AHEAD = 7;

    private final OperationRoomService operationRoomService;
    private final OperationRoomScheduler operationRoomScheduler;

    public OperationRoomSchedulerService(OperationRoomService operationRoomService) {
        this.operationRoomService = operationRoomService;
        this.operationRoomScheduler = new OperationRoomScheduler(operationRoomService.operationRooms);
    }

    /**
     * Find the time in the schedule by the doctor requirements and length of surgery
     * @param doctor
     * @return the place in the schedule that can be booked
     */
    public Optional<Map.Entry<Long, LocalDateTime>> findTimeByDoctor(Doctor doctor) {
        final Machines requiredByDoctor = doctor.getSurgeryType().getRequiredMachine();
        final LocalDateTime today = LocalDateTime.now();

        for (int day = 0; day < MAX_DAYS_AHEAD; day++) {
            final LocalDateTime date = today.plusDays(day);

            for (OperationRoom room : operationRoomService.operationRooms) {
                final List<Machines> machinesByRoom = room.getMachinesList();
                //if the room doesn't have the equipment skip it
                if (!machinesByRoom.contains(requiredByDoctor)) continue;

                int roomDuration = checkDurationByOperatingRoom(doctor, machinesByRoom);
                final LocalDateTime earliest = LocalDateTime.of(LocalDate.from(date), WORK_START);
                final LocalDateTime latestStartAllowed = LocalDateTime.of(LocalDate.from(date), WORK_END).minusHours(roomDuration);

                if (earliest.isAfter(latestStartAllowed)) {
                    // the duration doesn't match the surgery
                    continue;
                }

                // get bookings for this room on that date
                final List<Booking> roomBookings = operationRoomScheduler.getSchedule().get(room.getId()).stream()
                        .filter(booking -> booking.getStart().equals(date))
                        .sorted(Comparator.comparing(Booking::getStart))
                        .toList();

                // if no bookings, return earliest
                if (roomBookings.isEmpty()) {
                    return Optional.of(new AbstractMap.SimpleEntry<>(room.getId(), earliest));
                }

                // if there's a booking on this room already we need to check if we can schedule it
                final Booking first = roomBookings.getFirst();
                if (!first.getStart().isBefore(earliest.plusHours(roomDuration))) {
                    return Optional.of(new AbstractMap.SimpleEntry<>(room.getId(), earliest));
                }

                for (int i = 0; i < roomBookings.size() - 1; i++) {
                    LocalDateTime gapStart = roomBookings.get(i).getEnd(); //the end of the first booking
                    LocalDateTime gapEnd   = roomBookings.get(i + 1).getStart(); //the start of the second booking
                    LocalDateTime candidate = alignToHour(gapStart);
                    if (candidate.isBefore(gapStart)) candidate = candidate.plusHours(1);

                    if (!candidate.isBefore(LocalDateTime.of(LocalDate.from(date), WORK_START)))
                        if (!candidate.plusHours(roomDuration).isAfter(gapEnd)) {
                            // fits between bookings
                            return Optional.of(new AbstractMap.SimpleEntry<>(room.getId(), candidate));
                        }
                }

                //TODO
                // check after last booking until work end
                final Booking last = roomBookings.getLast();
                LocalDateTime candidate = alignToHour(last.getEnd());
                if (candidate.isBefore(last.getEnd())) candidate = candidate.plusHours(1);
                if (!candidate.isBefore(LocalDateTime.of(LocalDate.from(date), WORK_START)) &&
                        !candidate.plusHours(roomDuration).isAfter(LocalDateTime.of(LocalDate.from(date), WORK_END))) {
                    return Optional.of(new AbstractMap.SimpleEntry<>(room.getId(), candidate));
                }

                // otherwise, this room/day doesn't have a slot
            }
        }

        // no room was found to match requirements
        return Optional.empty();
    }

    /**
     * Book an OR as the schedule is free for that time
     *
     * @param roomId
     * @param start
     * @param doctor
     */
    public void bookRoom(long roomId, LocalDateTime start, Doctor doctor) {
        final OperationRoom room = operationRoomService.findById(roomId);
        final int duration = checkDurationByOperatingRoom(doctor, room.getMachinesList());
        final LocalDateTime end = start.plusHours(duration);
        final Booking booking = new Booking(roomId, start, end, doctor);

        List<Booking> list = operationRoomScheduler.getSchedule().get(roomId);
        list.add(booking);
        // schedule sorted from earliest
        list.sort(Comparator.comparing(Booking::getStart));
    }

    /**
     * Try to move the queue if there are any slots open in the queue
     */
    public void processQueue() {
        // iterate until we can no longer fulfill the head
        boolean progressed;
        do {
            Deque<PendingBooking> queue = operationRoomScheduler.getQueue();
            progressed = false;
            if (queue.isEmpty()) return;
            PendingBooking head = queue.peekFirst();
            Optional<Map.Entry<Long, LocalDateTime>> opt = findTimeByDoctor(head.doctor);
            if (opt.isPresent()) {
                final long roomId = opt.get().getKey();
                final LocalDateTime start = opt.get().getValue();
                bookRoom(roomId, start, head.doctor);
                queue.pollFirst(); // remove from queue
                progressed = true;
            }
        } while (progressed);
    }

    public Deque<PendingBooking> getQueueFromScheduler() {
        return operationRoomScheduler.getQueue();
    }

    private int checkDurationByOperatingRoom(Doctor doctor, List<Machines> machines) {
        if (doctor.getSurgeryType().equals(BRAIN_SURGERY) && machines.contains(CT)) {
            return 2;
        } else {
            return doctor.getSurgeryType().getHours();
        }
    }

    private LocalDateTime alignToHour(LocalDateTime dateTime) {
        return dateTime.withMinute(0).withSecond(0).withNano(0);
    }
}
