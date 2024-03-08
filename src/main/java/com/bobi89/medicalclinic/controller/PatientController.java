package com.bobi89.medicalclinic.controller;

import com.bobi89.medicalclinic.model.entity.ChangePasswordCommand;
import com.bobi89.medicalclinic.model.entity.PatientDTO;
import com.bobi89.medicalclinic.service.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/patients")
public class PatientController {

    private PatientService myPatientService;

    @GetMapping
    public Set<PatientDTO> showAllPatientData() {
        return myPatientService.getPatients();
    }

    @GetMapping("/{email}")
    public PatientDTO showPatientData(@PathVariable String email) {
        return myPatientService.getPatient(email);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatientDTO addPatient(@RequestBody PatientDTO patientDTO) {
        myPatientService
                .addPatient(patientDTO);
        return patientDTO;
    }

    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String removePatient(@PathVariable String email) {
        myPatientService
                .removePatient(email);
        return "Removed patient with e-mail: " + email;
    }

    @PutMapping("{email}")
    public PatientDTO updatePatient(@PathVariable String email, @RequestBody PatientDTO patientDTO) {
        return myPatientService.editPatient(email, patientDTO);
    }

    @PatchMapping("password/{email}")
    public ChangePasswordCommand updatePatientPassword(@PathVariable String email,
                                                       @RequestBody ChangePasswordCommand pass) {
        myPatientService.editPatientPassword(email, pass);
        return pass;
    }
}

