package com.bobi89.medicalclinic.service;

import com.bobi89.medicalclinic.model.entity.doctor.Doctor;
import com.bobi89.medicalclinic.model.entity.doctor.DoctorDTO;
import com.bobi89.medicalclinic.model.entity.doctor.DoctorDTOwithPassword;
import com.bobi89.medicalclinic.model.entity.mapper.DoctorMapper;
import com.bobi89.medicalclinic.model.entity.util.DoctorCreator;
import com.bobi89.medicalclinic.repository.DoctorJpaRepository;
import com.bobi89.medicalclinic.repository.LocationJpaRepository;
import com.bobi89.medicalclinic.service.doctor_service.DoctorService;
import com.bobi89.medicalclinic.service.doctor_service.DoctorServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

class DoctorServiceImplTest {

    private DoctorMapper doctorMapper;
    private DoctorJpaRepository doctorJpaRepository;
    private DoctorService doctorService;
    private LocationJpaRepository locationJpaRepository;


    @BeforeEach
    void setup() {
        this.doctorJpaRepository = Mockito.mock(DoctorJpaRepository.class);
        this.doctorMapper = Mappers.getMapper(DoctorMapper.class);
        this.locationJpaRepository = Mockito.mock(LocationJpaRepository.class);
        this.doctorService = new DoctorServiceImpl(doctorJpaRepository, doctorMapper, locationJpaRepository);
    }

    @Test
    void findAll_doctorsExists_returnDoctors() {
        //given
        List<Doctor> doctors = new ArrayList<>();
        Doctor doctor = DoctorCreator.createDoctor(0,"doctor@gmail.com");
        Doctor doctor2 = DoctorCreator.createDoctor(1, "doctor@gmail.com1");
        doctors.add(doctor);
        doctors.add(doctor2);

        when(doctorJpaRepository.findAll())
                .thenReturn(doctors);

        //when
        var result = doctorService.findAll();

        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("doctor@gmail.com", result.get(0).getEmail());
        Assertions.assertEquals("doctor@gmail.com1", result.get(1).getEmail());
    }

    @Test
    void findByEmail_DoctorExists_DoctorReturned() {
        //given
        Doctor doctor = DoctorCreator.createDoctor(0,"doctor@gmail.com");

        when(doctorJpaRepository.findById(0L))
                .thenReturn(Optional.of(doctor));

        //when
        var result = doctorService.findById(0);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals("doctor@gmail.com", result.getEmail());
        Assertions.assertEquals(0, result.getId());
    }

    @Test
    void save_IfUniqueEmail_DoctorReturned() {
        //given
        long id = 10;
        Doctor doctor = DoctorCreator.createDoctor(id, "doctor@gmail.com");
        DoctorDTOwithPassword doctorDTOwithPassword =
                DoctorCreator.createDoctorDTOwithPassword(id, "doctor@gmail.com");

        when(doctorJpaRepository.findByEmail(doctorDTOwithPassword.getEmail())).thenReturn(Optional.empty());
        when(doctorJpaRepository.save(doctor)).thenReturn(doctor);

        //when
        DoctorDTO updatedDoctor = doctorService.save(doctorDTOwithPassword);

        //then
        Assertions.assertNotNull(updatedDoctor);
        Assertions.assertEquals("doctor@gmail.com", updatedDoctor.getEmail());
        Assertions.assertEquals(10, updatedDoctor.getId());
    }
}