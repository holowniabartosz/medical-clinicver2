package com.bobi89.medicalclinic.repository;

import com.bobi89.medicalclinic.model.entity.patient.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface PatientJpaRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByEmail(String email);
    Page<Patient> findAll(Pageable pageable);
}
