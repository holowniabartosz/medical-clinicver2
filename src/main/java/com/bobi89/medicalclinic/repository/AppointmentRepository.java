package com.bobi89.medicalclinic.repository;

import com.bobi89.medicalclinic.model.entity.appointment.Appointment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value =
            "UPDATE appointments a " +
                    "SET a.patient_id = :patientId " +
                    "WHERE a.doctor_id = :doctorId " +
                    "AND ( " +
                    "  (a.start_date_time < :startDateTime) " +
                    "  AND (a.end_date_time > :endDateTime) " +
                    "  AND a.patient_id IS NULL " +
                    ")")
    void addPatientToAppointment(LocalDateTime startDateTime, LocalDateTime endDateTime,
                                 long patientId, long doctorId);
}
