package com.bobi89.medicalclinic.model.entity.patient;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
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
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthday;
    private Set<LocalDateTime> appointmentsDates;

    public PatientDTO(long id, String idCardNr, String email, String firstName, String lastName,
                      String phoneNumber,LocalDate birthday) {
        this.id = id;
        this.idCardNr = idCardNr;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
    }
}
