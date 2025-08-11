package com.example.or_scheduler.enums;

public enum SurgeryType {

    HEART_SURGERY(3, Machines.ECG),
    BRAIN_SURGERY(2, Machines.MRI),

    ;

    private final int hours;
    private final Machines requiredMachine;

    SurgeryType(int hours, Machines requiredMachine) {
        this.hours = hours;
        this.requiredMachine = requiredMachine;
    }

    public int getHours() {
        return hours;
    }

    public Machines getRequiredMachine() {
        return requiredMachine;
    }
}
