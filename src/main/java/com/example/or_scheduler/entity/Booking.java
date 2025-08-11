package com.example.or_scheduler.entity;

import java.time.LocalDateTime;

/**
 * A booked operation room will be saved as a Booking
 */
public class Booking {

    public long roomId;
    public LocalDateTime start;
    public LocalDateTime end;
    public Doctor doctor;

    public Booking(long roomId, LocalDateTime start, LocalDateTime end, Doctor doctor) {
        this.roomId = roomId;
        this.start = start;
        this.end = end;
        this.doctor = doctor;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
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

    @Override
    public String toString() {
        return "Booking{" +
                "roomId=" + roomId +
                ", start=" + start +
                ", end=" + end +
                ", doctor=" + doctor +
                '}';
    }
}
