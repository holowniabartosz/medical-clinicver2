package com.bobi89.medicalclinic.service;

import com.bobi89.medicalclinic.model.entity.ChangePasswordCommand;
import com.bobi89.medicalclinic.model.entity.Patient;
import com.bobi89.medicalclinic.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public HashSet<Patient> getPatients() {
        return patientRepository.getAllPatientData();
    }

    public Patient getPatient(String email) {
        return patientRepository.getPatient(email);
    }

    public void addBulkPatients(int nrOfNewPatients) {
        if (nrOfNewPatients > 0) {
            patientRepository.addBulkPatients(nrOfNewPatients);
        } else {
            throw new IllegalArgumentException("Number of new generated patients should be > 0");
        }
    }

    public Patient addPatient(Patient patient) {
        var existingPatient = patientRepository.getPatient(patient.getEmail());
            patientRepository.addPatient(patient);
        return patient;
    }

    public void removePatient(String email) {
        var existingPatient = patientRepository.getPatient(email);
        patientRepository.removePatient(email);
    }

    public Patient editPatient(String email, Patient patient) {
        if (patientRepository.getAllPatientData().contains(patientRepository.getPatient(email))) {
            throw new IllegalArgumentException("Patient with given email exists.");
        } else {
            patientRepository.editPatient(email, patient);
            return patient;
        }
    }

    public ChangePasswordCommand editPatientPassword(String email, ChangePasswordCommand pass) {
        if (patientRepository.getAllPatientData().contains(patientRepository.getPatient(email))) {
            patientRepository.editPatientPassword(email, pass);
        } else {
            throw new IllegalArgumentException("Patient with given email does not exists.");

        }
        return pass;
    }
}
