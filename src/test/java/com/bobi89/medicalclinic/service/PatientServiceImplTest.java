package com.bobi89.medicalclinic.service;

import com.bobi89.medicalclinic.exception.exc.EntityNotFoundException;
import com.bobi89.medicalclinic.exception.exc.EntityWithThisEmailExistsException;
import com.bobi89.medicalclinic.exception.exc.IncorrectOldPasswordException;
import com.bobi89.medicalclinic.model.entity.mapper.AppointmentMapper;
import com.bobi89.medicalclinic.model.entity.mapper.PatientMapper;
import com.bobi89.medicalclinic.model.entity.patient.ChangePasswordCommand;
import com.bobi89.medicalclinic.model.entity.patient.Patient;
import com.bobi89.medicalclinic.model.entity.patient.PatientDTO;
import com.bobi89.medicalclinic.model.entity.patient.PatientDTOwithPassword;
import com.bobi89.medicalclinic.model.entity.util.PatientCreator;
import com.bobi89.medicalclinic.repository.AppointmentRepository;
import com.bobi89.medicalclinic.repository.PatientJpaRepository;
import com.bobi89.medicalclinic.service.patient.PatientService;
import com.bobi89.medicalclinic.service.patient.PatientServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PatientServiceImplTest {

    private PatientMapper patientMapper;
    private AppointmentMapper appointmentMapper;
    private PatientJpaRepository patientJpaRepository;
    private PatientService patientService;
    private AppointmentRepository appointmentRepository;


    @BeforeEach
    void setup() {
        this.patientJpaRepository = Mockito.mock(PatientJpaRepository.class);
        this.patientMapper = Mappers.getMapper(PatientMapper.class);
        this.appointmentMapper = Mappers.getMapper(AppointmentMapper.class);
        this.appointmentRepository = Mockito.mock(AppointmentRepository.class);
        this.patientService = new PatientServiceImpl(patientJpaRepository, patientMapper, appointmentMapper,
    appointmentRepository);
    }

    @Test
    void findAll_patientsExists_returnPatients() {
        // given
        List<Patient> patients = new ArrayList<>();
        Patient patient = PatientCreator.createPatient(1, "john@gmail.com");
        Patient patient2 = PatientCreator.createPatient(2, "john@gmail.com1");
        patients.add(patient);
        patients.add(patient2);
        int pageSize = 2;
        Pageable pageable = PageRequest.of(0, pageSize);
        Page<Patient> patientPage = new PageImpl<>(patients, pageable, patients.size());

        when(patientJpaRepository.findAll(pageable))
                .thenReturn(patientPage);

        // when
        var result = patientService.findAll(pageable);

        // then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(pageSize, result.getSize());
        Assertions.assertEquals("john@gmail.com1", result.getContent().get(1).getEmail());
    }

    @Test
    void findByEmail_PatientExists_PatientReturned() {
        //given
        Patient patient = PatientCreator.createPatient(1, "john@gmail.com");

        when(patientJpaRepository.findByEmail("john@gmail.com"))
                .thenReturn(Optional.of(patient));

        //when
        var result = patientService.findByEmail("john@gmail.com");

        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals("john@gmail.com", result.getEmail());
        Assertions.assertEquals("John", result.getFirstName());
    }

    @Test
    void save_IfUniqueEmail_PatientReturned() {
        //given
        Patient patient = PatientCreator.createPatient(1, "john@gmail.com");
        PatientDTOwithPassword patientDTOwithPassword =
                PatientCreator.createPatientDTOwithPassword(1, "john@gmail.com");

        when(patientJpaRepository.findByEmail("john@gmail.com")).thenReturn(Optional.empty());
        when(patientJpaRepository.save(patient)).thenReturn(patient);

        //when
        PatientDTO updatedPatient = patientService.save(patientDTOwithPassword);

        //then
        Assertions.assertNotNull(updatedPatient);
        Assertions.assertEquals("john@gmail.com", updatedPatient.getEmail());
        Assertions.assertEquals("John", updatedPatient.getFirstName());
        Assertions.assertEquals("Doe", updatedPatient.getLastName());
        Assertions.assertEquals("123456789", updatedPatient.getPhoneNumber());
        Assertions.assertEquals("999", updatedPatient.getIdCardNr());
        Assertions.assertEquals(1, updatedPatient.getId());
    }

    @Test
    void save_IfEmailExists_ExceptionThrown() {
        //given
        Patient patient = PatientCreator.createPatient(1, "john@gmail.com");
        PatientDTOwithPassword patientDTOwithPassword =
                PatientCreator.createPatientDTOwithPassword(1, "john@gmail.com");

        when(patientJpaRepository.findByEmail("john@gmail.com")).thenReturn(Optional.of((patient)));

        //then (when)
        var result = Assertions.assertThrows(EntityWithThisEmailExistsException.class,
                () -> patientService.save(patientDTOwithPassword));

        Assertions.assertEquals("Patient is already in the database", result.getMessage());
    }

    @Test
    void deleteByEmail_ifExists_PatientDeleted() {
        //given
        Patient patient = PatientCreator.createPatient(1, "john@gmail.com");

        when(patientJpaRepository.findByEmail("john@gmail.com")).thenReturn(Optional.of(patient));

        //then
        Assertions.assertDoesNotThrow(() ->
                patientService.deleteByEmail("john@gmail.com"));

        verify(patientJpaRepository).delete(any());
    }

    @Test
    void deleteByEmail_IfNotExist_ExceptionThrown() {
        //given
        when(patientJpaRepository.findByEmail("john@gmail.com")).thenReturn(Optional.empty());

        //then
        var result = Assertions.assertThrows(EntityNotFoundException.class,
                () -> patientService.deleteByEmail("john@gmail.com"));

        Assertions.assertEquals("No such patient in the database", result.getMessage());
    }

    @Test
    void update_PatientExists_PatientReturned() {
        Patient patient = PatientCreator.createPatient(1, "john@gmail.com");
        PatientDTO patientDTO = PatientCreator.createPatientDTO(1, "john@gmail.com");

        when(patientJpaRepository.findByEmail("john@gmail.com"))
                .thenReturn(Optional.of(patient));

        PatientDTO updatedPatient = patientService.update("john@gmail.com", patientDTO);

        //then
        Assertions.assertNotNull(updatedPatient);
        Assertions.assertEquals("john@gmail.com", updatedPatient.getEmail());
        Assertions.assertEquals("John", updatedPatient.getFirstName());
        Assertions.assertEquals("Doe", updatedPatient.getLastName());
        Assertions.assertEquals("123456789", updatedPatient.getPhoneNumber());
        Assertions.assertEquals("999", updatedPatient.getIdCardNr());
        Assertions.assertEquals(1, updatedPatient.getId());
    }

    @Test
    void update_PatientNotExist_ExceptionThrown() {
        PatientDTO patientDTO = PatientCreator.createPatientDTO(1, "john@gmail.com");

        when(patientJpaRepository.findByEmail("john@gmail.com"))
                .thenReturn(Optional.empty());

        var result = Assertions.assertThrows(EntityNotFoundException.class,
                () -> patientService.update("john@gmail.com", patientDTO));

        Assertions.assertEquals("Patient not found", result.getMessage());
    }

    @Test
    void editPatientPassword_PatientNotExist_ThrowException() {
        ChangePasswordCommand pass = new ChangePasswordCommand("1234", "4321");

        when(patientJpaRepository.findByEmail("john@gmail.com"))
                .thenReturn(Optional.empty());

        var result = Assertions.assertThrows(EntityNotFoundException.class,
                () -> patientService.editPatientPassword("john@gmail.com", pass));

        Assertions.assertEquals("No such patient in the database", result.getMessage());
    }

    @Test
    void editPatientPassword_WrongOldPassword_ThrowException() {
        //given
        ChangePasswordCommand pass = new ChangePasswordCommand("51234", "4321");
        Patient patient = PatientCreator.createPatient(1, "john@gmail.com");

        when(patientJpaRepository.findByEmail("john@gmail.com"))
                .thenReturn(Optional.of(patient));

        //then (when)
        var result = Assertions.assertThrows(IncorrectOldPasswordException.class,
                () -> patientService.editPatientPassword("john@gmail.com", pass));

        Assertions.assertEquals("Old password does not match", result.getMessage());
    }

    @Test
    void editPatientPassword_CorrectPassword_ReturnPass() {
        //given
        ChangePasswordCommand pass = new ChangePasswordCommand("1234", "4321");
        Patient patient = PatientCreator.createPatient(1, "john@gmail.com");

        when(patientJpaRepository.findByEmail("john@gmail.com"))
                .thenReturn(Optional.of(patient));

        //then (when)
        Assertions.assertDoesNotThrow(() -> patientService.editPatientPassword("john@gmail.com", pass));
        Assertions.assertEquals(pass.getNewPassword(), patient.getPassword());
    }
}