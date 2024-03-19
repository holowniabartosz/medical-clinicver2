package com.bobi89.medicalclinic.model.entity.mapper;

import com.bobi89.medicalclinic.model.entity.location.Location;
import com.bobi89.medicalclinic.model.entity.location.LocationDTO;
import com.bobi89.medicalclinic.model.entity.util.LocationCreator;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocationMapperTest {

    private LocationMapper mapper = Mappers.getMapper(LocationMapper.class);

    @Test
    void toDTO_MapsAllFields() {

        Location location = LocationCreator.createLocation(1, "London");

        LocationDTO locationDTO = mapper.toDTO(location);

        assertEquals(location.getId(), locationDTO.getId());
        assertEquals(location.getName(), locationDTO.getName());
        assertEquals(location.getCity(), locationDTO.getCity());
        assertEquals(location.getZipCode(), locationDTO.getZipCode());
        assertEquals(location.getStreet(), locationDTO.getStreet());
        assertEquals(location.getStreetNr(), locationDTO.getStreetNr());
    }

    @Test
    void toLocation_MapsAllFields() {
        LocationDTO locationDTO = LocationCreator.createLocationDTO(1, "London");

        Location location = mapper.toLocation(locationDTO);

        assertEquals(locationDTO.getId(), location.getId());
        assertEquals(locationDTO.getName(), location.getName());
        assertEquals(locationDTO.getCity(), location.getCity());
        assertEquals(locationDTO.getZipCode(), location.getZipCode());
        assertEquals(locationDTO.getStreet(), location.getStreet());
        assertEquals(locationDTO.getStreetNr(), location.getStreetNr());
    }
}