package com.bobi89.medicalclinic.service.doctor_service;

import com.bobi89.medicalclinic.exception.exc.AppointmentConflictDateException;
import com.bobi89.medicalclinic.exception.exc.EntityNotFoundException;
import com.bobi89.medicalclinic.exception.exc.EntityNullFieldsException;
import com.bobi89.medicalclinic.exception.exc.EntityWithThisIdExistsException;
import com.bobi89.medicalclinic.model.entity.appointment.Appointment;
import com.bobi89.medicalclinic.model.entity.doctor.Doctor;
import com.bobi89.medicalclinic.model.entity.doctor.DoctorDTO;
import com.bobi89.medicalclinic.model.entity.doctor.DoctorDTOwithPassword;
import com.bobi89.medicalclinic.model.entity.mapper.DoctorMapper;
import com.bobi89.medicalclinic.repository.AppointmentRepository;
import com.bobi89.medicalclinic.repository.DoctorJpaRepository;
import com.bobi89.medicalclinic.repository.LocationJpaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private DoctorJpaRepository doctorJpaRepository;
    private DoctorMapper doctorMapper;
    private LocationJpaRepository locationJpaRepository;
    private AppointmentRepository appointmentRepository;

    @Override
    public List<DoctorDTO> findAll() {
        return doctorJpaRepository.findAll().stream()
                .map(d -> doctorMapper.toDTO(d))
                .collect(Collectors.toList());
    }

    @Override
    public DoctorDTO findById(long id) {
        var location = doctorJpaRepository.findById(id);
        if (location.isEmpty()) {
            throw new EntityNotFoundException("No such doctor in the database");
        } else {
            return doctorMapper.toDTO(location.get());
        }
    }

    @Override
    public DoctorDTO save(DoctorDTOwithPassword doctorDTOwithPassword) {
        if (doctorJpaRepository.findByEmail(doctorDTOwithPassword.getEmail()).isPresent()) {
            throw new EntityWithThisIdExistsException("Doctor is already in the database");
        }
        validateIfNull(doctorMapper.toDoctor(doctorDTOwithPassword));
        return doctorMapper.toDTO(doctorJpaRepository
                .save(doctorMapper.toDoctor(doctorDTOwithPassword)));
    }

    @Transactional
    @Override
    public DoctorDTO addLocationToDoctor(long locationId, long doctorId) {
        var doctor = doctorJpaRepository.findById(doctorId);
        var location = locationJpaRepository.findById(locationId);
        if (location.isEmpty() || doctor.isEmpty()) {
            throw new EntityNotFoundException("Location or doctor not found");
        }
        location.get().getDoctors().add(doctor.get());
        locationJpaRepository.save(location.get());
        return doctorMapper.toDTO(doctor.get());
    }

    @Transactional
    @Override
    public DoctorDTO addAppointmentToDoctor(LocalDateTime dateTime, int durationMinutes, long doctorId) {
        var doctor = doctorJpaRepository.findById(doctorId);
        if (doctor.isEmpty()) {
            throw new EntityNotFoundException("Doctor not found");
        }
        if (isSlotAvailable(dateTime, durationMinutes, doctorId)) {
            var appointment = new Appointment(dateTime, durationMinutes, doctor.get());
            appointment.setDoctor(doctor.get());
            appointmentRepository.save(appointment);
            return doctorMapper.toDTO(doctorJpaRepository.findById(doctorId).get());
        } else {
            throw new RuntimeException("!@#$%^$@#!$!~#$%$^%@!$#! XD");
        }
    }

    @Transactional
    @Override
    public DoctorDTO addAppointmentToDoctorSQL(LocalDateTime startDateTime, int durationMinutes, long doctorId) {
        var doctor = doctorJpaRepository.findById(doctorId);
        if (doctor.isEmpty()) {
            throw new EntityNotFoundException("Doctor not found");
        }
        var appointment = new Appointment(startDateTime, durationMinutes, doctor.get());
        doctorJpaRepository.addAppointment(appointment.getStartDateTime(), appointment.getEndDateTime(), appointment.getDuration(), doctorId);
//        if (!doctorJpaRepository.findById(doctorId).get().getAppointments().contains(appointment)) {
//            throw new AppointmentConflictDateException("Slot unavailable");
//        }
        return doctorMapper.toDTO(doctorJpaRepository.findById(doctorId).get());
    }

    private void validateIfNull(Doctor doctor) {
        if (doctor.getEmail() == null ||
                doctor.getPassword() == null ||
                doctor.getFieldOfExpertise() == null) {
            throw new EntityNullFieldsException("None of doctor class fields should be null");
        }
    }

    private boolean isSlotAvailable(LocalDateTime startDateTime, int durationMinutes, long doctorId) {
        var doctor = doctorJpaRepository.findById(doctorId);
        if (doctor.isEmpty()) {
            throw new EntityNotFoundException("Doctor not found");
        }
        var appointment = new Appointment(startDateTime, durationMinutes, doctor.get());
        if (doctor.get().getAppointments()
                .stream().anyMatch(s ->
                        (s.getStartDateTime().isAfter(appointment.getStartDateTime()) &&
                                s.getEndDateTime().isBefore(appointment.getEndDateTime()))
                                ||
                                (s.getStartDateTime().isAfter(appointment.getStartDateTime()) &&
                                        s.getEndDateTime().isAfter(appointment.getEndDateTime()) &&
                                        s.getStartDateTime().isBefore(appointment.getEndDateTime()))
                                ||
                                (s.getStartDateTime().isBefore(appointment.getStartDateTime()) &&
                                        s.getEndDateTime().isBefore(appointment.getEndDateTime()) &&
                                        s.getEndDateTime().isAfter(appointment.getStartDateTime()))
                                ||
                                (s.getStartDateTime().isBefore(appointment.getStartDateTime()) &&
                                        s.getEndDateTime().isAfter(appointment.getEndDateTime()))
                )
        ) {
            throw new AppointmentConflictDateException("Slot unavailable");
        } else return true;
    }
}

