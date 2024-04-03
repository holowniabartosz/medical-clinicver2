package com.bobi89.medicalclinic.service.patient_service;

import com.bobi89.medicalclinic.model.entity.patient.ChangePasswordCommand;
import com.bobi89.medicalclinic.model.entity.patient.PatientDTO;
import com.bobi89.medicalclinic.model.entity.patient.PatientDTOwithPassword;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface PatientService {

    Page<PatientDTO> findAll(Pageable pageable);

    PatientDTO findByEmail(String email);

    PatientDTO save(PatientDTOwithPassword patientDTOwithPassword);

    void deleteByEmail(String email);

    PatientDTO update(String email, PatientDTO patientDTO);

    ChangePasswordCommand editPatientPassword(String email, ChangePasswordCommand pass);

    PatientDTO findById(Long id);

    List<PatientDTO> findPatientsByDate(LocalDate date);
}
