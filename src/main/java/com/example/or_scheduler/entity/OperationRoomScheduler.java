package com.example.or_scheduler.entity;

import java.util.*;

/**
 * This object holds the whole schedule, booked rooms and the queue.
 * The queue is working in FIFO.
 * The schedule consist of a map of the room ID and all the bookings made to that room (starting from earliest)
 */
public class OperationRoomScheduler {

    private List<OperationRoom> rooms;
    private Map<Long, List<Booking>> schedule = new HashMap<>();
    private Deque<PendingBooking> queue = new ArrayDeque<>();

    public OperationRoomScheduler(List<OperationRoom> rooms) {
        this.rooms = rooms;
        for (OperationRoom room : rooms) {
            this.schedule.put(room.getId(), new ArrayList<>());
        }
    }

    public List<OperationRoom> getRooms() {
        return rooms;
    }

    public void setRooms(List<OperationRoom> rooms) {
        this.rooms = rooms;
    }

    public Map<Long, List<Booking>> getSchedule() {
        return schedule;
    }

    public void setSchedule(Map<Long, List<Booking>> schedule) {
        this.schedule = schedule;
    }

    public Deque<PendingBooking> getQueue() {
        return queue;
    }

    public void setQueue(Deque<PendingBooking> queue) {
        this.queue = queue;
    }
}
