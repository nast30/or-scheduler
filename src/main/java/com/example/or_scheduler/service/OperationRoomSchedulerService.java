package com.example.or_scheduler.service;

import com.example.or_scheduler.entity.Booking;
import com.example.or_scheduler.entity.Doctor;
import com.example.or_scheduler.entity.OperationRoom;
import com.example.or_scheduler.entity.OperationRoomScheduler;
import com.example.or_scheduler.enums.Machines;
import com.example.or_scheduler.enums.SurgeryType;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

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
        list.sort(Comparator.comparing(x -> x.start));
    }



    private int checkDurationByOperatingRoom(Doctor doctor, List<Machines> machines) {
        if (doctor.getSurgeryType().equals(BRAIN_SURGERY) && machines.contains(CT)) {
            return 2;
        } else {
            return doctor.getSurgeryType().getHours();
        }
    }
}
