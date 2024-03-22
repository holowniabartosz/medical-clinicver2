package com.bobi89.medicalclinic.model.entity.mapper;

import com.bobi89.medicalclinic.model.entity.appointment.Appointment;
import com.bobi89.medicalclinic.model.entity.appointment.AppointmentDTO;
import com.bobi89.medicalclinic.model.entity.doctor.Doctor;
import com.bobi89.medicalclinic.model.entity.location.Location;
import com.bobi89.medicalclinic.model.entity.patient.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import javax.print.Doc;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
        if (patient == null) {
            return null;
        } else {
            return patient.getId();
        }
    }

    @Named("doctorToDoctorId")
    static Long doctorToDoctorId(Doctor doctor) {
        if (doctor == null) {
            return null;
        } else {
            return doctor.getId();
        }
    }
}