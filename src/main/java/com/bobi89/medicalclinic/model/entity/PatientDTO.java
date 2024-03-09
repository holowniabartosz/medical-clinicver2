package com.bobi89.medicalclinic.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PatientDTO {
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public void update(PatientDTO newPatientData) {
        this.email = newPatientData.getEmail();
        this.firstName = newPatientData.getFirstName();
        this.lastName = newPatientData.getLastName();
        this.phoneNumber = newPatientData.getPhoneNumber();
    }

//    public static PatientDTO toDTO(Patient patient){
//        return new PatientDTO(
//                patient.getEmail(), patient.getFirstName(),
//                patient.getLastName(), patient.getPhoneNumber());
//    }
}
