package com.bobi89.medicalclinic.model.entity.doctor;

import com.bobi89.medicalclinic.model.entity.location.Location;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Set;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class DoctorDTOwithPassword {

    private long id;
    private String email;
    private String password;
    private FieldOfExpertise fieldOfExpertise;
    private Set<Location> locations;

}
