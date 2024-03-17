package com.bobi89.medicalclinic.model.entity.mapper;

import com.bobi89.medicalclinic.model.entity.doctor.Doctor;
import com.bobi89.medicalclinic.model.entity.doctor.DoctorDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    DoctorDTO toDTO(Doctor doctor);

    Doctor toDoctor(DoctorDTO doctorDTO);
}
