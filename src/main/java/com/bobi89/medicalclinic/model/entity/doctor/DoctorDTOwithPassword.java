package com.bobi89.medicalclinic.model.entity.doctor;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class DoctorDTOwithPassword {

    private long id;
    private String email;
    private String password;
    private FieldOfExpertise fieldOfExpertise;
}
