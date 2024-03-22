package com.bobi89.medicalclinic.repository;

import com.bobi89.medicalclinic.model.entity.appointment.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<Appointment> findByDoctorId(long doctorId);

    @Query(nativeQuery = true, value =
            "SELECT COUNT(*) AS conflicting_appointments " +
                    "FROM appointments " +
                    "WHERE doctor_id = :doctorId " +
                    "AND ( " +
                    "  (:startDateTime BETWEEN start_date_time AND end_date_time) " +
                    "OR (:endDateTime BETWEEN start_date_time AND end_date_time) " +
                    "OR (start_date_time BETWEEN :startDateTime AND :endDateTime) " +
                    ")")
    int checkForConflictingSlotsForDoctor(LocalDateTime startDateTime, LocalDateTime endDateTime, long doctorId);
//
//    @Modifying
//    @Transactional
//    @Query(nativeQuery = true, value =
//            "UPDATE appointments a " +
//                    "SET a.patient_id = :patientId " +
//                    "WHERE a.start_date_time = :startDateTime " +
//                    "AND a.end_date_time = :endDateTime " +
//                    "AND a.doctor_id = :doctorId")
//    void addPatientToAppointment(LocalDateTime startDateTime, LocalDateTime endDateTime,
//                                 long patientId, long doctorId);
}
