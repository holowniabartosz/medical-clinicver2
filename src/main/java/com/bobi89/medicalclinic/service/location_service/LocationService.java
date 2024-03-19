package com.bobi89.medicalclinic.service.location_service;

import com.bobi89.medicalclinic.model.entity.location.LocationDTO;
import com.bobi89.medicalclinic.model.entity.location.LocationDTOnonRecurring;

import java.util.List;

public interface LocationService {

    List<LocationDTO> findAll();

    LocationDTO findById(long id);

    LocationDTO save(LocationDTO locationDTO);

    LocationDTOnonRecurring addDoctorToLocation(long doctorId, long locationId);
}
