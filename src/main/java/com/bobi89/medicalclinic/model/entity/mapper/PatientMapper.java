package com.bobi89.medicalclinic.model.entity.mapper;

import com.bobi89.medicalclinic.model.entity.Patient;
import com.bobi89.medicalclinic.model.entity.PatientDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    @Mapping(target = "birthday", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "id", ignore = true)
    PatientDTO toDTO(Patient patient);

    Patient toPatient(PatientDTO patientDTO);
}
