package com.example.or_scheduler.entity;

import java.time.LocalDateTime;

/**
 * This obj will be in the queue of the scheduler with the doctor that made the request and the time.
 */
public class PendingBooking {
    public Doctor doctor;
    public LocalDateTime requestTime;

    public PendingBooking(Doctor doctor, LocalDateTime requestTime) {
        this.doctor = doctor;
        this.requestTime = requestTime;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(LocalDateTime requestTime) {
        this.requestTime = requestTime;
    }
}
