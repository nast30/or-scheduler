package com.example.or_scheduler.entity;

public class Hospital {

    private String name;
    private Integer numOperatingRooms;

    public Hospital(Integer numOperatingRooms, String name) {
        this.numOperatingRooms = numOperatingRooms;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumOperatingRooms() {
        return numOperatingRooms;
    }

    public void setNumOperatingRooms(Integer numOperatingRooms) {
        this.numOperatingRooms = numOperatingRooms;
    }
}
