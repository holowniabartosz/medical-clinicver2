package com.bobi89.medicalclinic.service;

import com.bobi89.medicalclinic.exception.exc.IncorrectOldPasswordException;
import com.bobi89.medicalclinic.exception.exc.PatientNotFoundException;
import com.bobi89.medicalclinic.exception.exc.PatientNullFieldsException;
import com.bobi89.medicalclinic.exception.exc.PatientWithThisEmailExistsException;
import com.bobi89.medicalclinic.model.entity.ChangePasswordCommand;
import com.bobi89.medicalclinic.model.entity.Patient;
import com.bobi89.medicalclinic.model.entity.PatientDTO;
import com.bobi89.medicalclinic.model.entity.PatientDTOwithPassword;
import com.bobi89.medicalclinic.model.entity.mapper.PatientMapper;
import com.bobi89.medicalclinic.repository.PatientJpaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {

    private PatientJpaRepository patientJpaRepository;
    private PatientMapper patientMapper;

    @Override
    public List<PatientDTO> findAll() {
        var patients = patientJpaRepository.findAll().stream()
                .map(p -> patientMapper.toDTO(p))
                .collect(Collectors.toList());
        return patients;
    }

    @Override
    public PatientDTO findByEmail(String email) {
        var patient = patientJpaRepository.findByEmail(email);
        if (patient.isEmpty()) {
            throw new PatientNotFoundException("No such patient in the database");
        } else {
            return patientMapper.toDTO(patient.get());
        }
    }

    @Override
    public PatientDTO save(PatientDTOwithPassword patientDTOwithPassword) {
        if (patientJpaRepository.findByEmail(patientDTOwithPassword.getEmail()).isPresent()) {
            throw new PatientWithThisEmailExistsException("Patient is already in the database");
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
    public PatientDTO update(String email, PatientDTO patientDTO) {
        Optional<Patient> patientToUpdate = patientJpaRepository.findByEmail(email);
        if (patientToUpdate.isEmpty()) {
            throw new PatientNotFoundException("Patient not found");
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
    public ChangePasswordCommand editPatientPassword(String email, ChangePasswordCommand pass) {
        Optional<Patient> editedPasswordPatient = patientJpaRepository.findByEmail(email);
        if (editedPasswordPatient.isEmpty()) {
            throw new PatientNotFoundException("No such patient in the database");
        }
        if (!editedPasswordPatient.get().getPassword().equals(pass.getOldPassword())) {
            throw new IncorrectOldPasswordException("Old password does not match");
        }
        editedPasswordPatient.get().setPassword(pass.getNewPassword());
        return pass;
    }

    private void validateIfNull(Patient patient) {
        if (patient.getEmail() == null ||
                patient.getPhoneNumber() == null ||
                patient.getFirstName() == null ||
                patient.getLastName() == null ||
                patient.getBirthday() == null) {
            throw new PatientNullFieldsException("None of patient class fields should be null");
        }
    }

//    public void checkIfIdChanged(String email, Patient patient) {
//        if (!patient.getIdCardNr().equals(patientRepository.getPatient(email).getIdCardNr())) {
//            throw new PatientIdChangeException("ID can't be changed");
//        }
//    }
}

