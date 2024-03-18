package com.bobi89.medicalclinic.service.doctor_service;

import com.bobi89.medicalclinic.exception.exc.EntityNotFoundException;
import com.bobi89.medicalclinic.exception.exc.EntityNullFieldsException;
import com.bobi89.medicalclinic.exception.exc.EntityWithThisIdExistsException;
import com.bobi89.medicalclinic.model.entity.doctor.Doctor;
import com.bobi89.medicalclinic.model.entity.doctor.DoctorDTO;
import com.bobi89.medicalclinic.model.entity.doctor.DoctorDTOwithPassword;
import com.bobi89.medicalclinic.model.entity.mapper.DoctorMapper;
import com.bobi89.medicalclinic.repository.DoctorJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private DoctorJpaRepository doctorJpaRepository;
    private DoctorMapper doctorMapper;

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

    private void validateIfNull(Doctor doctor) {
        if (doctor.getEmail() == null ||
                doctor.getPassword() == null ||
                doctor.getFieldOfExpertise() == null)
        {
            throw new EntityNullFieldsException("None of doctor class fields should be null");
        }
    }
}

