package com.bobi89.medicalclinic.service;

import com.bobi89.medicalclinic.model.entity.ChangePasswordCommand;
import com.bobi89.medicalclinic.model.entity.PatientDTO;
import com.bobi89.medicalclinic.model.entity.PatientDTOwithPassword;

import java.util.List;

public interface PatientService {

    List<PatientDTO> findAll();

    PatientDTO findByEmail(String email);

    PatientDTO save(PatientDTOwithPassword patientDTOwithPassword);

    void deleteByEmail(String email);

    PatientDTO update(String email, PatientDTO patientDTO);

    ChangePasswordCommand editPatientPassword(String email, ChangePasswordCommand pass);
}
