package com.bobi89.medicalclinic.model.entity.mapper;

import com.bobi89.medicalclinic.model.entity.Patient;
import com.bobi89.medicalclinic.model.entity.PatientDTO;
import com.bobi89.medicalclinic.model.entity.PatientDTOwithPassword;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {

//    PatientMapper PATIENT_MAPPER = Mappers.getMapper((PatientMapper.class));

    PatientDTO toDTO(Patient patient);

//    PatientDTO toDTO(PatientDTOwithPassword patientDTOwithPassword);

    Patient toPatient(PatientDTO patientDTO);

    Patient toPatient(PatientDTOwithPassword patientDTOwithPassword);

}
