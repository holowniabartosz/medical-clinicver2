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

    public void update(Patient newPatientData) {
        this.email = newPatientData.getEmail();
//        this.id = newPatientData.getId();
        this.firstName = newPatientData.getFirstName();
        this.lastName = newPatientData.getLastName();
//        this.password = newPatientData.getPassword();
        this.birthday = newPatientData.getBirthday();
        this.phoneNumber = newPatientData.getPhoneNumber();
    }
}
