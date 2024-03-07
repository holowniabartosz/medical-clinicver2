package com.bobi89.medicalclinic.controller;

import com.bobi89.medicalclinic.model.entity.ChangePasswordCommand;
import com.bobi89.medicalclinic.model.entity.Patient;
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
    public Set<Patient> showAllPatientData() {
        return myPatientService.getPatients();
    }

    @GetMapping("/{email}")
    public Patient showPatientData(@PathVariable String email) {
        return myPatientService.getPatient(email);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Patient addPatient(@RequestBody Patient patient) {
        myPatientService
                .addPatient(patient);
        return patient;
    }

    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String removePatient(@PathVariable String email) {
        myPatientService
                .removePatient(email);
        return "Removed patient with e-mail: " + email;
    }

    @PutMapping("{email}")
    public Patient updatePatient(@PathVariable String email, @RequestBody Patient patient) {
        return myPatientService.editPatient(email, patient);
    }

    @PatchMapping("password/{email}")
    public ChangePasswordCommand updatePatientPassword(@PathVariable String email,
                                                       @RequestBody ChangePasswordCommand pass) {
        myPatientService.editPatientPassword(email, pass);
        return pass;
    }
}

