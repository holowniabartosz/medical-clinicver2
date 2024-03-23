package com.bobi89.medicalclinic.service.appointment_service;

import com.bobi89.medicalclinic.exception.exc.AppointmentConflictDateException;
import com.bobi89.medicalclinic.exception.exc.EntityNotFoundException;
import com.bobi89.medicalclinic.model.entity.appointment.Appointment;
import com.bobi89.medicalclinic.model.entity.appointment.AppointmentDTO;
import com.bobi89.medicalclinic.model.entity.mapper.AppointmentMapper;
import com.bobi89.medicalclinic.repository.AppointmentRepository;
import com.bobi89.medicalclinic.repository.DoctorJpaRepository;
import com.bobi89.medicalclinic.repository.PatientJpaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private DoctorJpaRepository doctorJpaRepository;
    private AppointmentMapper appointmentMapper;
    private PatientJpaRepository patientJpaRepository;
    private AppointmentRepository appointmentRepository;

    @Override
    public List<AppointmentDTO> findAll() {
        return appointmentRepository.findAll().stream()
                .map(d -> appointmentMapper.toDTO(d))
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentDTO findById(long id) {
        var appointment = appointmentRepository.findById(id);
        if (appointment.isEmpty()) {
            throw new EntityNotFoundException("No such appointment in the database");
        } else {
            return appointmentMapper.toDTO(appointment.get());
        }
    }

    @Transactional
    @Override
    public AppointmentDTO addAppointmentToDoctor(LocalDateTime startDateTime, Duration durationMinutes, long doctorId) {
        var doctor = doctorJpaRepository.findById(doctorId);
        if (doctor.isEmpty()) {
            throw new EntityNotFoundException("Doctor not found");
        }
        var appointment = new Appointment(startDateTime, durationMinutes.toMinutes(), doctor.get());
        if (checkForConflictingSlots(appointment.getStartDateTime(), appointment.getEndDateTime(), doctor.get().getId()) != 0) {
            throw new AppointmentConflictDateException("Timeslot unavailable");
        }
        return appointmentMapper.toDTO(appointmentRepository.save(appointment));
    }

    @Transactional
    @Override
    public AppointmentDTO addPatientToAppointment(long appointmentId, long patientId) {
        var patient = patientJpaRepository.findById(patientId);
        var appointment = appointmentRepository.findById(appointmentId);
        if (patient.isEmpty() || appointment.isEmpty()) {
            throw new EntityNotFoundException("Patient or appointment not found");
        }
        appointment.get().setPatient(patient.get());
        return appointmentMapper.toDTO(appointmentRepository.findById(appointmentId).get());
    }

    private int checkForConflictingSlots(LocalDateTime startDateTime, LocalDateTime endDateTime, long doctorId) {
        return appointmentRepository.checkForConflictingSlotsForDoctor(startDateTime, endDateTime, doctorId);
    }
}

