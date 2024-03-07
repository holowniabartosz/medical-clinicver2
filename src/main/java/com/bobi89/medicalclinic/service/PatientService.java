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

    public Patient addPatient(Patient patient) {
//        var existingPatient = patientRepository.getPatient(patient.getEmail());
       patientRepository.addPatient(patient);
       return patient;
    }

    public void removePatient(String email) {
        var existingPatient = patientRepository.getPatient(email);
        patientRepository.removePatient(email);
    }

    // nie można ID
    public Patient editPatient(String email, Patient patient) {
        checkIfIdChanged(email, patient);
        validate(email);
        validateIfNull(patient);
        return patientRepository.editPatient(email, patient);
    }


    public ChangePasswordCommand editPatientPassword(String email, ChangePasswordCommand pass) {
        var editedPasswordPatient = patientRepository.getPatient(email);
        if (editedPasswordPatient == null) {
            throw new IllegalArgumentException("Patient not found");
        }
        patientRepository.editPatientPassword(email, pass);
        return pass;
    }

    public void validate(String email) {
        if (!patientRepository.getAllPatientData().contains(patientRepository.getPatient(email))) {
            throw new IllegalArgumentException("Patient with given email does not exist.");
        }
    }

    public void validateIfNull(Patient patient) {
        if (patient.getPassword() == null ||
                patient.getBirthday() == null ||
                patient.getEmail() == null ||
                patient.getPhoneNumber() == null ||
                patient.getFirstName() == null ||
                patient.getLastName() == null) {
            throw new IllegalArgumentException("None of patient class fields should be null");
        }
    }

    public void checkIfIdChanged(String email, Patient patient) {
        if (!patient.getIdCardNr().equals(patientRepository.getPatient(email).getIdCardNr())) {
            throw new IllegalArgumentException("ID can't be changed");
        }
    }
}

