package com.bobi89.medicalclinic.model.entity.patient;

import com.bobi89.medicalclinic.model.entity.appointment.Appointment;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

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
    private LocalDate birthday;

    @OneToMany(mappedBy = "patient")
    private Set<Appointment> appointments;

    public Patient(Long id, String idCardNr, String email, String password, String firstName, String lastName,
                   String phoneNumber, LocalDate birthday) {
        this.id = id;
        this.idCardNr = idCardNr;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
    }

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
