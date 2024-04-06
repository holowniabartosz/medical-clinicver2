package com.bobi89.medicalclinic.controller;

import com.bobi89.medicalclinic.model.entity.doctor.DoctorDTO;
import com.bobi89.medicalclinic.model.entity.doctor.DoctorDTOwithPassword;
import com.bobi89.medicalclinic.service.doctor.DoctorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/doctors")
public class DoctorController {

    private DoctorService doctorService;

    @GetMapping
    public List<DoctorDTO> findAll() {
        return doctorService.findAll();
    }

    @GetMapping("/{id}")
    public DoctorDTO findById(@PathVariable long id) {
        return doctorService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorDTO save(@RequestBody DoctorDTOwithPassword doctorDTOwithPassword) {
        return doctorService
                .save(doctorDTOwithPassword);
    }

    @PostMapping("/{doctorId}/assign-location")
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorDTO addLocationToDoctor(@RequestBody long locationId, @PathVariable long doctorId) {
        return doctorService.addLocationToDoctor(locationId, doctorId);
    }
}