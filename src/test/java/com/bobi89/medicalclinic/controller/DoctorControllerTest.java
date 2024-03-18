package com.bobi89.medicalclinic.controller;

import com.bobi89.medicalclinic.model.entity.doctor.DoctorDTO;
import com.bobi89.medicalclinic.model.entity.doctor.DoctorDTOwithPassword;
import com.bobi89.medicalclinic.model.entity.util.DoctorCreator;
import com.bobi89.medicalclinic.service.doctor_service.DoctorServiceImpl;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class DoctorControllerTest {

    @MockBean
    private DoctorServiceImpl doctorServiceImpl;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        List<DoctorDTO> doctors = new ArrayList<>();
        DoctorDTO doctorDTO = DoctorCreator.createDoctorDTO(0,"doctor@gmail.com");
        DoctorDTO doctorDTO2 = DoctorCreator.createDoctorDTO(0,"doctor@gmail.com1");

        doctors.add(doctorDTO);
        doctors.add(doctorDTO2);

        when(doctorServiceImpl.findAll()).thenReturn(doctors);

        mockMvc.perform(get("/doctors"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("doctor@gmail.com"))
                .andExpect(jsonPath("$[1].email").value("doctor@gmail.com1"));
    }

    @Test
    void findById_DoctorExists_ReturnDoctor() throws Exception {
        long id = 1;
        String email = "doctor@gmail.com";
        DoctorDTO doctorDTO = DoctorCreator.createDoctorDTO(id,email);

        when(doctorServiceImpl.findById(id)).thenReturn(doctorDTO);

        mockMvc.perform(get("/doctors/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(doctorDTO.getId()))
                .andExpect(jsonPath("$.email").value(doctorDTO.getEmail()));
    }

    @Test
    void save_CorrectDoctor_ReturnDoctor() throws Exception {
        long id = 10;
        String email = "doctor@gmail.com";
        DoctorDTO doctorDTO = DoctorCreator.createDoctorDTO(id,email);
        DoctorDTOwithPassword doctorDTOwithPassword = DoctorCreator.createDoctorDTOwithPassword(id, email);

        when(doctorServiceImpl.save(doctorDTOwithPassword)).thenReturn(doctorDTO);

        mockMvc.perform(post("/doctors")
                        .content(objectMapper.writeValueAsString(doctorDTOwithPassword))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(doctorDTO.getId()))
                .andExpect(jsonPath("$.email").value(doctorDTO.getEmail()));
    }

}