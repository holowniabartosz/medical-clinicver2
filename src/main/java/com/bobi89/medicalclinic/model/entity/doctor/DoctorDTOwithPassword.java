package com.bobi89.medicalclinic.model.entity.doctor;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
public class DoctorDTOwithPassword {

    private Long id;
    private String email;
    private String password;
    private FieldOfExpertise fieldOfExpertise;
    private Set<String> locationsNames;

    public DoctorDTOwithPassword(long id,String email, String password, FieldOfExpertise fieldOfExpertise) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.fieldOfExpertise = fieldOfExpertise;
        this.locationsNames = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof DoctorDTOwithPassword))
            return false;

        DoctorDTOwithPassword other = (DoctorDTOwithPassword) o;

        return id != null &&
                id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
