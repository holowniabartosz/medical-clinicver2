package com.bobi89.medicalclinic.model.entity.mapper;

import com.bobi89.medicalclinic.model.entity.appointment.Appointment;
import com.bobi89.medicalclinic.model.entity.patient.Patient;
import com.bobi89.medicalclinic.model.entity.patient.PatientDTO;
import com.bobi89.medicalclinic.model.entity.patient.PatientDTOwithPassword;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    @Mapping(source = "appointments", target = "appointmentsDates", qualifiedByName = "appointmentsSetToDateSet")
    PatientDTO toDTO(Patient patient);

    Patient toPatient(PatientDTO patientDTO);

    Patient toPatient(PatientDTOwithPassword patientDTOwithPassword);

    @Named("appointmentsSetToDateSet")
    static Set<LocalDateTime> appointmentsSetToDateSet(Set<Appointment> appointments) {
        if (appointments == null) {
            return new HashSet<>();
        }
        return appointments.stream().map(Appointment::getStartDateTime).collect(Collectors.toSet());
    }
}
