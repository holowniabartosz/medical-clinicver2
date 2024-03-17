package com.bobi89.medicalclinic.controller;

import com.bobi89.medicalclinic.exception.exc.EntityNotFoundException;
import com.bobi89.medicalclinic.exception.exc.EntityNullFieldsException;
import com.bobi89.medicalclinic.exception.exc.EntityWithThisEmailExistsException;
import com.bobi89.medicalclinic.model.entity.patient.ChangePasswordCommand;
import com.bobi89.medicalclinic.model.entity.patient.PatientDTO;
import com.bobi89.medicalclinic.model.entity.patient.PatientDTOwithPassword;
import com.bobi89.medicalclinic.model.entity.util.PatientCreator;
import com.bobi89.medicalclinic.service.patient_service.PatientServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class PatientControllerTest {

    @MockBean
    private PatientServiceImpl patientServiceImpl;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        List<PatientDTO> patients = new ArrayList<>();
        PatientDTO patientDTO = PatientCreator.createPatientDTO(1, "john@gmail.com");
        PatientDTO patientDTO2 = PatientCreator.createPatientDTO(2, "john@gmail.com1");

        patients.add(patientDTO);
        patients.add(patientDTO2);

        when(patientServiceImpl.findAll()).thenReturn(patients);

        mockMvc.perform(get("/patients"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("john@gmail.com"))
                .andExpect(jsonPath("$[1].email").value("john@gmail.com1"));
    }

    @Test
    void findByEmail_PatientExists_ReturnPatient() throws Exception {
        String email = "john@gmail.com";
        PatientDTO patientDTO = PatientCreator.createPatientDTO(1, email);

        when(patientServiceImpl.findByEmail(email)).thenReturn(patientDTO);

        mockMvc.perform(get("/patients/{email}", email))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(patientDTO.getId()))
                .andExpect(jsonPath("$.email").value(patientDTO.getEmail()))
                .andExpect(jsonPath("$.firstName").value(patientDTO.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(patientDTO.getLastName()))
                .andExpect(jsonPath("$.phoneNumber").value(patientDTO.getPhoneNumber()))
                .andExpect(jsonPath("$.birthday").value(patientDTO.getBirthday()));
    }

    @Test
    void findByEmail_PatientNotExist_ThrowException() throws Exception {
        String email = "unknown@gmail.com";

        when(patientServiceImpl.findByEmail(email))
                .thenThrow(new EntityNotFoundException("No such patient"));

        mockMvc.perform(get("/patients/{email}", email))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Patient not found"));
    }

    @Test
    void save_CorrectPatient_ReturnPatient() throws Exception {
        String email = "john@gmail.com";
        PatientDTOwithPassword patientDTOwithPassword = PatientCreator.createPatientDTOwithPassword(1, email);
        PatientDTO patientDTO = PatientCreator.createPatientDTO(1, email);

        when(patientServiceImpl.save(patientDTOwithPassword)).thenReturn(patientDTO);

        mockMvc.perform(post("/patients")
                        .content(objectMapper.writeValueAsString(patientDTOwithPassword))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(patientDTO.getId()))
                .andExpect(jsonPath("$.email").value(patientDTO.getEmail()))
                .andExpect(jsonPath("$.firstName").value(patientDTO.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(patientDTO.getLastName()))
                .andExpect(jsonPath("$.phoneNumber").value(patientDTO.getPhoneNumber()))
                .andExpect(jsonPath("$.birthday").value(patientDTO.getBirthday()));
    }

    @Test
    void save_PatientWithThisEmailExists_ThrowException() throws Exception {
        String email = "john@gmail.com";
        PatientDTOwithPassword patientDTOwithPassword = PatientCreator.createPatientDTOwithPassword(1, email);

        when(patientServiceImpl.save(patientDTOwithPassword))
                .thenThrow(new EntityWithThisEmailExistsException("Patient is already in the database"));

        mockMvc.perform(post("/patients")
                        .content(objectMapper.writeValueAsString(patientDTOwithPassword))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Patient with this email already exists"));
    }

    @Test
    void save_PatientFieldNull_ThrowException() throws Exception {
        PatientDTOwithPassword patientDTOwithPassword = PatientCreator.createPatientDTOwithPassword(1, null);

        when(patientServiceImpl.save(patientDTOwithPassword))
                .thenThrow(new EntityNullFieldsException("None of patient class fields should be null"));

        mockMvc.perform(post("/patients")
                        .content(objectMapper.writeValueAsString(patientDTOwithPassword))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Patient fields cannot be empty"));
    }

    @Test
    void deleteByEmail_PatientFound_Delete() throws Exception {
        String email = "john@gmail.com";



        mockMvc.perform(delete("/patients/{email}", email))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(patientServiceImpl).deleteByEmail(email);
    }

    @Test
    void update_PatientExists_ReturnUpdated() throws Exception {
        String email = "john@gmail.com";
        PatientDTO patientDTO = PatientCreator.createPatientDTO(1, email);
        PatientDTO updatedPatientDTO = PatientCreator.createPatientDTO(1, "john@gmail.com");

        when(patientServiceImpl.update(email, patientDTO)).thenReturn(updatedPatientDTO);
        when(patientServiceImpl.findByEmail(email)).thenReturn(updatedPatientDTO);


        mockMvc.perform(put("/patients/{email}", email)
                        .content(objectMapper.writeValueAsString(patientDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(updatedPatientDTO.getId()))
                .andExpect(jsonPath("$.email").value(updatedPatientDTO.getEmail()))
                .andExpect(jsonPath("$.firstName").value(updatedPatientDTO.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(updatedPatientDTO.getLastName()))
                .andExpect(jsonPath("$.phoneNumber").value(updatedPatientDTO.getPhoneNumber()))
                .andExpect(jsonPath("$.birthday").value(updatedPatientDTO.getBirthday()));
    }

    @Test
    void update_PatientNotExist_ThrowException() throws Exception {
        String email = "john@gmail.com";
        PatientDTO patientDTO = PatientCreator.createPatientDTO(1, email);

        when(patientServiceImpl.update(email, patientDTO))
                .thenThrow(new EntityNotFoundException("Patient not found"));

        mockMvc.perform(put("/patients/{email}", email)
                        .content(objectMapper.writeValueAsString(patientDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Patient not found"));
    }

    @Test
    void updatePatientPassword_PasswordChanged_ReturnPassword() throws Exception {
        String email = "john@gmail.com";
        ChangePasswordCommand pass = new ChangePasswordCommand("1234", "4321");

        when(patientServiceImpl.editPatientPassword(email, pass)).thenReturn(pass);

        mockMvc.perform(patch("/patients/password/{email}", email)
                        .content(objectMapper.writeValueAsString(pass))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.newPassword").value(pass.getNewPassword()));
    }
}