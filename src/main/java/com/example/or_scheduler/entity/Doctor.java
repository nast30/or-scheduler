package com.example.or_scheduler.entity;

import com.example.or_scheduler.enums.SurgeryType;

public class Doctor {

    private Long id;
    private String name;
    private SurgeryType surgeryType;

    public Doctor(Long id, String name, SurgeryType surgeryType) {
        this.id = id;
        this.name = name;
        this.surgeryType = surgeryType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SurgeryType getSurgeryType() {
        return surgeryType;
    }

    public void setSurgeryType(SurgeryType surgeryType) {
        this.surgeryType = surgeryType;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surgeryType=" + surgeryType +
                '}';
    }
}
