package com.bobi89.medicalclinic.service.doctor_service;

import com.bobi89.medicalclinic.model.entity.doctor.DoctorDTO;
import com.bobi89.medicalclinic.model.entity.doctor.DoctorDTOwithPassword;

import java.time.LocalDateTime;
import java.util.List;

public interface DoctorService {
    List<DoctorDTO> findAll();

    DoctorDTO findById(long id);

    DoctorDTO save(DoctorDTOwithPassword doctorDTOwithPassword);

    DoctorDTO addLocationToDoctor(long locationId, long doctorId);

    DoctorDTO addAppointmentToDoctor(LocalDateTime dateTime, int durationMinutes, long doctorId);

    DoctorDTO addAppointmentToDoctorSQL(LocalDateTime dateTime, int durationMinutes, long doctorId);
}
