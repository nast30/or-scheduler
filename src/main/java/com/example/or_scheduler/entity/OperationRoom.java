package com.example.or_scheduler.entity;

import com.example.or_scheduler.enums.Machines;

import java.util.List;

public class OperationRoom {

    private Long id;
    private String name;
    private List<Machines> machinesList;

    public OperationRoom(Long id, String name, List<Machines> machinesList) {
        this.id = id;
        this.name = name;
        this.machinesList = machinesList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Machines> getMachinesList() {
        return machinesList;
    }

    public void setMachinesList(List<Machines> machinesList) {
        this.machinesList = machinesList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
