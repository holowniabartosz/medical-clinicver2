package com.bobi89.medicalclinic.model.entity.util;

import com.bobi89.medicalclinic.model.entity.location.Location;
import com.bobi89.medicalclinic.model.entity.location.LocationDTO;

public class LocationCreator {
    public static Location createLocation(long id, String city) {
        return new Location(id, "Clinic1", city, "1234",
                "StJames", "101");
    }

    public static LocationDTO createLocationDTO(long id, String city) {
        return new LocationDTO(id, "Clinic1", city, "1234",
                "StJames", "101");
    }
}
