package com.bobi89.medicalclinic.repository;

import com.bobi89.medicalclinic.model.entity.doctor.Doctor;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

public interface DoctorJpaRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByEmail(String email);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value =
            "INSERT INTO appointments (start_date_time, end_date_time, duration, doctor_id) " +
                    "SELECT :startDateTime, :endDateTime, :duration, :doctorId " +
                    "WHERE NOT EXISTS ( " +
                    "  SELECT 1 FROM appointments WHERE doctor_id = :doctorId " +
                    "  AND ((start_date_time > :startDateTime AND end_date_time < :endDateTime) " +
                    "       OR (start_date_time > :startDateTime AND end_date_time >= :endDateTime AND start_date_time < :endDateTime) " +
                    "       OR (start_date_time <= :startDateTime AND end_date_time <= :endDateTime AND end_date_time > :startDateTime) " +
                    "       OR (start_date_time <= :startDateTime AND end_date_time >= :endDateTime)) " +
                    ")")
    void addAppointment(LocalDateTime startDateTime, LocalDateTime endDateTime, Duration duration, long doctorId);
}
