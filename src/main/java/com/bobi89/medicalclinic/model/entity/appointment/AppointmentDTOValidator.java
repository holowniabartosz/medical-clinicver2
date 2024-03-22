package com.bobi89.medicalclinic.model.entity.appointment;

import com.bobi89.medicalclinic.exception.exc.AppointmentNotQuarterException;
import com.bobi89.medicalclinic.exception.exc.DateInThePastException;

import java.time.LocalDateTime;

public class AppointmentDTOValidator {

    public static void validate(LocalDateTime startDateTime, long durationMinutes) {
        if (startDateTime.isBefore(LocalDateTime.now())) {
            throw new DateInThePastException("Appointment cannot be in the past");
        }
        if ((startDateTime.getMinute() % 15) != 0) {
            throw new AppointmentNotQuarterException("Appointment's start must be rounded up to a quarter of an hour");
        }
        if ((durationMinutes % 15) != 0) {
            throw new AppointmentNotQuarterException("Appointment's duration must be rounded up to a quarter of an hour");
        }
    }
}