package com.bobi89.medicalclinic.model.entity.mapper;

import com.bobi89.medicalclinic.model.entity.appointment.Appointment;
import com.bobi89.medicalclinic.model.entity.appointment.AppointmentDTO;
import com.bobi89.medicalclinic.model.entity.util.AppointmentCreator;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppointmentMapperTest {

    private AppointmentMapper mapper = Mappers.getMapper(AppointmentMapper.class);

    @Test
    void toDTO_MapsAllFields() {
        LocalDateTime startDateTime = LocalDateTime.of(2030, 12, 25, 18, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2030, 12, 25, 18, 30);
        Appointment appointment = AppointmentCreator.createAppointment(startDateTime, endDateTime);

        AppointmentDTO appointmentDTO = mapper.toDTO(appointment);

        assertEquals(appointment.getStartDateTime(), appointmentDTO.getStartDateTime());
        assertEquals(appointment.getEndDateTime(), appointmentDTO.getEndDateTime());
    }

    @Test
    void toDoctor_MapsAllFields() {
        LocalDateTime startDateTime = LocalDateTime.of(2030, 12, 25, 18, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2030, 12, 25, 18, 30);
        long doctorId = 1;
        AppointmentDTO appointmentDTO = AppointmentCreator.createAppointmentDTO(startDateTime, endDateTime, doctorId);

        Appointment appointment = mapper.toAppointment(appointmentDTO);

        assertEquals(appointmentDTO.getStartDateTime(), appointment.getStartDateTime());
        assertEquals(appointmentDTO.getEndDateTime(), appointment.getEndDateTime());
    }
}