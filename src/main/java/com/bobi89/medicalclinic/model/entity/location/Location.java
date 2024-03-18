package com.bobi89.medicalclinic.model.entity.location;

import com.bobi89.medicalclinic.model.entity.doctor.Doctor;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
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

    public Location(Long id, String name, String city, String ZIPcode, String street, String streetNr) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.ZIPcode = ZIPcode;
        this.street = street;
        this.streetNr = streetNr;
    }
}
