package com.example.or_scheduler.controller;

import com.example.or_scheduler.entity.ScheduleResponse;
import com.example.or_scheduler.service.DoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/operation-room")
public class OperationRoomScheduleController {

    private final DoctorService doctorService;

    public OperationRoomScheduleController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/doctor/{id}/get")
    public synchronized ResponseEntity<ScheduleResponse> getNextScheduleSlot(@PathVariable Long id) {
            return new ResponseEntity<>(doctorService.requestRoom(id), HttpStatus.OK);
    }
}