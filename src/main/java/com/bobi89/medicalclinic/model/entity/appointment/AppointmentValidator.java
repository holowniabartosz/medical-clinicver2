package com.bobi89.medicalclinic.model.entity.appointment;

import com.bobi89.medicalclinic.exception.exc.AppointmentNotQuarterException;
import com.bobi89.medicalclinic.exception.exc.DateInThePastException;
import com.bobi89.medicalclinic.model.entity.doctor.Doctor;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AppointmentValidator {

    public static boolean validate(LocalDateTime startDateTime, LocalDateTime endDateTime, Doctor doctor) {
        if (startDateTime.isBefore(LocalDateTime.now())) {
            throw new DateInThePastException("Appointment cannot be in the past");
        }
        if ((startDateTime.getMinute() % 15) != 0) {
            throw new AppointmentNotQuarterException("Appointment's start must be rounded up to a quarter of an hour");
        }
        if (doctor == null) {
            throw new IllegalArgumentException("Doctor cannot be null");
        }
        return true;
    }
}