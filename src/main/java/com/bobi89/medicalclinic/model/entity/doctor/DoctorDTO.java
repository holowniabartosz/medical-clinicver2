package com.bobi89.medicalclinic.model.entity.doctor;

import lombok.*;

@AllArgsConstructor
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class DoctorDTO {
    private long id;
    private String email;
    private FieldOfExpertise fieldOfExpertise;

}
