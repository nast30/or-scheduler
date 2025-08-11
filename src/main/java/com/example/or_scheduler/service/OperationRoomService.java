package com.example.or_scheduler.service;

import com.example.or_scheduler.entity.Doctor;
import com.example.or_scheduler.entity.OperationRoom;
import com.example.or_scheduler.entity.OperationRoomScheduler;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.example.or_scheduler.enums.Machines.*;

@Service
public class OperationRoomService {

    public List<OperationRoom> operationRooms = new ArrayList<>();

    @PostConstruct
    public void initDoctorsList() {
        operationRooms.add(new OperationRoom(1L, "One", List.of(MRI, CT, ECG)));
        operationRooms.add(new OperationRoom(2L, "Two", List.of(MRI, CT)));
        operationRooms.add(new OperationRoom(3L, "Three", List.of(MRI, CT)));
        operationRooms.add(new OperationRoom(4L, "Four", List.of(MRI, ECG)));
        operationRooms.add(new OperationRoom(5L, "Five", List.of(MRI, ECG)));
    }




    /**
     * Find the room by id (from hardcoded data)
     * @param roomId - room id
     * @return room object
     */
    public OperationRoom findById(Long roomId) {
        final Optional<OperationRoom> foundDoctor = operationRooms.stream()
                .filter(room -> Objects.equals(room.getId(), roomId))
                .findAny();
        return foundDoctor.orElseThrow(() -> new NoSuchElementException("Doctor with ID: " + roomId + " not found."));
    }
}
