package com.bobi89.medicalclinic.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
//@Table(name="patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,unique = true)
    private String idCardNr;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String birthday;


    public void update(Patient newPatientData) {
        this.email = newPatientData.getEmail();
        this.firstName = newPatientData.getFirstName();
        this.lastName = newPatientData.getLastName();
        this.phoneNumber = newPatientData.getPhoneNumber();
        this.birthday = newPatientData.getBirthday();
    }


}
