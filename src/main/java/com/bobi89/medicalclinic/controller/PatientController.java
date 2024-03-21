package com.bobi89.medicalclinic.controller;

import com.bobi89.medicalclinic.model.entity.appointment.AppointmentRequest;
import com.bobi89.medicalclinic.model.entity.patient.ChangePasswordCommand;
import com.bobi89.medicalclinic.model.entity.patient.PatientDTO;
import com.bobi89.medicalclinic.model.entity.patient.PatientDTOwithPassword;
import com.bobi89.medicalclinic.service.patient_service.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/patients")
public class PatientController {

    private PatientService patientService;

    @GetMapping
    public List<PatientDTO> findAll() {
        return patientService.findAll();
    }

    @GetMapping("/{email}")
    public PatientDTO findByEmail(@PathVariable String email) {
        return patientService.findByEmail(email);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatientDTO save(@RequestBody PatientDTOwithPassword patientDTOwithPassword) {
        return patientService
                .save(patientDTOwithPassword);
    }

    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteByEmail(@PathVariable String email) {
        patientService
                .deleteByEmail(email);
        return "Removed patient with e-mail: " + email;
    }

    @PutMapping("/{email}")
    public PatientDTO update(@PathVariable String email, @RequestBody PatientDTO patientDTO) {
        patientService.update(email, patientDTO);
        return patientService.findByEmail(patientDTO.getEmail());
    }

    @PatchMapping("password/{email}")
    public ChangePasswordCommand updatePatientPassword(@PathVariable String email,
                                                       @RequestBody ChangePasswordCommand pass) {
        patientService.editPatientPassword(email, pass);
        return pass;
    }

    @PostMapping("/{patientId}/{doctorId}/fitSlot")
    @ResponseStatus(HttpStatus.CREATED)
    public PatientDTO addAppointmentToPatient(@RequestBody AppointmentRequest appointmentRequest,
                                            @PathVariable long patientId, @PathVariable long doctorId) {
        return patientService.addAppointmentToPatient(appointmentRequest.getLocalDateTime(),
                appointmentRequest.getDurationMinutes(), patientId, doctorId);
    }
}

