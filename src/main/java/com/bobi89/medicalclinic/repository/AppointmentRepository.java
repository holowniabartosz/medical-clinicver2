package com.bobi89.medicalclinic.repository;

import com.bobi89.medicalclinic.model.entity.appointment.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
