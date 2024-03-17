package com.bobi89.medicalclinic.model.entity.doctor;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DoctorDTO {
    private long id;
    private String email;
    private String password;
    private FieldOfExpertise fieldOfExpertise;
}
