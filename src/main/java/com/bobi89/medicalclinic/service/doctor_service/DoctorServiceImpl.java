package com.bobi89.medicalclinic.service.doctor_service;

import com.bobi89.medicalclinic.exception.exc.EntityNotFoundException;
import com.bobi89.medicalclinic.exception.exc.EntityNullFieldsException;
import com.bobi89.medicalclinic.exception.exc.EntityWithThisIdExistsException;
import com.bobi89.medicalclinic.model.entity.doctor.Doctor;
import com.bobi89.medicalclinic.model.entity.doctor.DoctorDTO;
import com.bobi89.medicalclinic.model.entity.doctor.DoctorDTOnonRecurring;
import com.bobi89.medicalclinic.model.entity.doctor.DoctorDTOwithPassword;
import com.bobi89.medicalclinic.model.entity.location.Location;
import com.bobi89.medicalclinic.model.entity.mapper.DoctorMapper;
import com.bobi89.medicalclinic.repository.DoctorJpaRepository;
import com.bobi89.medicalclinic.repository.LocationJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private DoctorJpaRepository doctorJpaRepository;
    private DoctorMapper doctorMapper;
    private LocationJpaRepository locationJpaRepository;

    @Override
    public List<DoctorDTO> findAll() {
        return doctorJpaRepository.findAll().stream()
                .map(d -> doctorMapper.toDTO(d))
                .collect(Collectors.toList());
    }

    @Override
    public DoctorDTO findById(long id){
        var location = doctorJpaRepository.findById(id);
        if (location.isEmpty()) {
            throw new EntityNotFoundException("No such doctor in the database");
        } else {
            return doctorMapper.toDTO(location.get());
        }
    }

    @Override
    public DoctorDTO save(DoctorDTOwithPassword doctorDTOwithPassword) {
        if (doctorJpaRepository.findByEmail(doctorDTOwithPassword.getEmail()).isPresent()) {
            throw new EntityWithThisIdExistsException("Doctor is already in the database");
        }
        validateIfNull(doctorMapper.toDoctor(doctorDTOwithPassword));
        return doctorMapper.toDTO(doctorJpaRepository
                .save(doctorMapper.toDoctor(doctorDTOwithPassword)));
    }

    @Override
    public DoctorDTOnonRecurring addLocationToDoctor(long locationId, long doctorId) {
        var doctor = doctorJpaRepository.findById(doctorId);
        var location = locationJpaRepository.findById(locationId);
        if (location.isEmpty() || doctor.isEmpty()) {
            throw new EntityNotFoundException("Location or doctor not found");
        } doctor.get().getLocations().add(location.get());
        return toDTOnonRecurring(doctorJpaRepository.save(doctor.get()));
    }

    private void validateIfNull(Doctor doctor) {
        if (doctor.getEmail() == null ||
                doctor.getPassword() == null ||
                doctor.getFieldOfExpertise() == null)
        {
            throw new EntityNullFieldsException("None of doctor class fields should be null");
        }
    }

    private static DoctorDTOnonRecurring toDTOnonRecurring(Doctor doctor){
        return new DoctorDTOnonRecurring(
                doctor.getId(),
                doctor.getEmail(),
                doctor.getFieldOfExpertise(),
                doctor.getLocations().stream()
                        .map(Location::getName)
                        .collect(Collectors.toSet())
        );

    }
}

