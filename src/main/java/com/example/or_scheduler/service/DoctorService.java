package com.example.or_scheduler.service;

import com.example.or_scheduler.entity.Doctor;
import com.example.or_scheduler.enums.SurgeryType;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService {

    //TODO notice the permission here
    public List<Doctor> doctors = new ArrayList<>();

    @PostConstruct
    public void initDoctorsList() {
        doctors.add(new Doctor(1L, "Meredith Grey", SurgeryType.BRAIN_SURGERY));
        doctors.add(new Doctor(2L, "Christina Young", SurgeryType.HEART_SURGERY));
    }






}
