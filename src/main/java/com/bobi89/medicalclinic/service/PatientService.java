package com.bobi89.medicalclinic.service;

import com.bobi89.medicalclinic.exception.exc.PatientNotFoundException;
import com.bobi89.medicalclinic.exception.exc.PatientNullFieldsException;
import com.bobi89.medicalclinic.model.entity.ChangePasswordCommand;
import com.bobi89.medicalclinic.model.entity.Patient;
import com.bobi89.medicalclinic.model.entity.PatientDTO;
import com.bobi89.medicalclinic.model.entity.mapper.PatientMapper;
import com.bobi89.medicalclinic.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class PatientService {

    private PatientRepository patientRepository;
    private PatientMapper patientMapper;

    public HashSet<PatientDTO> getPatients() {
        HashSet<Patient> patients = patientRepository.getAllPatientData();
        HashSet<PatientDTO> patientsDTO = new HashSet<>();
        for (Patient p : patients){
            patientsDTO.add(patientMapper.toDTO(p));
        }
        return patientsDTO;
    }

    public PatientDTO getPatient(String email) {
        Patient nonDto = patientRepository.getPatient(email);
        return patientMapper.toDTO(nonDto);
    }

    public PatientDTO addPatient(PatientDTO patientDTO) {
        patientRepository.addPatient(patientMapper.toPatient(patientDTO));
        return patientDTO;
    }

    public void removePatient(String email) {
        patientRepository.removePatient(email);
    }

    public PatientDTO editPatient(String email, PatientDTO patientDTO) {
//        checkIfIdChanged(email, Patient.toPatient(patientDTO));
        validateIfNull(patientMapper.toPatient(patientDTO));
        Patient editedPatient = patientRepository.editPatient
                (email, patientMapper.toPatient(patientDTO));
        return patientMapper.toDTO(editedPatient);
    }

    public ChangePasswordCommand editPatientPassword(String email, ChangePasswordCommand pass) {
        var editedPasswordPatient = patientRepository.getPatient(email);
        if (editedPasswordPatient == null) {
            throw new PatientNotFoundException("Patient not found");
        }
        patientRepository.editPatientPassword(email, pass);
        return pass;
    }

    public void validateIfNull(Patient patient) {
        if (patient.getEmail() == null ||
                patient.getPhoneNumber() == null ||
                patient.getFirstName() == null ||
                patient.getLastName() == null) {
            throw new PatientNullFieldsException("None of patient class fields should be null");
        }
    }

//    public void checkIfIdChanged(String email, Patient patient) {
//        if (!patient.getIdCardNr().equals(patientRepository.getPatient(email).getIdCardNr())) {
//            throw new PatientIdChangeException("ID can't be changed");
//        }
//    }
}

