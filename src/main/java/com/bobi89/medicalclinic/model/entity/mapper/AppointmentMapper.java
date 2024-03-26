package com.bobi89.medicalclinic.model.entity.mapper;

import com.bobi89.medicalclinic.model.entity.appointment.Appointment;
import com.bobi89.medicalclinic.model.entity.appointment.AppointmentDTO;
import com.bobi89.medicalclinic.model.entity.doctor.Doctor;
import com.bobi89.medicalclinic.model.entity.patient.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(source = "patient",
            target = "patientId", qualifiedByName = "patientToPatientId")
    @Mapping(source = "doctor",
            target = "doctorId", qualifiedByName = "doctorToDoctorId")
    AppointmentDTO toDTO(Appointment appointment);

    Appointment toAppointment(AppointmentDTO appointmentDTO);

    @Named("patientToPatientId")
    static Long patientToPatientId(Patient patient) {
        return patient == null ? null : patient.getId();
    }

    @Named("doctorToDoctorId")
    static Long doctorToDoctorId(Doctor doctor) {
        return doctor == null ? null : doctor.getId();
    }
}