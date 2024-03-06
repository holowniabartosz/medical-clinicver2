package com.bobi89.medicalclinic.controller;

import com.bobi89.medicalclinic.model.entity.Patient;
import com.bobi89.medicalclinic.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class PatientController {

    private PatientService myPatientService;

    public PatientController(PatientService patientService) {
        myPatientService = patientService;
    }

    @GetMapping("/show-all-patient-data")
    public Set<Patient> showAllPatientData() {
        return myPatientService.getPatients();
    }

    @GetMapping("/{email}/show-patient-data")
    public Patient showPatientData(@PathVariable String email) {
        return myPatientService.getPatient(email);
    }

    @PostMapping("/{nrOfPatients}/add-bulk-patients")
    @ResponseStatus(HttpStatus.CREATED)
    public String addBulkPatients(@PathVariable int nrOfPatients) {
        myPatientService
                .addBulkPatients(nrOfPatients);
        return "Added " + nrOfPatients + " patients";
    }

    @PostMapping("/add-patient")
    @ResponseStatus(HttpStatus.CREATED)
    public Patient addPatient(@RequestBody Patient patient) {
        myPatientService
                .addPatient(patient);
        return patient;
    }

    @DeleteMapping("/{email}/remove-patient")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String getRemovePatient(@PathVariable String email) {
        myPatientService
                .removePatient(email);
        return "Removed patient with e-mail: " + email;
    }

    @PutMapping("{email}/edit-patient")
    public Patient updatePatient(@PathVariable String email, @RequestBody Patient patient) {
        myPatientService.editPatient(email, patient);
        return patient;
    }
}

