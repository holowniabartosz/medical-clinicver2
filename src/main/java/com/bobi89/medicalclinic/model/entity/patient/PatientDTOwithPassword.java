package com.bobi89.medicalclinic.model.entity.patient;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class PatientDTOwithPassword {
    private long id;
    private String idCardNr;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String birthday;
    private String password;
}
