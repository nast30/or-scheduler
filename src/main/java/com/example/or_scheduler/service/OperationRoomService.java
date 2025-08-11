package com.example.or_scheduler.service;

import com.example.or_scheduler.entity.OperationRoom;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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





}
