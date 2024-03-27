package com.bobi89.medicalclinic.model.entity.appointment;

import com.bobi89.medicalclinic.exception.exc.AppointmentNotQuarterException;
import com.bobi89.medicalclinic.exception.exc.DateInThePastException;
import com.bobi89.medicalclinic.model.entity.doctor.Doctor;
import com.bobi89.medicalclinic.model.entity.util.DoctorCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class AppointmentValidatorTest {

    @Test
    void validate_CorrectAppointment_DoesNotThrowException() {
        LocalDateTime startDateTime = LocalDateTime.of(2030, 12, 25, 18, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2030, 12, 25, 18, 30);

        Doctor doctor = DoctorCreator.createDoctor(1, "doctor@gmail.com");

        Assertions.assertDoesNotThrow(() -> AppointmentValidator.validate(startDateTime, endDateTime, doctor));
    }

    @Test
    void validate_DateInPast_ThrowsException() {
        LocalDateTime startDateTime = LocalDateTime.of(2020, 12, 25, 18, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2020, 12, 25, 18, 30);

        long duration = 30;
        Doctor doctor = DoctorCreator.createDoctor(1, "doctor@gmail.com");

        Assertions.assertThrows(DateInThePastException.class,
                () -> AppointmentValidator.validate(startDateTime, endDateTime, doctor));
    }

    @Test
    void validate_StartDateNotQuarter_ThrowsException() {
        LocalDateTime startDateTime = LocalDateTime.of(2030, 12, 25, 18, 1);
        LocalDateTime endDateTime = LocalDateTime.of(2030, 12, 25, 18, 31);

        long duration = 30;
        Doctor doctor = DoctorCreator.createDoctor(1, "doctor@gmail.com");

        Assertions.assertThrows(AppointmentNotQuarterException.class,
                () -> AppointmentValidator.validate(startDateTime, endDateTime, doctor),
                "Appointment's start must be rounded up to a quarter of an hour");
    }

    @Test
    void validate_EndDateNotQuarter_ThrowsException() {
        LocalDateTime startDateTime = LocalDateTime.of(2030, 12, 25, 18, 1);
        LocalDateTime endDateTime = LocalDateTime.of(2030, 12, 25, 18, 31);

        long duration = 31;
        Doctor doctor = DoctorCreator.createDoctor(1, "doctor@gmail.com");

        Assertions.assertThrows(AppointmentNotQuarterException.class,
                () -> AppointmentValidator.validate(startDateTime, endDateTime, doctor),
                "Appointment's duration must be rounded up to a quarter of an hour");
    }
}