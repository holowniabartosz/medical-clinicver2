package com.bobi89.medicalclinic.model.entity.mapper;

import com.bobi89.medicalclinic.model.entity.patient.Patient;
import com.bobi89.medicalclinic.model.entity.patient.PatientDTO;
import com.bobi89.medicalclinic.model.entity.patient.PatientDTOwithPassword;
import com.bobi89.medicalclinic.model.entity.util.PatientCreator;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

public class PatientMapperTest {

    private PatientMapper mapper = Mappers.getMapper(PatientMapper.class);

    @Test
    void toDTO_MapsAllFields() {

        Patient patient = PatientCreator.createPatient(1, "john@gmail.com");

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
        PatientDTO patientDTO = PatientCreator.createPatientDTO(1, "john@gmail.com");

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
        PatientDTOwithPassword patientDTOWithPassword = PatientCreator.createPatientDTOwithPassword(1, "john@gmail.com");

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


}
