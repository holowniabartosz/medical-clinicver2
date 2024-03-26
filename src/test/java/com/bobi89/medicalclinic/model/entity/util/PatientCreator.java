package com.bobi89.medicalclinic.model.entity.util;

import com.bobi89.medicalclinic.model.entity.patient.Patient;
import com.bobi89.medicalclinic.model.entity.patient.PatientDTO;
import com.bobi89.medicalclinic.model.entity.patient.PatientDTOwithPassword;

import java.time.LocalDate;

public class PatientCreator {
    public static Patient createPatient(long id, String email) {
        return new Patient(id, "999", email, "1234",
                "John", "Doe", "123456789", LocalDate.of(2000, 12, 25));
    }

    public static PatientDTO createPatientDTO(long id, String email) {
        return new PatientDTO(id, "999", email,
                "John", "Doe", "123456789", LocalDate.of(2000, 12, 25));
    }

    public static PatientDTOwithPassword createPatientDTOwithPassword(long id, String email) {
        return new PatientDTOwithPassword(id, "999", email,
                "John", "Doe", "123456789", LocalDate.of(2000, 12, 25), "1234");
    }
}
