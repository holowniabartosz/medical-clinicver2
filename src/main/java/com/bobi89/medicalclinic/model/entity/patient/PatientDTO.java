package com.bobi89.medicalclinic.model.entity.patient;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

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
    private Set<LocalDateTime> appointmentsDates;

    public PatientDTO(long id, String idCardNr, String email, String firstName, String lastName, String phoneNumber, String birthday) {
        this.id = id;
        this.idCardNr = idCardNr;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
    }
}
