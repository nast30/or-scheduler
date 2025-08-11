package com.example.or_scheduler.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/operation-room")
public class OperationRoomScheduleController {

    @GetMapping("/doctor/{id}/get")
    public String getNextScheduleSlot(@PathVariable Integer id) {
            return "This is the next schedule";
    }
}