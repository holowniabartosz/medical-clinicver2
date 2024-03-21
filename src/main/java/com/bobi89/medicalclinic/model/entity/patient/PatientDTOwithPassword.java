package com.bobi89.medicalclinic.model.entity.patient;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class PatientDTOwithPassword {
    private long id;
    private String idCardNr;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String birthday;
    private String password;
    private Set<LocalDateTime> appointmentsDates;

    public PatientDTOwithPassword(long id, String idCardNr, String email, String firstName, String lastName, String phoneNumber, String birthday, String password) {
        this.id = id;
        this.idCardNr = idCardNr;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.password = password;
    }
}


