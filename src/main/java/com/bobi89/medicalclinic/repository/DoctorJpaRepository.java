package com.bobi89.medicalclinic.repository;

import com.bobi89.medicalclinic.model.entity.doctor.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorJpaRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByEmail(String email);
}
