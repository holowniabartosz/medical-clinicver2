package com.bobi89.medicalclinic.model.entity.doctor;

import com.bobi89.medicalclinic.model.entity.appointment.Appointment;
import com.bobi89.medicalclinic.model.entity.location.Location;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;
    private String email;
    private String password;
    private FieldOfExpertise fieldOfExpertise;

    @ManyToMany(mappedBy = "doctors",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    private Set<Location> locations = new HashSet<>();

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private Set<Appointment> appointments;

    public Doctor(long id,String email, String password, FieldOfExpertise fieldOfExpertise) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.fieldOfExpertise = fieldOfExpertise;
        this.locations = new HashSet<>();
        this.appointments = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Doctor))
            return false;

        Doctor other = (Doctor) o;

        return id != null &&
                id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}