package com.bobi89.medicalclinic.service.location_service;

import com.bobi89.medicalclinic.exception.exc.EntityNotFoundException;
import com.bobi89.medicalclinic.exception.exc.EntityNullFieldsException;
import com.bobi89.medicalclinic.exception.exc.EntityWithThisIdExistsException;
import com.bobi89.medicalclinic.model.entity.location.Location;
import com.bobi89.medicalclinic.model.entity.location.LocationDTO;
import com.bobi89.medicalclinic.model.entity.mapper.LocationMapper;
import com.bobi89.medicalclinic.repository.DoctorJpaRepository;
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
    private DoctorJpaRepository doctorJpaRepository;

    @Override
    public List<LocationDTO> findAll() {
        return locationJpaRepository.findAll().stream()
                .map(l -> locationMapper.toDTO(l))
                .collect(Collectors.toList());
    }

    @Override
    public LocationDTO findById(long id) {
        var location = locationJpaRepository.findById(id);
        if (location.isEmpty()) {
            throw new EntityNotFoundException("No such location in the database");
        }
        return locationMapper.toDTO(location.get());
    }

    @Override
    public LocationDTO save(LocationDTO locationDTO) {
        if (locationJpaRepository.findByName(locationDTO.getName()).isPresent()) {
            throw new EntityWithThisIdExistsException("Location is already in the database");
        }
        validateIfNull(locationMapper.toLocation(locationDTO));
        return locationMapper.toDTO(locationJpaRepository
                .save(locationMapper.toLocation(locationDTO)));
    }

    @Override
    public LocationDTO addDoctorToLocation(long doctorId, long locationId) {
        var doctor = doctorJpaRepository.findById(doctorId);
        var location = locationJpaRepository.findById(locationId);
        if (location.isEmpty() || doctor.isEmpty()) {
            throw new EntityNotFoundException("Location or doctor not found");
        }
        location.get().getDoctors().add(doctor.get());
        return locationMapper.toDTO(locationJpaRepository.save(location.get()));
    }

    private void validateIfNull(Location location) {
        if (location.getStreetNr() == null ||
                location.getName() == null ||
                location.getCity() == null ||
                location.getZipCode() == null ||
                location.getStreet() == null) {
            throw new EntityNullFieldsException("None of location class fields should be null");
        }
    }
}

