package com.example.or_scheduler.entity;

import com.example.or_scheduler.enums.SurgeryType;

public class Doctor {

    private Long id;
    private String name;
    private SurgeryType surgeryType;

    public Doctor(String name, Long id, SurgeryType surgeryType) {
        this.name = name;
        this.id = id;
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
}
