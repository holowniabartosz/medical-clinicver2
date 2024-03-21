package com.bobi89.medicalclinic.service.patient_service;

import com.bobi89.medicalclinic.exception.exc.*;
import com.bobi89.medicalclinic.model.entity.appointment.Appointment;
import com.bobi89.medicalclinic.model.entity.mapper.PatientMapper;
import com.bobi89.medicalclinic.model.entity.patient.ChangePasswordCommand;
import com.bobi89.medicalclinic.model.entity.patient.Patient;
import com.bobi89.medicalclinic.model.entity.patient.PatientDTO;
import com.bobi89.medicalclinic.model.entity.patient.PatientDTOwithPassword;
import com.bobi89.medicalclinic.repository.AppointmentRepository;
import com.bobi89.medicalclinic.repository.DoctorJpaRepository;
import com.bobi89.medicalclinic.repository.PatientJpaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {

    private PatientJpaRepository patientJpaRepository;
    private PatientMapper patientMapper;
    private AppointmentRepository appointmentRepository;
    private DoctorJpaRepository doctorJpaRepository;


    @Override
    public List<PatientDTO> findAll() {
        return patientJpaRepository.findAll().stream()
                .map(p -> patientMapper.toDTO(p))
                .collect(Collectors.toList());
    }

    @Override
    public PatientDTO findByEmail(String email) {
        var patient = patientJpaRepository.findByEmail(email);
        if (patient.isEmpty()) {
            throw new EntityNotFoundException("No such patient in the database");
        } else {
            return patientMapper.toDTO(patient.get());
        }
    }

    @Override
    public PatientDTO save(PatientDTOwithPassword patientDTOwithPassword) {
        if (patientJpaRepository.findByEmail(patientDTOwithPassword.getEmail()).isPresent()) {
            throw new EntityWithThisEmailExistsException("Patient is already in the database");
        }
        validateIfNull(patientMapper.toPatient(patientDTOwithPassword));
        return patientMapper.toDTO(patientJpaRepository
                .save(patientMapper.toPatient(patientDTOwithPassword)));
    }

    @Override
    public void deleteByEmail(String email) {
        patientJpaRepository.delete(patientMapper.toPatient(findByEmail(email)));
    }

    @Transactional
    @Override
    public PatientDTO update(String email, PatientDTO patientDTO) {
        Optional<Patient> patientToUpdate = patientJpaRepository.findByEmail(email);
        if (patientToUpdate.isEmpty()) {
            throw new EntityNotFoundException("Patient not found");
        } else {
            patientToUpdate.map(updatedPatient -> {
                updatedPatient.update(patientMapper.toPatient(patientDTO));
                patientJpaRepository.save(updatedPatient);
                return updatedPatient;
            });
            return patientDTO;
        }
    }

    @Transactional
    @Override
    public ChangePasswordCommand editPatientPassword(String email, ChangePasswordCommand pass) {
        Optional<Patient> editedPasswordPatient = patientJpaRepository.findByEmail(email);
        if (editedPasswordPatient.isEmpty()) {
            throw new EntityNotFoundException("No such patient in the database");
        }
        if (!editedPasswordPatient.get().getPassword().equals(pass.getOldPassword())) {
            throw new IncorrectOldPasswordException("Old password does not match");
        }
        editedPasswordPatient.get().setPassword(pass.getNewPassword());
        return pass;
    }

    @Transactional
    @Override
    public PatientDTO addAppointmentToPatient(LocalDateTime dateTime, int durationMinutes,
                                              long patientId, long doctorId) {
        var patient = patientJpaRepository.findById(patientId);
        var doctor = doctorJpaRepository.findById(doctorId);

        if (patient.isEmpty() || doctor.isEmpty()) {
            throw new EntityNotFoundException("Patient or doctor not found");
        }
        var appointments = appointmentRepository.findAll();
        var requestedAppointment = new Appointment(dateTime, durationMinutes, doctor.get());

        var targetAppointment = appointments.stream().filter(s ->
                        ((s.getStartDateTime().isBefore(requestedAppointment.getStartDateTime().plusSeconds(1)))
                        &&
                        (s.getEndDateTime().isAfter(requestedAppointment.getEndDateTime().minusSeconds(1)))
                        && s.getPatient() == null))
                .findFirst();
        if (targetAppointment.isEmpty()){
            throw new EntityNotFoundException("No doctor's appointment at this particular date and duration");
        }
        targetAppointment.get().setPatient(patient.get());
        appointmentRepository.save(targetAppointment.get());
        return patientMapper.toDTO(patient.get());
    }

    private void validateIfNull(Patient patient) {
        if (patient.getEmail() == null ||
                patient.getPhoneNumber() == null ||
                patient.getFirstName() == null ||
                patient.getLastName() == null ||
                patient.getBirthday() == null) {
            throw new EntityNullFieldsException("None of patient class fields should be null");
        }
    }
}

