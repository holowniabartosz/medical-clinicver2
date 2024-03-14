package com.bobi89.medicalclinic.service;

import com.bobi89.medicalclinic.exception.exc.PatientNotFoundException;
import com.bobi89.medicalclinic.exception.exc.PatientWithThisEmailExistsException;
import com.bobi89.medicalclinic.model.entity.ChangePasswordCommand;
import com.bobi89.medicalclinic.model.entity.Patient;
import com.bobi89.medicalclinic.model.entity.PatientDTO;
import com.bobi89.medicalclinic.model.entity.PatientDTOwithPassword;
import com.bobi89.medicalclinic.model.entity.mapper.PatientMapper;
import com.bobi89.medicalclinic.repository.PatientJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

class PatientServiceImplTest {

    private PatientMapper patientMapper;
    private PatientJpaRepository patientJpaRepository;
    private PatientService patientService;


    @BeforeEach
    void setup() {
        this.patientJpaRepository = Mockito.mock(PatientJpaRepository.class);
        this.patientMapper = Mappers.getMapper(PatientMapper.class);
        this.patientService = new PatientServiceImpl(patientJpaRepository, patientMapper);
    }

    @Test
    void findAll_patientsExists_returnPatients() {
        //given
        List<Patient> patients = new ArrayList<>();
        Patient patient = generatePatient();
        Patient patient2 = new Patient("991", "john@gmail.com1", "12341",
                "John1", "Doe1", "1234567891", "25/12/20001");
        patients.add(patient);
        patients.add(patient2);

        when(patientJpaRepository.findAll())
                .thenReturn(patients);

        //when
        var result = patientService.findAll();

        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("john@gmail.com", result.get(0).getEmail());
        Assertions.assertEquals("john@gmail.com1", result.get(1).getEmail());
    }

    @Test
    void findByEmail_PatientExists_PatientReturned() {
        //given
        Patient patient = generatePatient();

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
        Patient patient = generatePatient();
        PatientDTOwithPassword patientDTOwithPassword = generatePatientDTOwithPassword();



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
        Assertions.assertEquals("25/12/2000", updatedPatient.getBirthday());
        Assertions.assertEquals("999", updatedPatient.getIdCardNr());
    }

    @Test
    void save_IfEmailExists_ExceptionThrown() {
        //given
        Patient patient = generatePatient();
        PatientDTOwithPassword patientDTOwithPassword = generatePatientDTOwithPassword();

        when(patientJpaRepository.findByEmail("john@gmail.com")).thenReturn(Optional.of((patient)));

        //then (when??)
        Assertions.assertThrows(PatientWithThisEmailExistsException.class,
                () -> patientService.save(patientDTOwithPassword));
    }

    @Test
    void deleteByEmail_ifExists_PatientDeleted() {
        //given
        Patient patient = generatePatient();

        when(patientJpaRepository.findByEmail("john@gmail.com")).thenReturn(Optional.of(patient));

        //then
        Assertions.assertDoesNotThrow(() ->
        patientService.deleteByEmail("john@gmail.com"));
    }

    @Test
    void deleteByEmail_IfNotExist_ExceptionThrown() {
        //given
        Patient patient = generatePatient();

        when(patientJpaRepository.findByEmail("john@gmail.com")).thenReturn(Optional.empty());

        //then
        Assertions.assertThrows(PatientNotFoundException.class,
                () -> patientService.deleteByEmail("john@gmail.com"));
    }

    @Test
    void update_PatientExists_PatientReturned() {
        Patient patient = generatePatient();
        PatientDTO patientDTO = generatePatientDTO();

        when(patientJpaRepository.findByEmail("john@gmail.com"))
                .thenReturn(Optional.of(patient));

        PatientDTO updatedPatient = patientService.update("john@gmail.com", patientDTO);

        //then
        Assertions.assertNotNull(updatedPatient);
        Assertions.assertEquals("john@gmail.com", updatedPatient.getEmail());
        Assertions.assertEquals("John", updatedPatient.getFirstName());
        Assertions.assertEquals("Doe", updatedPatient.getLastName());
        Assertions.assertEquals("123456789", updatedPatient.getPhoneNumber());
        Assertions.assertEquals("25/12/2000", updatedPatient.getBirthday());
        Assertions.assertEquals("999", updatedPatient.getIdCardNr());
    }

    @Test
    void update_PatientNotExist_ExceptionThrown() {
        PatientDTO patientDTO = generatePatientDTO();

        when(patientJpaRepository.findByEmail("john@gmail.com"))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(PatientNotFoundException.class,
                () -> patientService.update("john@gmail.com", patientDTO));
    }

    @Test
    void editPatientPassword() {
        ChangePasswordCommand pass = new ChangePasswordCommand("1234", "4321");

    }

    @Test
    void validateIfNull() {
    }

    private Patient generatePatient(){
        return new Patient("999", "john@gmail.com","1234",
                "John", "Doe", "123456789", "25/12/2000");
    }

    private PatientDTO generatePatientDTO(){
        return new PatientDTO("999", "john@gmail.com",
                "John", "Doe", "123456789", "25/12/2000");
    }

    private PatientDTOwithPassword generatePatientDTOwithPassword(){
        return new PatientDTOwithPassword("999", "john@gmail.com",
                "John", "Doe", "123456789", "25/12/2000", "1234");
    }
}