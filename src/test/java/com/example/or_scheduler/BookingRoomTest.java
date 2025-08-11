package com.example.or_scheduler;

import com.example.or_scheduler.entity.Doctor;
import com.example.or_scheduler.entity.OperationRoom;
import com.example.or_scheduler.entity.OperationRoomScheduler;
import com.example.or_scheduler.entity.ScheduleResponse;
import com.example.or_scheduler.enums.SurgeryType;
import com.example.or_scheduler.service.DoctorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.example.or_scheduler.enums.Machines.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookingRoomTest {

    @Autowired
    DoctorService doctorService;

    @Test
    void testBookingAvailableRoom() {
        List<OperationRoom> rooms = Arrays.asList(
                new OperationRoom(1L, "one", List.of(MRI, CT, ECG)),
                new OperationRoom(2L, "two",List.of(MRI, CT))
        );

        Doctor doctor = new Doctor(1L,"Dr. Heart", SurgeryType.HEART_SURGERY);

        OperationRoomScheduler operationRoomScheduler = new OperationRoomScheduler(rooms);
        ScheduleResponse response = doctorService.requestRoom(doctor.getId());

        assertNotNull(response.getRoomId(), "Expected a booked room, but got queued");
        assertNotNull(response.getStartTime(), "Expected a start time, but got null");
        assertNull(response.getQueuePosition(), "Queue position should be null for a booking");

        OperationRoom bookedRoom = rooms.stream()
                .filter(room -> Objects.equals(room.getId(), response.getRoomId()))
                .findFirst()
                .orElseThrow();
        assertTrue(bookedRoom.getMachinesList().contains(ECG),
                "Assigned room needs to have ECG");

        int hour = response.getStartTime().getHour();
        assertTrue(hour >= 10 && hour < 18,
                "Booking should start during working hours (10:00-18:00)");

        System.out.println("Test booking: " + response.getRoomId() + " at " + response.getStartTime());
    }
}
