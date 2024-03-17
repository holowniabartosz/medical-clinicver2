package com.bobi89.medicalclinic.model.entity.location;

import com.bobi89.medicalclinic.model.entity.doctor.Doctor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    private String city;
    private String ZIPcode;
    private String street;
    private String streetNr;

    @ManyToMany
    @JoinTable(
            name = "location_doctor",
            joinColumns = @JoinColumn(name = "location_id"),
            inverseJoinColumns = @JoinColumn(name = "doctor_id")
    )
    private Set<Doctor> doctors;
}
