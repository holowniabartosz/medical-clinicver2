package com.bobi89.medicalclinic.service.doctor;

import com.bobi89.medicalclinic.model.entity.doctor.DoctorDTO;
import com.bobi89.medicalclinic.model.entity.doctor.DoctorDTOwithPassword;

import java.util.List;

public interface DoctorService {
    List<DoctorDTO> findAll();

    DoctorDTO findById(long id);

    DoctorDTO save(DoctorDTOwithPassword doctorDTOwithPassword);

    DoctorDTO addLocationToDoctor(long locationId, long doctorId);
}
