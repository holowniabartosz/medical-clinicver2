package com.bobi89.medicalclinic.integration;

import com.bobi89.medicalclinic.model.entity.doctor.DoctorDTOwithPassword;
import com.bobi89.medicalclinic.model.entity.util.DoctorCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"file:src/test/resources/scripts/insert_doctor_data.sql"},
        config = @SqlConfig(encoding = "UTF-8", transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"file:src/test/resources/scripts/insert_location_data.sql"},
        config = @SqlConfig(encoding = "UTF-8", transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"file:src/test/resources/scripts/clear_doctor_data.sql"},
        config = @SqlConfig(encoding = "UTF-8", transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = {"file:src/test/resources/scripts/clear_location_data.sql"},
        config = @SqlConfig(encoding = "UTF-8", transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class DoctorIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void findAll() throws Exception {

        mockMvc.perform(get("/doctors"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("doctor@gmail.com"))
                .andExpect(jsonPath("$[1].email").value("doctor@gmail.com1"));
    }

    @Test
    void findById_DoctorExists_ReturnDoctor() throws Exception {
        long id = 1;

        mockMvc.perform(get("/doctors/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("doctor@gmail.com"));
    }

    @Test
    void save_CorrectDoctor_ReturnDoctor() throws Exception {
        String email = "Ndoctor@gmail.com";
        long id = 1;
        DoctorDTOwithPassword doctorDTOwithPassword = DoctorCreator.createDoctorDTOwithPassword(id, email);

        mockMvc.perform(post("/doctors")
                        .content(objectMapper.writeValueAsString(doctorDTOwithPassword))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(doctorDTOwithPassword.getId()))
                .andExpect(jsonPath("$.email").value(doctorDTOwithPassword.getEmail()));
    }

    @Test
    void addLocationToDoctor_CorrectLocation_ReturnDoctor() throws Exception {
        Long doctorId = 1L;
        Long locationId = 1L;
        String email = "doctor@gmail.com";

        mockMvc.perform(post("/doctors/{doctorId}/assign-location", doctorId)
                        .content(objectMapper.writeValueAsString(locationId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(doctorId))
                .andExpect(jsonPath("$.email").value(email));
    }
}