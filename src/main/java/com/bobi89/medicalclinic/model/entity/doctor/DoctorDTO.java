package com.bobi89.medicalclinic.model.entity.doctor;

import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
@AllArgsConstructor
public class DoctorDTO {

    private Long id;
    private String email;
    private FieldOfExpertise fieldOfExpertise;
    private Set<String> locationsNames;
    private Set<LocalDateTime> appointmentsDates;

    public DoctorDTO(long id, String email, FieldOfExpertise fieldOfExpertise) {
        this.id = id;
        this.email = email;
        this.fieldOfExpertise = fieldOfExpertise;
        this.locationsNames = new HashSet<>();
        this.appointmentsDates = new HashSet<>();
    }
}
