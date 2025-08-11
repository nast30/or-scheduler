package com.example.or_scheduler.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The response that is returned once a decision was made to book a room or to queue the request
 * When the room is booked the queuePosition will be null
 * When the request is in queue only the queuePosition has a value
 */
public class ScheduleResponse {
    public Long roomId;
    public LocalDateTime startTime;
    public Integer queuePosition;

    private ScheduleResponse(Long roomId, LocalDateTime startTime, Integer queuePosition) {
        this.roomId = roomId;
        this.startTime = startTime;
        this.queuePosition = queuePosition;
    }

    public static ScheduleResponse booked(long roomId, LocalDateTime startTime) {
        return new ScheduleResponse(roomId, startTime, null);
    }

    public static ScheduleResponse queued(int position) {
        return new ScheduleResponse(null, null, position);
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Integer getQueuePosition() {
        return queuePosition;
    }

    public void setQueuePosition(Integer queuePosition) {
        this.queuePosition = queuePosition;
    }

    @Override
    public String toString() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        if (roomId != null) {
            return String.format("BOOKED -> room=%d, start=%s", roomId, startTime.format(f));
        } else {
            return String.format("QUEUED -> position=%d", queuePosition);
        }
    }
}
