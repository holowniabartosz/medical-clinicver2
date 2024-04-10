package com.bobi89.medicalclinic.repository;

import com.bobi89.medicalclinic.model.entity.appointment.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<Appointment> findByDoctorId(long doctorId);

    @Query(nativeQuery = true, value =
            "SELECT COUNT(*) AS conflicting_appointments " +
                    "FROM appointment " +
                    "WHERE doctor_id = :doctorId " +
                    "AND ( " +
                    "  (:startDateTime BETWEEN start_date_time AND end_date_time) " +
                    "OR (:endDateTime BETWEEN start_date_time AND end_date_time) " +
                    "OR (start_date_time BETWEEN :startDateTime AND :endDateTime) " +
                    ")")
    int checkForConflictingSlotsForDoctor(LocalDateTime startDateTime, LocalDateTime endDateTime, Long doctorId);

    List<Appointment> findByStartDateTimeBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);

    List<Appointment> findByPatientId(Long patientId);
}