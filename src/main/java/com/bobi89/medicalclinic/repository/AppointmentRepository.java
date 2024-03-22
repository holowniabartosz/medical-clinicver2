package com.bobi89.medicalclinic.repository;

import com.bobi89.medicalclinic.model.entity.appointment.Appointment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<Appointment> findByDoctorId(long doctorId);

    @Query(nativeQuery = true, value =
            "SELECT COUNT(*) AS conflicting_appointments" +
                    "FROM appointments" +
                    "WHERE doctor_id = :doctorId" +
                    "AND (start_date_time, end_date_time) OVERLAPS (:startDateTime, :endDateTime)")
    int checkForConflictingSlotsForDoctor(LocalDateTime startDateTime, LocalDateTime endDateTime, long doctorId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value =
            "INSERT INTO appointments (start_date_time, end_date_time, duration, doctor_id)" +
                    "VALUES (:startDateTime, :endDateTime, :duration, :doctorId)")
    Appointment addAppointmentToDoctor(LocalDateTime startDateTime, LocalDateTime endDateTime,
                                       Duration duration, long doctorId);

    @Query(nativeQuery = true, value =
            "SELECT COUNT(*) AS assignable_appointments " +
                    "FROM appointments a " +
                    "WHERE a.doctor_id = :doctorId " +
                    "AND (a.start_date_time <= :startDateTime) " +
                    "AND (a.end_date_time >= :endDateTime) " +
                    "AND a.patient_id IS NULL " +
                    "AND a.patient_id != :patientId")
    int checkForAvailableSlotsForPatient(LocalDateTime startDateTime, LocalDateTime endDateTime,
                                         long patientId, long doctorId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value =
            "UPDATE appointments a " +
                    "SET a.patient_id = :patientId " +
                    "WHERE a.start_date_time = :startDateTime " +
                    "AND a.end_date_time = :endDateTime " +
                    "AND a.doctor_id = :doctorId")
    void addPatientToAppointment(LocalDateTime startDateTime, LocalDateTime endDateTime,
                                 long patientId, long doctorId);
}
