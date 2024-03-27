package com.bobi89.medicalclinic.model.entity.util;

import com.bobi89.medicalclinic.model.entity.appointment.Appointment;
import com.bobi89.medicalclinic.model.entity.appointment.AppointmentDTO;

import java.time.LocalDateTime;

public class AppointmentCreator {
    public static Appointment createAppointment(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return new Appointment(startDateTime,
                endDateTime, DoctorCreator.createDoctor(1, "doctor@gmail.com"));
    }

    public static AppointmentDTO createAppointmentDTO(LocalDateTime startDateTime, LocalDateTime endDateTime, long doctorId) {
        return new AppointmentDTO(startDateTime, endDateTime, doctorId);
    }
}
