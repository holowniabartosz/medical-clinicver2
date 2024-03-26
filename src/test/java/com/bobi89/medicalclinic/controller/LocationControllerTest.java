package com.bobi89.medicalclinic.controller;

import com.bobi89.medicalclinic.model.entity.location.LocationDTO;
import com.bobi89.medicalclinic.model.entity.util.LocationCreator;
import com.bobi89.medicalclinic.service.location_service.LocationServiceImpl;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class LocationControllerTest {

    @MockBean
    private LocationServiceImpl locationServiceImpl;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        List<LocationDTO> locations = new ArrayList<>();
        LocationDTO locationDTO = LocationCreator.createLocationDTO(1, "London");
        LocationDTO locationDTO2 = LocationCreator.createLocationDTO(2, "Manchester");

        locations.add(locationDTO);
        locations.add(locationDTO2);

        when(locationServiceImpl.findAll()).thenReturn(locations);

        mockMvc.perform(get("/locations"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].city").value("London"))
                .andExpect(jsonPath("$[1].city").value("Manchester"));
    }

    @Test
    void findById_LocationExists_ReturnLocation() throws Exception {
        long id = 1;
        String city = "London";
        LocationDTO locationDTO = LocationCreator.createLocationDTO(id, city);

        when(locationServiceImpl.findById(id)).thenReturn(locationDTO);

        mockMvc.perform(get("/locations/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(locationDTO.getId()))
                .andExpect(jsonPath("$.city").value(locationDTO.getCity()));
    }

    @Test
    void save_CorrectLocation_ReturnLocation() throws Exception {
        long id = 1;
        String city = "London";
        LocationDTO locationDTO = LocationCreator.createLocationDTO(id, city);

        when(locationServiceImpl.save(any())).thenReturn(locationDTO);

        mockMvc.perform(post("/locations")
                        .content(objectMapper.writeValueAsString(locationDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(locationDTO.getId()))
                .andExpect(jsonPath("$.city").value(locationDTO.getCity()));
    }

    @Test
    void addDoctorToLocation_CorrectDoctor_ReturnLocation() throws Exception {
        Long doctorId = 1L;
        Long locationId = 10L;
        String city = "London";
        LocationDTO locationDTO = LocationCreator.createLocationDTO(locationId, city);

        when(locationServiceImpl.addDoctorToLocation(doctorId, locationId)).thenReturn(locationDTO);

        mockMvc.perform(post("/locations/{locationId}/assign-doctor", locationId)
                        .content(objectMapper.writeValueAsString(doctorId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(locationDTO.getId()))
                .andExpect(jsonPath("$.city").value(locationDTO.getCity()));
    }

}