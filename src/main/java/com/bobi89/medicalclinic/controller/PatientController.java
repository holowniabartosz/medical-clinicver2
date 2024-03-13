package com.bobi89.medicalclinic.controller;

import com.bobi89.medicalclinic.model.entity.ChangePasswordCommand;
import com.bobi89.medicalclinic.model.entity.PatientDTO;
import com.bobi89.medicalclinic.model.entity.PatientDTOwithPassword;
import com.bobi89.medicalclinic.service.PatientServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/patients")
public class PatientController {

    private PatientServiceImpl myPatientService;

    @GetMapping
    public List<PatientDTO> findAll() {
        return myPatientService.findAll();
    }

    @GetMapping("/{email}")
    public PatientDTO findByEmail(@PathVariable String email) {
        return myPatientService.findByEmail(email);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatientDTO save(@RequestBody PatientDTOwithPassword patientDTOwithPassword) {
        return myPatientService
                .save(patientDTOwithPassword);
    }

    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteByEmail(@PathVariable String email) {
        myPatientService
                .deleteByEmail(email);
        return "Removed patient with e-mail: " + email;
    }

    @PutMapping("/{email}")
    public PatientDTO update(@PathVariable String email, @RequestBody PatientDTO patientDTO) {
        myPatientService.update(email, patientDTO);
        return myPatientService.findByEmail(patientDTO.getEmail());
    }

    @PatchMapping("password/{email}")
    public ChangePasswordCommand updatePatientPassword(@PathVariable String email,
                                                       @RequestBody ChangePasswordCommand pass) {
        myPatientService.editPatientPassword(email, pass);
        return pass;
    }
}

