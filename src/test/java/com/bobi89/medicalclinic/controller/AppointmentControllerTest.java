package com.bobi89.medicalclinic.controller;

import com.bobi89.medicalclinic.model.entity.appointment.AppointmentDTO;
import com.bobi89.medicalclinic.model.entity.appointment.AppointmentRequest;
import com.bobi89.medicalclinic.model.entity.util.AppointmentCreator;
import com.bobi89.medicalclinic.service.appointment_service.AppointmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class AppointmentControllerTest {
    @MockBean
    private AppointmentService appointmentService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void findAll_Correct_ListReturned() throws Exception {
        List<AppointmentDTO> appointments = new ArrayList<>();
        LocalDateTime startDateTime = LocalDateTime.of(2030, 12, 25, 18, 0);
        LocalDateTime startDateTime2 = LocalDateTime.of(2031, 12, 25, 18, 0);
        long duration = 30;
        long doctorId = 1;
        AppointmentDTO appointmentDTO = AppointmentCreator.createAppointmentDTO(startDateTime, duration, doctorId);
        AppointmentDTO appointmentDTO2 = AppointmentCreator.createAppointmentDTO(startDateTime2, duration, doctorId);
        appointments.add(appointmentDTO);
        appointments.add(appointmentDTO2);

        when(appointmentService.findAll()).thenReturn(appointments);

        mockMvc.perform(get("/appointments"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].doctorId").value(1))
                .andExpect(jsonPath("$[1].duration").value("PT30M"));
    }

    @Test
    void addAppointmentToDoctor_AppointmentAddedToDoctor_ReturnsAppointment() throws Exception {
        LocalDateTime startDateTime = LocalDateTime.of(2030, 12, 25, 18, 0);
        long duration = 30;
        long doctorId = 1;
        AppointmentDTO appointmentDTO = AppointmentCreator.createAppointmentDTO(startDateTime, duration, doctorId);
        AppointmentRequest appointmentRequest = new AppointmentRequest(
                LocalDateTime.of(2030, 12, 25, 18, 0), duration);

        when(appointmentService.addAppointmentToDoctor(appointmentDTO.getStartDateTime(),
                appointmentDTO.getDuration(), doctorId)).thenReturn(appointmentDTO);

        mockMvc.perform(post("/appointments/add-appointment-to-doctor/{doctorId}", doctorId)
                .content(objectMapper.writeValueAsString(appointmentRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.doctorId").value(1))
                .andExpect(jsonPath("$.duration").value("PT30M"));
    }

    @Test
    void addPatientToAppointment_PatientAddedToAppointment_ReturnsAppointment() throws Exception {
        LocalDateTime startDateTime = LocalDateTime.of(2030, 12, 25, 18, 0);
        long duration = 30;
        long doctorId = 1;
        long appointmentId = 1;
        long patientId = 1;
        AppointmentDTO appointmentDTO = AppointmentCreator.createAppointmentDTO(startDateTime, duration, doctorId);

        when(appointmentService.addPatientToAppointment(appointmentId,doctorId)).thenReturn(appointmentDTO);
        appointmentDTO.setPatientId(patientId);

        mockMvc.perform(patch("/appointments/{appointmentId}", appointmentId)
                        .content(objectMapper.writeValueAsString(patientId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.patientId").value(1))
                .andExpect(jsonPath("$.duration").value("PT30M"));
    }
}