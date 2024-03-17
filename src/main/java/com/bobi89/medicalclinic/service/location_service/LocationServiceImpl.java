package com.bobi89.medicalclinic.service.location_service;

import com.bobi89.medicalclinic.exception.exc.EntityNotFoundException;
import com.bobi89.medicalclinic.exception.exc.EntityNullFieldsException;
import com.bobi89.medicalclinic.exception.exc.EntityWithThisIdExistsException;
import com.bobi89.medicalclinic.model.entity.location.Location;
import com.bobi89.medicalclinic.model.entity.location.LocationDTO;
import com.bobi89.medicalclinic.model.entity.mapper.LocationMapper;
import com.bobi89.medicalclinic.repository.LocationJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LocationServiceImpl implements LocationService {

    private LocationJpaRepository locationJpaRepository;
    private LocationMapper locationMapper;

    @Override
    public List<LocationDTO> findAll() {
        return locationJpaRepository.findAll().stream()
                .map(l -> locationMapper.toDTO(l))
                .collect(Collectors.toList());
    }

    @Override
    public LocationDTO findById(long id){
        var location = locationJpaRepository.findById(id);
        if (location.isEmpty()) {
            throw new EntityNotFoundException("No such location in the database");
        } else {
            return locationMapper.toDTO(location.get());
        }
    }

    @Override
    public LocationDTO save(LocationDTO locationDTO) {
        if (locationJpaRepository.findById(locationDTO.getId()).isPresent()) {
            throw new EntityWithThisIdExistsException("Location is already in the database");
        }
        validateIfNull(locationMapper.toLocation(locationDTO));
        return locationMapper.toDTO(locationJpaRepository
                .save(locationMapper.toLocation(locationDTO)));
    }

    private void validateIfNull(Location location) {
        if (location.getStreetNr() == null ||
                location.getName() == null ||
                location.getCity() == null ||
                location.getZIPcode() == null ||
                location.getStreet() == null) {
            throw new EntityNullFieldsException("None of location class fields should be null");
        }
    }
}

