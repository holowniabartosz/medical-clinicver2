package com.bobi89.medicalclinic.model.entity.doctor;

import com.bobi89.medicalclinic.model.entity.location.Location;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
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
}
