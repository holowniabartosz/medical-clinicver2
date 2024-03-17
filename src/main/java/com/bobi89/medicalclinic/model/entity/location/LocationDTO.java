package com.bobi89.medicalclinic.model.entity.location;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationDTO {

    private Long id;
    private String name;
    private String city;
    private String ZIPcode;
    private String street;
    private String streetNr;
}
