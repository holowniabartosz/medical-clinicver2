package com.bobi89.medicalclinic.model.entity.patient;

import lombok.*;

@AllArgsConstructor
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class PatientDTO {
    private long id;
    private String idCardNr;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String birthday;
}
