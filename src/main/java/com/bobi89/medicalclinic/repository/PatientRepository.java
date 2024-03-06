package com.bobi89.medicalclinic.repository;

import com.bobi89.medicalclinic.model.entity.Patient;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PatientRepository {

    HashSet<Patient> patientSet;

    public PatientRepository(HashSet<Patient> patientSet) {
        this.patientSet = new HashSet<>();
    }

    public HashSet<Patient> getAllPatientData() {
        return patientSet;

    }

    public Optional<Patient> getPatient(String email) {
        return patientSet.stream()
                .filter(s -> s.getEmail().equals(email)).findFirst();
    }

    public void addBulkPatients(int nrOfNewPatients) {
        Set<String> existingEmails = new HashSet<>(patientSet.stream()
                .map(s -> s.getEmail())
                .collect(Collectors.toSet()));
        for (int i = 0; i < nrOfNewPatients; i++) {
            Patient patient = new Patient();
            String email = patient.getEmail();
            if (existingEmails.add(email)) { // Add and check simultaneously using 'add' method
                patientSet.add(patient);
            }
        }
    }

    public Patient addPatient(Patient patient) {
        patientSet.add(patient);
        return patient;
    }

    public void removePatient(String patientEmail) {
        patientSet.removeIf(s -> s.getEmail().equals(patientEmail));
    }

    public Optional<Patient> editPatient(String patientEmail, Patient newPatient) {
        var editedPatient = patientSet.stream()
                .filter(s -> s.getEmail().equals(patientEmail))
                .findFirst();
        return editedPatient;
    }
}
