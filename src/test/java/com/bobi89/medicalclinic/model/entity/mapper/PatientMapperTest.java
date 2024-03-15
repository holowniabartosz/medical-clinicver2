package com.bobi89.medicalclinic.model.entity.mapper;

import com.bobi89.medicalclinic.model.entity.Patient;
import com.bobi89.medicalclinic.model.entity.PatientDTO;
import com.bobi89.medicalclinic.model.entity.PatientDTOwithPassword;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

public class PatientMapperTest {

    private PatientMapper mapper = Mappers.getMapper(PatientMapper.class);

    @Test
    void toDTO_MapsAllFields() {

        Patient patient = generatePatient();

        PatientDTO patientDTO = mapper.toDTO(patient);

        assertEquals(patient.getEmail(), patientDTO.getEmail());
        assertEquals(patient.getFirstName(), patientDTO.getFirstName());
        assertEquals(patient.getLastName(), patientDTO.getLastName());
        assertEquals(patient.getBirthday(), patientDTO.getBirthday());
        assertEquals(patient.getBirthday(), patientDTO.getBirthday());
        assertEquals(patient.getPhoneNumber(), patientDTO.getPhoneNumber());
        assertEquals(patient.getId(), patientDTO.getId());
    }

    @Test
    void toPatient_MapsAllFields() {
        PatientDTO patientDTO = generatePatientDTO();

        Patient patient = mapper.toPatient(patientDTO);

        assertEquals(patientDTO.getEmail(), patient.getEmail());
        assertEquals(patientDTO.getFirstName(), patient.getFirstName());
        assertEquals(patientDTO.getLastName(), patient.getLastName());
        assertEquals(patientDTO.getBirthday(), patient.getBirthday());
        assertEquals(patientDTO.getBirthday(), patient.getBirthday());
        assertEquals(patientDTO.getPhoneNumber(), patient.getPhoneNumber());
        assertEquals(patientDTO.getId(), patient.getId());
    }

    @Test
    void toPatientWithPassword_MapsAllFieldsIncludingPassword() {
        PatientDTOwithPassword patientDTOWithPassword = generatePatientDTOwithPassword();

        Patient patient = mapper.toPatient(patientDTOWithPassword);

        assertEquals(patientDTOWithPassword.getEmail(), patient.getEmail());
        assertEquals(patientDTOWithPassword.getFirstName(), patient.getFirstName());
        assertEquals(patientDTOWithPassword.getLastName(), patient.getLastName());
        assertEquals(patientDTOWithPassword.getBirthday(), patient.getBirthday());
        assertEquals(patientDTOWithPassword.getBirthday(), patient.getBirthday());
        assertEquals(patientDTOWithPassword.getPhoneNumber(), patient.getPhoneNumber());
        assertEquals(patientDTOWithPassword.getPassword(), patient.getPassword());
        assertEquals(patientDTOWithPassword.getId(), patient.getId());
    }

    private static Patient generatePatient(){
        return new Patient(0,"999", "john@gmail.com","1234",
                "John", "Doe", "123456789", "25/12/2000");
    }

    private static PatientDTO generatePatientDTO(){
        return new PatientDTO(0,"999", "john@gmail.com",
                "John", "Doe", "123456789", "25/12/2000");
    }

    private static PatientDTOwithPassword generatePatientDTOwithPassword(){
        return new PatientDTOwithPassword(0,"999", "john@gmail.com",
                "John", "Doe", "123456789", "25/12/2000", "1234");
    }
}
