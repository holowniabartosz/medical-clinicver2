package com.bobi89.medicalclinic.repository;

import com.bobi89.medicalclinic.model.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientJpaRepository extends JpaRepository<Patient, String> {
    Optional<Patient> findByEmail(String email);
}
