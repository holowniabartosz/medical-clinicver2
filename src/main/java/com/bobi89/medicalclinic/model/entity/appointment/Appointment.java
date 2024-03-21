package com.bobi89.medicalclinic.model.entity.appointment;

import com.bobi89.medicalclinic.exception.exc.DateInThePastException;
import com.bobi89.medicalclinic.model.entity.doctor.Doctor;
import com.bobi89.medicalclinic.model.entity.patient.Patient;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;
    private LocalDateTime startDateTime;
    private Duration duration;
    private LocalDateTime endDateTime;

    @ManyToOne
    private Patient patient;

    @ManyToOne
    private Doctor doctor;

    public Appointment(LocalDateTime startDateTime, int durationMinutes, Doctor doctor) {
        //time to 15
        if (startDateTime.isBefore(LocalDateTime.now())){
            throw new DateInThePastException("Appointment cannot be in the past");
        }
        if ((startDateTime.getMinute() % 15) != 0){
            this.startDateTime = startDateTime.plusMinutes(15 - (startDateTime.getMinute() % 15))
                    .truncatedTo(ChronoUnit.MINUTES);
        } else {
            this.startDateTime = startDateTime.truncatedTo(ChronoUnit.MINUTES);
        }
        // duration to 15
        if ((durationMinutes % 15) != 0){
            this.duration = Duration.ofMinutes(durationMinutes + (15 - (durationMinutes % 15)));
        }
        else {
            this.duration = Duration.ofMinutes(durationMinutes);
        }
        this.endDateTime = this.startDateTime.plus(duration).truncatedTo(ChronoUnit.MINUTES);
        this.doctor = doctor;
    }
}