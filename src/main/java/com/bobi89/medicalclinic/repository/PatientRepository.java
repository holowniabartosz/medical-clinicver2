package com.bobi89.medicalclinic.repository;

import com.bobi89.medicalclinic.exceptions.exc.PatientNotFoundException;
import com.bobi89.medicalclinic.exceptions.exc.PatientWithThisEmailExistsException;
import com.bobi89.medicalclinic.model.entity.ChangePasswordCommand;
import com.bobi89.medicalclinic.model.entity.Patient;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class PatientRepository {

    private HashSet<Patient> patientSet;

    public PatientRepository(HashSet<Patient> patientSet) {
        this.patientSet = new HashSet<>(List.of(
                new Patient("jd@gmail.com","1234","4321",
                        "John","Doe","987987987","01/02/2000"),
                new Patient("ct@gmail.com","1234","4321",
                        "Cindy","Tanner","987987987","01/02/2000"),
                new Patient("mr@gmail.com","1234","4321",
                        "Mark","Roberts","987987987","01/02/2000")));
    }

    public HashSet<Patient> getAllPatientData() {
        return patientSet;
    }

    public Patient getPatient(String email) {
        return patientSet.stream()
                .filter(s -> s.getEmail()
                        .equals(email))
                .findFirst()
                .orElseThrow(() ->
                        new PatientNotFoundException("Patient not found"));
    }

    public Patient addPatient(Patient patient) {
        Set<String> existingEmails = new HashSet<>(patientSet.stream()
                .map(s -> s.getEmail())
                .collect(Collectors.toSet()));
        String email = patient.getEmail();
        if (existingEmails.add(email)) {
            patientSet.add(patient);
        } else {
            throw new PatientWithThisEmailExistsException("Patient with this email already exists");
        }
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

    public ChangePasswordCommand editPatientPassword(String email, ChangePasswordCommand pass) {
        var editedPatient = getPatient(email);
        if (!editedPatient.getPassword().equals(pass.getOldPassword())) {
            throw new IllegalArgumentException("Provided old password is incorrect");
        }
        editedPatient.setPassword(pass.getNewPassword());
        return pass;
    }
}
