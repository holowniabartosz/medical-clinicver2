package com.bobi89.medicalclinic.model.entity.mapper;

import com.bobi89.medicalclinic.model.entity.location.Location;
import com.bobi89.medicalclinic.model.entity.location.LocationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    LocationDTO toDTO(Location location);

    Location toLocation(LocationDTO locationDTO);
}
