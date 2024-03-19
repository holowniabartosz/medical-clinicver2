package com.bobi89.medicalclinic.model.entity.location;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@Builder
@ToString
@AllArgsConstructor
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id")
public class LocationDTO {

    private Long id;
    private String name;
    private String city;
    private String zipCode;
    private String street;
    private String streetNr;

//    @JsonManagedReference
    private Set<String> doctorsEmails;

    public LocationDTO(long id, String name, String city,
                       String zipCode, String street, String streetNr) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.zipCode = zipCode;
        this.street = street;
        this.streetNr = streetNr;
        this.doctorsEmails = new HashSet<>();
    }
}
