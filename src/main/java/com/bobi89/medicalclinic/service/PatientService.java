package com.bobi89.medicalclinic.service;

import com.bobi89.medicalclinic.model.entity.PatientDTO;

import java.util.List;

public interface PatientService {

    List<PatientDTO> findAll();

    PatientDTO findByEmail(String email);

    PatientDTO save(PatientDTO patientDTO);

    void deleteByEmail(String email);

    PatientDTO update(String email, PatientDTO patientDTO);

//    PatientDTO updateSimple(PatientDTO patientDTO);
}
