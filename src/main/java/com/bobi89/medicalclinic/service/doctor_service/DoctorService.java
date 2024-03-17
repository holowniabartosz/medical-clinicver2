package com.bobi89.medicalclinic.service.doctor_service;

import com.bobi89.medicalclinic.model.entity.doctor.DoctorDTO;

import java.util.List;

public interface DoctorService {
    List<DoctorDTO> findAll();

    DoctorDTO findById(long id);

    DoctorDTO save(DoctorDTO doctorDTO);
}
