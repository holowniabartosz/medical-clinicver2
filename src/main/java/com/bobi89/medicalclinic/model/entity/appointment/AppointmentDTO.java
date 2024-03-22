package com.bobi89.medicalclinic.model.entity.appointment;

import com.bobi89.medicalclinic.exception.exc.AppointmentNotQuarterException;
import com.bobi89.medicalclinic.exception.exc.DateInThePastException;
import com.bobi89.medicalclinic.model.entity.doctor.Doctor;
import com.bobi89.medicalclinic.model.entity.patient.Patient;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
public class AppointmentDTO {

    private Long id;
    private LocalDateTime startDateTime;
    private Duration duration;
    private LocalDateTime endDateTime;

    private Patient patient;

    private Doctor doctor;

    public AppointmentDTO(LocalDateTime startDateTime, int durationMinutes, Doctor doctor) {
        if (startDateTime.isBefore(LocalDateTime.now())){
            throw new DateInThePastException("Appointment cannot be in the past");
        }
        if ((startDateTime.getMinute() % 15) != 0){
            throw new AppointmentNotQuarterException("Appointment's start must be rounded up to a quarter of an hour");
        }
        this.startDateTime = startDateTime.truncatedTo(ChronoUnit.MINUTES);
        if ((durationMinutes % 15) != 0){
            throw new AppointmentNotQuarterException("Appointment's duration must be rounded up to a quarter of an hour");
        }
        this.duration = Duration.ofMinutes(durationMinutes);
        this.endDateTime = this.startDateTime.plus(duration).truncatedTo(ChronoUnit.MINUTES);
        this.doctor = doctor;
    }
}