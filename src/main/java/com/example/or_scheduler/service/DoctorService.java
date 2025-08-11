package com.example.or_scheduler.service;

import com.example.or_scheduler.entity.Doctor;
import com.example.or_scheduler.entity.PendingBooking;
import com.example.or_scheduler.entity.ScheduleResponse;
import com.example.or_scheduler.enums.SurgeryType;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class DoctorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DoctorService.class);

    private final OperationRoomSchedulerService operationRoomSchedulerService;

    //TODO notice the permission here
    public List<Doctor> doctors = new ArrayList<>();

    @PostConstruct
    public void initDoctorsList() {
        doctors.add(new Doctor(1L, "Meredith Grey", SurgeryType.BRAIN_SURGERY));
        doctors.add(new Doctor(2L, "Christina Young", SurgeryType.HEART_SURGERY));
    }

    public DoctorService(OperationRoomSchedulerService operationRoomSchedulerService) {
        this.operationRoomSchedulerService = operationRoomSchedulerService;
    }

    /**
     * Doctor is making a request for a room
     * @param doctorId
     * @return
     */
    public synchronized ScheduleResponse requestRoom(Long doctorId) {
        final Doctor doctor = findById(doctorId);
        // try to find a slot in the next 7 days
        Optional<Map.Entry<Long, LocalDateTime>> optSlot = operationRoomSchedulerService.findTimeByDoctor(doctor);
        if (optSlot.isPresent()) {
            long roomId = optSlot.get().getKey();
            final LocalDateTime start = optSlot.get().getValue();
            operationRoomSchedulerService.bookRoom(roomId, start, doctor);
            // TODO after booking, try to allocate queued requests (lazy dequeue)
            operationRoomSchedulerService.processQueue();
            return ScheduleResponse.booked(roomId, start);
        } else {
            // queue the request
            Deque<PendingBooking> queue = operationRoomSchedulerService.getQueueFromScheduler();
            queue.addLast(new PendingBooking(doctor, LocalDateTime.now()));
            return ScheduleResponse.queued(queue.size());
        }
    }

    /**
     * Find the doctor by id (from hardcoded data)
     * @param doctorId - doctor id
     * @return doctor object
     */
    private Doctor findById(Long doctorId) {
        final Optional<Doctor> foundDoctor = doctors.stream()
                .filter(doctor -> Objects.equals(doctor.getId(), doctorId))
                .findAny();
        return foundDoctor.orElseThrow(() -> new NoSuchElementException("Doctor with ID: " + doctorId + " not found."));
    }
}
