package com.bobi89.medicalclinic.repository;

import com.bobi89.medicalclinic.model.entity.ChangePasswordCommand;
import com.bobi89.medicalclinic.model.entity.Patient;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class PatientRepository {

    private HashSet<Patient> patientSet;

    public PatientRepository(HashSet<Patient> patientSet) {
        this.patientSet = new HashSet<>();
    }

    public HashSet<Patient> getAllPatientData() {
        return patientSet;
    }

    public Patient getPatient(String email) {
        return patientSet.stream()
                .filter(s -> s.getEmail()
                        .equals(email))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No such patient"));
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

    public Patient editPatient(String email, Patient newPatient) {
        var editedPatient = getPatient(email);
        editedPatient.update(newPatient);
        return editedPatient;
    }

    public ChangePasswordCommand editPatientPassword (String email, ChangePasswordCommand pass){
        var editedPatient = getPatient(email);
        if (editedPatient.getPassword().equals(pass.getOldPassword())){
            editedPatient.setPassword(pass.getNewPassword());
        } else {
            new IllegalArgumentException("Incorrect old password");
        } return pass;
    }
}
