package com.bobi89.medicalclinic.model.entity.appointment;

import com.bobi89.medicalclinic.exception.exc.AppointmentNotQuarterException;
import com.bobi89.medicalclinic.exception.exc.DateInThePastException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class AppointmentDTOValidatorTest {

    @Test
    void validate_CorrectAppointment_DoesNotThrowException() {
        LocalDateTime startDateTime = LocalDateTime.of(2030, 12, 25, 18, 0);
        long duration = 30;

        Assertions.assertDoesNotThrow(() -> AppointmentDTOValidator.validate(startDateTime, duration));
    }

    @Test
    void validate_DateInPast_ThrowsException() {
        LocalDateTime startDateTime = LocalDateTime.of(2020, 12, 25, 18, 0);
        long duration = 30;

        Assertions.assertThrows(DateInThePastException.class,
                () -> AppointmentDTOValidator.validate(startDateTime, duration));
    }

    @Test
    void validate_StartDateNotQuarter_ThrowsException() {
        LocalDateTime startDateTime = LocalDateTime.of(2030, 12, 25, 18, 1);
        long duration = 30;

        Assertions.assertThrows(AppointmentNotQuarterException.class,
                () -> AppointmentDTOValidator.validate(startDateTime, duration),
                "Appointment's start must be rounded up to a quarter of an hour");
    }

    @Test
    void validate_DurationNotQuarter_ThrowsException() {
        LocalDateTime startDateTime = LocalDateTime.of(2030, 12, 25, 18, 0);
        long duration = 31;

        Assertions.assertThrows(AppointmentNotQuarterException.class,
                () -> AppointmentDTOValidator.validate(startDateTime, duration),
                "Appointment's duration must be rounded up to a quarter of an hour");
    }
}