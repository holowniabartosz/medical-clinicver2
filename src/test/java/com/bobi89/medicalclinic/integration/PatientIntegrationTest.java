package com.bobi89.medicalclinic.integration;

import com.bobi89.medicalclinic.model.entity.patient.ChangePasswordCommand;
import com.bobi89.medicalclinic.model.entity.patient.PatientDTO;
import com.bobi89.medicalclinic.model.entity.patient.PatientDTOwithPassword;
import com.bobi89.medicalclinic.model.entity.util.PatientCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"file:src/test/resources/scripts/insert_data.sql"},
        config = @SqlConfig(encoding = "UTF-8", transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"file:src/test/resources/scripts/clear_data.sql"},
        config = @SqlConfig(encoding = "UTF-8", transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class PatientIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        int pageSize = 2;
        int pageNumber = 0;

        mockMvc.perform(get("/patients")
                        .param("page", String.valueOf(pageNumber))
                        .param("size", String.valueOf(pageSize)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].email").value("john@gmail.com"))
                .andExpect(jsonPath("$.content[1].email").value("john@gmail.com1"));
    }

    @Test
    void findByEmail_PatientExists_ReturnPatient() throws Exception {
        String email = "john@gmail.com";

        mockMvc.perform(get("/patients/email/{email}", email))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("john@gmail.com"))
                .andExpect(jsonPath("$.firstName").value("J"))
                .andExpect(jsonPath("$.lastName").value("D"))
                .andExpect(jsonPath("$.phoneNumber").value("999999"));
    }

    @Test
    void save_CorrectPatient_ReturnPatient() throws Exception {
        String email = "Njohn@gmail.com";
        PatientDTOwithPassword patientDTOwithPassword = PatientCreator.createPatientDTOwithPassword(1, email);

        mockMvc.perform(post("/patients")
                        .content(objectMapper.writeValueAsString(patientDTOwithPassword))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(patientDTOwithPassword.getId()))
                .andExpect(jsonPath("$.email").value(patientDTOwithPassword.getEmail()))
                .andExpect(jsonPath("$.firstName").value(patientDTOwithPassword.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(patientDTOwithPassword.getLastName()))
                .andExpect(jsonPath("$.phoneNumber").value(patientDTOwithPassword.getPhoneNumber()));
    }

    @Test
    void deleteByEmail_PatientFound_Delete() throws Exception {
        String email = "john@gmail.com";

        mockMvc.perform(delete("/patients/{email}", email))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void update_PatientExists_ReturnUpdated() throws Exception {
        String email = "john@gmail.com";
        PatientDTO patientDTO = PatientCreator.createPatientDTO(1, email);

        mockMvc.perform(put("/patients/{email}", email)
                        .content(objectMapper.writeValueAsString(patientDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(patientDTO.getId()))
                .andExpect(jsonPath("$.email").value(patientDTO.getEmail()))
                .andExpect(jsonPath("$.firstName").value(patientDTO.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(patientDTO.getLastName()))
                .andExpect(jsonPath("$.phoneNumber").value(patientDTO.getPhoneNumber()));
    }

    @Test
    void updatePatientPassword_PatientExists() throws Exception {
        String email = "john@gmail.com";
        ChangePasswordCommand pass = new ChangePasswordCommand("1234", "4321");

        mockMvc.perform(put("/patients/{email}", email)
                        .content(objectMapper.writeValueAsString(pass))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }
}