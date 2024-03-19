package com.bobi89.medicalclinic.model.entity.doctor;

import com.bobi89.medicalclinic.model.entity.location.Location;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
@AllArgsConstructor
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id")
public class DoctorDTO {

    private Long id;
    private String email;
    private FieldOfExpertise fieldOfExpertise;

//    @JsonBackReference
    private Set<Location> locations;

    public DoctorDTO(long id,String email, FieldOfExpertise fieldOfExpertise) {
        this.id = id;
        this.email = email;
        this.fieldOfExpertise = fieldOfExpertise;
        this.locations = new HashSet<>();
    }

    //    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//
//        if (!(o instanceof DoctorDTO))
//            return false;
//
//        DoctorDTO other = (DoctorDTO) o;
//
//        return id != null &&
//                id.equals(other.getId());
//    }
//
//    @Override
//    public int hashCode() {
//        return getClass().hashCode();
//    }
}
