package com.bobi89.medicalclinic.model.entity.doctor;

import com.bobi89.medicalclinic.model.entity.location.Location;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private long id;
    private String email;
    private String password;
    private FieldOfExpertise fieldOfExpertise;

    @ManyToMany(mappedBy = "doctors")
    private Set<Location> locations;

    public Doctor(long id,String email, String password, FieldOfExpertise fieldOfExpertise) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.fieldOfExpertise = fieldOfExpertise;
    }
}
