package com.bobi89.medicalclinic.model.entity.mapper;

import com.bobi89.medicalclinic.model.entity.doctor.Doctor;
import com.bobi89.medicalclinic.model.entity.doctor.DoctorDTO;
import com.bobi89.medicalclinic.model.entity.doctor.DoctorDTOwithPassword;
import com.bobi89.medicalclinic.model.entity.util.DoctorCreator;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoctorMapperTest {
    private DoctorMapper mapper = Mappers.getMapper(DoctorMapper.class);

    @Test
    void toDTO_MapsAllFields() {

        Doctor doctor = DoctorCreator.createDoctor(0, "doctor@gmail.com");

        DoctorDTO doctorDTO = mapper.toDTO(doctor);

        assertEquals(doctor.getEmail(), doctorDTO.getEmail());
        assertEquals(doctor.getId(), doctorDTO.getId());
        assertEquals(doctor.getFieldOfExpertise(), doctorDTO.getFieldOfExpertise());
    }

    @Test
    void toDoctor_MapsAllFields() {
        DoctorDTO doctorDTO = DoctorCreator.createDoctorDTO(0, "doctor@gmail.com");

        Doctor doctor = mapper.toDoctor(doctorDTO);

        assertEquals(doctorDTO.getEmail(), doctor.getEmail());
        assertEquals(doctorDTO.getId(), doctor.getId());
        assertEquals(doctorDTO.getFieldOfExpertise(), doctor.getFieldOfExpertise());
    }

    @Test
    void toDoctorWithPassword_MapsAllFieldsIncludingPassword() {
        DoctorDTOwithPassword doctorDTOwithPassword =
                DoctorCreator.createDoctorDTOwithPassword(10, "doctor@gmail.com");

        Doctor doctor = mapper.toDoctor(doctorDTOwithPassword);

        assertEquals(doctorDTOwithPassword.getEmail(), doctor.getEmail());
        assertEquals(doctorDTOwithPassword.getId(), doctor.getId());
        assertEquals(doctorDTOwithPassword.getPassword(), doctor.getPassword());
        assertEquals(doctorDTOwithPassword.getFieldOfExpertise(), doctor.getFieldOfExpertise());
    }


}
