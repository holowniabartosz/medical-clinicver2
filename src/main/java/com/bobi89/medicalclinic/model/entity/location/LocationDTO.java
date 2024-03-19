package com.bobi89.medicalclinic.model.entity.location;

import com.bobi89.medicalclinic.model.entity.doctor.Doctor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
@EqualsAndHashCode
public class LocationDTO {

    private long id;
    private String name;
    private String city;
    private String ZIPcode;
    private String street;
    private String streetNr;
    private Set<Doctor> doctors;

    public LocationDTO(long id, String name, String city,
                       String ZIPcode, String street, String streetNr) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.ZIPcode = ZIPcode;
        this.street = street;
        this.streetNr = streetNr;
        this.doctors = new HashSet<>();
    }
}
