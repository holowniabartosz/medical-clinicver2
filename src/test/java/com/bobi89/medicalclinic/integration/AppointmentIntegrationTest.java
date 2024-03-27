package com.bobi89.medicalclinic.integration;

import com.bobi89.medicalclinic.model.entity.appointment.AppointmentRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Sql(scripts = {"file:src/test/resources/scripts/insert_data.sql"},
        config = @SqlConfig(encoding = "UTF-8", transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"file:src/test/resources/scripts/insert_doctor_data.sql"},
        config = @SqlConfig(encoding = "UTF-8", transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"file:src/test/resources/scripts/insert_appointment_data.sql"},
        config = @SqlConfig(encoding = "UTF-8", transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"file:src/test/resources/scripts/clear_appointment_data.sql"},
        config = @SqlConfig(encoding = "UTF-8", transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class AppointmentIntegrationTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void findAll_Correct_ListReturned() throws Exception {

        mockMvc.perform(get("/appointments"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].doctorId").value(1))
                .andExpect(jsonPath("$[1].endDateTime").value("2031-12-25T18:30:00"));
    }

    @Test
    void addAppointmentToDoctor_AppointmentAddedToDoctor_ReturnsAppointment() throws Exception {
        long doctorId = 1;
        AppointmentRequest appointmentRequest = new AppointmentRequest(
                LocalDateTime.of(2032, 11, 25, 20, 0),
                LocalDateTime.of(2032, 11, 25, 20, 30), doctorId);

        mockMvc.perform(post("/appointments")
                        .content(objectMapper.writeValueAsString(appointmentRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.doctorId").value(1))
                .andExpect(jsonPath("$.endDateTime").value("2032-11-25T20:30:00"));
    }

    @Test
    void addPatientToAppointment_PatientAddedToAppointment_ReturnsAppointment() throws Exception {
        long appointmentId = 1;
        long patientId = 1;

        mockMvc.perform(patch("/appointments/{appointmentId}", appointmentId)
                        .content(objectMapper.writeValueAsString(patientId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.patientId").value(1))
                .andExpect(jsonPath("$.endDateTime").value("2030-12-25T18:30:00"));
    }
}