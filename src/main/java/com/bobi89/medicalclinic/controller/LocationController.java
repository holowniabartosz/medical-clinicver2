package com.bobi89.medicalclinic.controller;

import com.bobi89.medicalclinic.model.entity.location.LocationDTO;
import com.bobi89.medicalclinic.service.location_service.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/locations")
public class LocationController {

    private LocationService locationService;

    @GetMapping
    public List<LocationDTO> findAll() {
        return locationService.findAll();
    }

    @GetMapping("/{id}")
    public LocationDTO findById(@PathVariable long id) {
        return locationService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LocationDTO save(@RequestBody LocationDTO locationDTO) {
        return locationService
                .save(locationDTO);
    }

    @PostMapping("/{locationId}/assign")
    @ResponseStatus(HttpStatus.CREATED)
    public LocationDTO addDoctorToLocation(@RequestBody long doctorId, @PathVariable long locationId) {
        return locationService.addDoctorToLocation(doctorId,locationId);
    }
}