package com.bobi89.medicalclinic.service.appointment_service;

import com.bobi89.medicalclinic.model.entity.appointment.AppointmentDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {
    List<AppointmentDTO> findAll();

    AppointmentDTO findById(long id);

    AppointmentDTO addAppointmentToDoctor(LocalDateTime startDateTime, LocalDateTime endDateTime, long doctorId);

    AppointmentDTO addPatientToAppointment(long appointmentId, long patientId);
}
