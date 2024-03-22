package com.bobi89.medicalclinic.model.entity.mapper;

import com.bobi89.medicalclinic.model.entity.appointment.Appointment;
import com.bobi89.medicalclinic.model.entity.appointment.AppointmentDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    AppointmentDTO toDTO(Appointment appointment);
    Appointment toAppointment(AppointmentDTO appointmentDTO);
}
