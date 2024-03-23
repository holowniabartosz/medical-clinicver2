package com.bobi89.medicalclinic.model.entity.util;

import com.bobi89.medicalclinic.model.entity.doctor.Doctor;
import com.bobi89.medicalclinic.model.entity.doctor.DoctorDTO;
import com.bobi89.medicalclinic.model.entity.doctor.DoctorDTOwithPassword;
import com.bobi89.medicalclinic.model.entity.doctor.FieldOfExpertise;

public class DoctorCreator {
    public static Doctor createDoctor(long id, String email) {
        return new Doctor(id, email, "1234",
                FieldOfExpertise.OPHTAMOLOGIST);
    }

    public static DoctorDTO createDoctorDTO(long id, String email) {
        return new DoctorDTO(id, email,
                FieldOfExpertise.OPHTAMOLOGIST);
    }

    public static DoctorDTOwithPassword createDoctorDTOwithPassword(long id, String email) {
        return new DoctorDTOwithPassword(id, email, "1234",
                FieldOfExpertise.OPHTAMOLOGIST);
    }
}
