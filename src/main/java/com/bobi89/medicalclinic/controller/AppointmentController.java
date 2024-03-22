package com.bobi89.medicalclinic.controller;

import com.bobi89.medicalclinic.model.entity.appointment.AppointmentDTO;
import com.bobi89.medicalclinic.model.entity.appointment.AppointmentRequest;
import com.bobi89.medicalclinic.service.appointment_service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping
    public List<AppointmentDTO> findAll() {
        return appointmentService.findAll();
    }

    @GetMapping("/{id}")
    public AppointmentDTO findById(@PathVariable long id) {
        return appointmentService.findById(id);
    }

    @PostMapping("/add-appointment-to-doctor/{doctorId}")
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentDTO addAppointmentToDoctor(@RequestBody AppointmentRequest appointmentRequest,
                                                 @PathVariable long doctorId) {
        return appointmentService.addAppointmentToDoctor(appointmentRequest.getLocalDateTime(),
                Duration.ofMinutes(appointmentRequest.getDurationMinutes()), doctorId);
    }

    @PatchMapping("/{appointmentId}")
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentDTO addPatientToAppointment(@PathVariable long appointmentId, @RequestBody long patientId) {
        return appointmentService.addPatientToAppointment(appointmentId, patientId);
    }
}