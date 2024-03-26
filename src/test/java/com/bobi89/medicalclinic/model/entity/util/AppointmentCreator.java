package com.bobi89.medicalclinic.model.entity.util;

import com.bobi89.medicalclinic.model.entity.appointment.Appointment;
import com.bobi89.medicalclinic.model.entity.appointment.AppointmentDTO;

import java.time.LocalDateTime;

public class AppointmentCreator {
    public static Appointment createAppointment(LocalDateTime startDateTime, long durationMinutes) {
        return new Appointment(startDateTime,
                durationMinutes, DoctorCreator.createDoctor(1, "doctor@gmail.com"));
    }

    public static AppointmentDTO createAppointmentDTO(LocalDateTime startDateTime, long durationMinutes, long doctorId) {
        return new AppointmentDTO(startDateTime, durationMinutes, doctorId);
    }
}
