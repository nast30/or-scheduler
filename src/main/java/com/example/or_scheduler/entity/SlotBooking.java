package com.example.or_scheduler.entity;

import java.time.LocalDateTime;

/**
 * A booked operation room will be saved as a SlotBooking
 */
public class SlotBooking {

    public int roomId;
    public LocalDateTime start;
    public LocalDateTime end;
    public Doctor doctor;

    public SlotBooking(int roomId, LocalDateTime start, LocalDateTime end, Doctor doctor) {
        this.roomId = roomId;
        this.start = start;
        this.end = end;
        this.doctor = doctor;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
