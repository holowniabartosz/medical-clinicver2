package com.bobi89.medicalclinic.service;

import com.bobi89.medicalclinic.model.entity.doctor.Doctor;
import com.bobi89.medicalclinic.model.entity.location.Location;
import com.bobi89.medicalclinic.model.entity.location.LocationDTO;
import com.bobi89.medicalclinic.model.entity.mapper.LocationMapper;
import com.bobi89.medicalclinic.model.entity.util.DoctorCreator;
import com.bobi89.medicalclinic.model.entity.util.LocationCreator;
import com.bobi89.medicalclinic.repository.DoctorJpaRepository;
import com.bobi89.medicalclinic.repository.LocationJpaRepository;
import com.bobi89.medicalclinic.service.location_service.LocationService;
import com.bobi89.medicalclinic.service.location_service.LocationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

class LocationServiceImplTest {

    private LocationMapper locationMapper;
    private LocationJpaRepository locationJpaRepository;
    private LocationService locationService;
    private DoctorJpaRepository doctorJpaRepository;


    @BeforeEach
    void setup() {
        this.locationJpaRepository = Mockito.mock(LocationJpaRepository.class);
        this.locationMapper = Mappers.getMapper(LocationMapper.class);
        this.doctorJpaRepository = Mockito.mock(DoctorJpaRepository.class);
        this.locationService = new LocationServiceImpl(locationJpaRepository, locationMapper, doctorJpaRepository);
    }

    @Test
    void findAll_locationsExists_returnLocations() {
        //given
        List<Location> locations = new ArrayList<>();
        Location location = LocationCreator.createLocation(1, "London");
        Location location2 = LocationCreator.createLocation(2, "Manchester");
        locations.add(location);
        locations.add(location2);

        when(locationJpaRepository.findAll())
                .thenReturn(locations);

        //when
        var result = locationService.findAll();

        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("London", result.get(0).getCity());
        Assertions.assertEquals("Manchester", result.get(1).getCity());
    }

    @Test
    void findByEmail_LocationExists_LocationReturned() {
        //given
        Location location = LocationCreator.createLocation(1, "London");

        when(locationJpaRepository.findById(1L))
                .thenReturn(Optional.of(location));

        //when
        var result = locationService.findById(1);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals("London", result.getCity());
        Assertions.assertEquals(1, result.getId());
    }

    @Test
    void save_IfUniqueEmail_LocationReturned() {
        //given
        Location location = LocationCreator.createLocation(1, "London");
        LocationDTO locationDTO = LocationCreator.createLocationDTO(1, "London");


        when(locationJpaRepository.findById(1L)).thenReturn(Optional.empty());
        when(locationJpaRepository.save(location)).thenReturn(location);

        //when
        LocationDTO updatedLocation = locationService.save(locationDTO);

        //then
        Assertions.assertNotNull(updatedLocation);
        Assertions.assertEquals("London", updatedLocation.getCity());
        Assertions.assertEquals(1, updatedLocation.getId());
    }

    @Test
    void addDoctorToLocation_Correct_LocationDTOReturned() {
        //given
        Long doctorId = 1L;
        Long locationId = 10L;
        String email = "doctor@gmail.com";

        Doctor doctor = DoctorCreator.createDoctor(doctorId, email);

        Location location = LocationCreator.createLocation(1, "London");

        when(doctorJpaRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(locationJpaRepository.findById(locationId)).thenReturn(Optional.of(location));
        when(locationJpaRepository.save(location)).thenReturn(location);

        //when
        var locationDtoReturned = locationService
                .addDoctorToLocation(doctorId,locationId);

        //then
        Assertions.assertNotNull(locationDtoReturned);
        Assertions.assertEquals("London", locationDtoReturned.getCity());
        Assertions.assertEquals(1, locationDtoReturned.getId());
    }
}