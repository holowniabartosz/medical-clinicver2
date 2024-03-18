package com.bobi89.medicalclinic.model.entity.location;

import com.bobi89.medicalclinic.model.entity.doctor.Doctor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class LocationDTO {

    private Long id;
    private String name;
    private String city;
    private String ZIPcode;
    private String street;
    private String streetNr;
    private Set<Doctor> doctors;

    public LocationDTO(Long id, String name, String city,
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
