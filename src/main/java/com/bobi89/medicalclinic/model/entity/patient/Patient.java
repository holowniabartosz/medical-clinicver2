package com.bobi89.medicalclinic.model.entity.patient;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Patient))
            return false;

        Patient other = (Patient) o;

        return id != null &&
                id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
