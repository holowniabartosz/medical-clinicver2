package com.bobi89.medicalclinic.service;

import com.bobi89.medicalclinic.exception.exc.AppointmentConflictDateException;
import com.bobi89.medicalclinic.exception.exc.EntityNotFoundException;
import com.bobi89.medicalclinic.model.entity.appointment.Appointment;
import com.bobi89.medicalclinic.model.entity.doctor.Doctor;
import com.bobi89.medicalclinic.model.entity.mapper.AppointmentMapper;
import com.bobi89.medicalclinic.model.entity.patient.Patient;
import com.bobi89.medicalclinic.model.entity.util.AppointmentCreator;
import com.bobi89.medicalclinic.model.entity.util.DoctorCreator;
import com.bobi89.medicalclinic.model.entity.util.PatientCreator;
import com.bobi89.medicalclinic.repository.AppointmentRepository;
import com.bobi89.medicalclinic.repository.DoctorJpaRepository;
import com.bobi89.medicalclinic.repository.PatientJpaRepository;
import com.bobi89.medicalclinic.service.appointment_service.AppointmentService;
import com.bobi89.medicalclinic.service.appointment_service.AppointmentServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AppointmentServiceTest {

    private AppointmentMapper appointmentMapper;
    private AppointmentRepository appointmentRepository;
    private DoctorJpaRepository doctorJpaRepository;
    private PatientJpaRepository patientJpaRepository;
    private AppointmentService appointmentService;

    @BeforeEach
    void setup() {
        this.appointmentMapper = Mappers.getMapper(AppointmentMapper.class);
        this.appointmentRepository = Mockito.mock(AppointmentRepository.class);
        this.doctorJpaRepository = Mockito.mock(DoctorJpaRepository.class);
        this.patientJpaRepository = Mockito.mock(PatientJpaRepository.class);
        this.appointmentService = new AppointmentServiceImpl(doctorJpaRepository, appointmentMapper,
                patientJpaRepository, appointmentRepository);
    }

    @Test
    void findAll_doctorsExists_returnDoctors() {
        List<Appointment> appointments = new ArrayList<>();
        LocalDateTime startDateTime = LocalDateTime.of(2030, 12, 25, 18, 0);
        LocalDateTime startDateTime2 = LocalDateTime.of(2031, 12, 25, 18, 0);
        long duration = 30;
        long doctorId = 1;
        Appointment appointment = AppointmentCreator.createAppointment(startDateTime, duration);
        Appointment appointment2 = AppointmentCreator.createAppointment(startDateTime2, duration);
        appointments.add(appointment);
        appointments.add(appointment2);

        when(appointmentRepository.findAll())
                .thenReturn(appointments);

        var result = appointmentService.findAll();

        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(Duration.ofMinutes(duration), result.get(0).getDuration());
        Assertions.assertEquals(Duration.ofMinutes(duration), result.get(1).getDuration());
    }

    @Test
    void addAppointmentToDoctor_AppointmentAddedToDoctor_ReturnsAppointment() throws Exception {
        LocalDateTime startDateTime = LocalDateTime.of(2030, 12, 25, 18, 0);
        long duration = 30;
        long doctorId = 1;
        Doctor doctor = DoctorCreator.createDoctor(doctorId, "doctor@gmail.com");
        Appointment appointment = AppointmentCreator.createAppointment(startDateTime, duration);

        when(doctorJpaRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(appointmentRepository.save(any())).thenReturn(appointment);

        var result = appointmentService.addAppointmentToDoctor(startDateTime,
                Duration.ofMinutes(duration), doctorId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(Duration.ofMinutes(duration), result.getDuration());
        Assertions.assertEquals(1, result.getDoctorId());
    }

    @Test
    void addAppointmentToDoctor_DoctorNotFound_ThrowsException() throws Exception {
        LocalDateTime startDateTime = LocalDateTime.of(2030, 12, 25, 18, 0);
        long duration = 30;
        long doctorId = 1;

        when(doctorJpaRepository.findById(doctorId)).thenReturn(Optional.empty());

        var result = Assertions.assertThrows(EntityNotFoundException.class,
                () -> appointmentService.addAppointmentToDoctor(startDateTime,
                        Duration.ofMinutes(duration), doctorId));

        Assertions.assertEquals("Doctor not found", result.getMessage());
    }

    @Test
    void addAppointmentToDoctor_TimeslotUnavailable_ThrowsException() throws Exception {
        LocalDateTime startDateTime = LocalDateTime.of(2030, 12, 25, 18, 0);
        long duration = 30;
        long doctorId = 1;
        Doctor doctor = DoctorCreator.createDoctor(doctorId, "doctor@gmail.com");
        Appointment appointment = AppointmentCreator.createAppointment(startDateTime, duration);

        when(doctorJpaRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(appointmentRepository.checkForConflictingSlotsForDoctor(appointment.getStartDateTime(), appointment.getEndDateTime(), doctorId))
                .thenReturn(1);

        var result = Assertions.assertThrows(AppointmentConflictDateException.class,
                () -> appointmentService.addAppointmentToDoctor(startDateTime,
                        Duration.ofMinutes(duration), doctorId));

        Assertions.assertEquals("Timeslot unavailable", result.getMessage());
    }

    @Test
    void addPatientToAppointment_PatientAddedToAppointment_ReturnsAppointment() throws Exception {
        LocalDateTime startDateTime = LocalDateTime.of(2030, 12, 25, 18, 0);
        long duration = 30;
        long doctorId = 1;
        long patientId = 1;
        Patient patient = PatientCreator.createPatient(patientId, "john@gmail,com");
        Doctor doctor = DoctorCreator.createDoctor(doctorId, "doctor@gmail.com");
        Appointment appointment = AppointmentCreator.createAppointment(startDateTime, duration);
        appointment.setDoctor(doctor);
        appointment.setId(1L);

        when(doctorJpaRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(patientJpaRepository.findById(patientId)).thenReturn(Optional.of(patient));
        when(appointmentRepository.findById(appointment.getId())).thenReturn(Optional.of(appointment));

        var result = appointmentService.addPatientToAppointment(appointment.getId(), patientId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(Duration.ofMinutes(duration), result.getDuration());
        Assertions.assertEquals(1, result.getDoctorId());
    }

    @Test
    void addPatientToAppointment_PatientNotFound_ThrowsException() throws Exception {
        LocalDateTime startDateTime = LocalDateTime.of(2030, 12, 25, 18, 0);
        long duration = 30;
        long appointmentId = 1;
        long patientId = 1;
        Appointment appointment = AppointmentCreator.createAppointment(startDateTime, duration);

        when(patientJpaRepository.findById(patientId)).thenReturn(Optional.empty());
        when(appointmentRepository.findById(appointment.getId())).thenReturn(Optional.of(appointment));

        var result = Assertions.assertThrows(EntityNotFoundException.class,
                () -> appointmentService.addPatientToAppointment(appointmentId, patientId));

        Assertions.assertEquals("Patient or appointment not found", result.getMessage());
    }

    @Test
    void addPatientToAppointment_AppointmentNotFound_ThrowsException() throws Exception {
        LocalDateTime startDateTime = LocalDateTime.of(2030, 12, 25, 18, 0);
        long duration = 30;
        long appointmentId = 1;
        long patientId = 1;
        Patient patient = PatientCreator.createPatient(patientId, "john@gmail,com");
        Appointment appointment = AppointmentCreator.createAppointment(startDateTime, duration);

        when(patientJpaRepository.findById(patientId)).thenReturn(Optional.of(patient));
        when(appointmentRepository.findById(appointment.getId())).thenReturn(Optional.empty());

        var result = Assertions.assertThrows(EntityNotFoundException.class,
                () -> appointmentService.addPatientToAppointment(appointmentId, patientId));

        Assertions.assertEquals("Patient or appointment not found", result.getMessage());
    }
}