package com.bobi89.medicalclinic.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PatientDTOwithPassword {
    private String idCardNr;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String birthday;
    private String password;
}
