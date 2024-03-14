package com.bobi89.medicalclinic.model.entity.mapper;

import com.bobi89.medicalclinic.model.entity.Patient;
import com.bobi89.medicalclinic.model.entity.PatientDTO;
import com.bobi89.medicalclinic.model.entity.PatientDTOwithPassword;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientDTO toDTO(Patient patient);

    Patient toPatient(PatientDTO patientDTO);

    Patient toPatient(PatientDTOwithPassword patientDTOwithPassword);

}
