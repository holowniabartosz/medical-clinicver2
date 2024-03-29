package com.bobi89.medicalclinic.model.entity.location;

import com.bobi89.medicalclinic.model.entity.doctor.Doctor;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
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
    private String zipCode;
    private String street;
    private String streetNr;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name = "location_doctor",
            joinColumns = @JoinColumn(name = "location_id"),
            inverseJoinColumns = @JoinColumn(name = "doctor_id")
    )
    private Set<Doctor> doctors;

    public Location(Long id, String name, String city, String ZIPcode,
                    String street, String streetNr) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.zipCode = ZIPcode;
        this.street = street;
        this.streetNr = streetNr;
        this.doctors = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Location))
            return false;

        Location other = (Location) o;

        return id != null &&
                id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
