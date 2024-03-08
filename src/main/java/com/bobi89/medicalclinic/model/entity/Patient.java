package com.bobi89.medicalclinic.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Patient {
    private String email;
    private String password;
    private String idCardNr;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String birthday;

    public Patient(String email, String firstName, String lastName, String phoneNumber) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public void update(Patient newPatientData) {
        this.email = newPatientData.getEmail();
        this.firstName = newPatientData.getFirstName();
        this.lastName = newPatientData.getLastName();
        this.phoneNumber = newPatientData.getPhoneNumber();
    }

    public static Patient toPatient(PatientDTO patientDTO){
        return new Patient(
                patientDTO.getEmail(), patientDTO.getFirstName(),
                patientDTO.getLastName(), patientDTO.getPhoneNumber());
    }
}
