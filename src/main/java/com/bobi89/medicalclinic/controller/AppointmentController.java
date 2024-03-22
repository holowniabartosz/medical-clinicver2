package com.bobi89.medicalclinic.controller;

import com.bobi89.medicalclinic.model.entity.appointment.AppointmentDTO;
import com.bobi89.medicalclinic.model.entity.appointment.AppointmentRequest;
import com.bobi89.medicalclinic.service.appointment_service.AppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/appointments")
public class AppointmentController {

    private AppointmentService appointmentService;

    @GetMapping
    public List<AppointmentDTO> findAll() {
        return appointmentService.findAll();
    }

    @GetMapping("/{id}")
    public AppointmentDTO findById(@PathVariable long id) {
        return appointmentService.findById(id);
    }

    @PostMapping("/{doctorId}/schedule-appointment-slot")
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentDTO addAppointmentToDoctor(@RequestBody AppointmentRequest appointmentRequest,
                                            @PathVariable long doctorId) {
        return appointmentService.addAppointmentToDoctor(appointmentRequest.getLocalDateTime(),
                appointmentRequest.getDurationMinutes(), doctorId);
    }

    @PatchMapping("/patient/{patientId}/wanted-doctor/{doctorId}")
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentDTO addPatientToAppointment(@RequestBody AppointmentRequest appointmentRequest,
                                              @PathVariable long patientId, @PathVariable long doctorId) {
        return appointmentService.addPatientToAppointment(appointmentRequest.getLocalDateTime(),
                appointmentRequest.getDurationMinutes(), patientId, doctorId);
    }
}