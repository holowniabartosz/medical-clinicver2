package com.bobi89.medicalclinic.model.entity.doctor;

import com.bobi89.medicalclinic.model.entity.location.Location;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@ToString
@EqualsAndHashCode
public class DoctorDTO {
    private long id;
    private String email;
    private FieldOfExpertise fieldOfExpertise;
    private Set<Location> locations;

    public DoctorDTO(long id,String email, FieldOfExpertise fieldOfExpertise) {
        this.id = id;
        this.email = email;
        this.fieldOfExpertise = fieldOfExpertise;
        this.locations = new HashSet<>();
    }
}
