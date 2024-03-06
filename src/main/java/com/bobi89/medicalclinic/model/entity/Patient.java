package com.bobi89.medicalclinic.model.entity;

import com.bobi89.medicalclinic.enums.FIRST_NAMES;
import com.bobi89.medicalclinic.enums.LAST_NAMES;
import lombok.Data;

import java.util.Random;

@Data
public class Patient {
    private String email;
    private String password;
    private final int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String birthday;

    public Patient() {
        Random r = new Random();
        this.password = String.valueOf(r.nextInt(1000000, 9999999));
        this.id = r.nextInt(1000000, 9999999);
        this.firstName = String.valueOf(FIRST_NAMES.values()[r.nextInt(0, (FIRST_NAMES.values().length - 1))]);
        this.lastName = String.valueOf(LAST_NAMES.values()[r.nextInt(0, (FIRST_NAMES.values().length - 1))]);
        this.phoneNumber =
                String.valueOf(r.nextInt(100000000, 999999999));
        this.birthday = r.nextInt(1, 29) + "/"
                + r.nextInt(1, 13) + "/"
                + r.nextInt(1950, 2020);
        this.email = firstName.toLowerCase() + lastName.toLowerCase() + "@gmail.com";
    }

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
