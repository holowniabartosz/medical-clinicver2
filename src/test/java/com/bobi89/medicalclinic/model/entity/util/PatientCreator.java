package com.bobi89.medicalclinic.model.entity.util;

import com.bobi89.medicalclinic.model.entity.Patient;
import com.bobi89.medicalclinic.model.entity.PatientDTO;
import com.bobi89.medicalclinic.model.entity.PatientDTOwithPassword;

public class PatientCreator {
    public static Patient createPatient(long id, String email){
        return new Patient(id,"999", email,"1234",
                "John", "Doe", "123456789", "25/12/2000");
    }

    public static PatientDTO createPatientDTO(long id, String email){
        return new PatientDTO(id,"999", email,
                "John", "Doe", "123456789", "25/12/2000");
    }

    public static PatientDTOwithPassword createPatientDTOwithPassword(long id, String email){
        return new PatientDTOwithPassword(id,"999", email,
                "John", "Doe", "123456789", "25/12/2000", "1234");
    }
}
