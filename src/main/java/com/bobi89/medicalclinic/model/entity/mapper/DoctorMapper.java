package com.bobi89.medicalclinic.model.entity.mapper;

import com.bobi89.medicalclinic.model.entity.appointment.Appointment;
import com.bobi89.medicalclinic.model.entity.doctor.Doctor;
import com.bobi89.medicalclinic.model.entity.doctor.DoctorDTO;
import com.bobi89.medicalclinic.model.entity.doctor.DoctorDTOwithPassword;
import com.bobi89.medicalclinic.model.entity.location.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface DoctorMapper {

    @Mapping(source = "locations",
            target = "locationsNames", qualifiedByName = "locationsSetToNameSet")
    @Mapping(source = "appointments",
            target = "appointmentsDates", qualifiedByName = "appointmentsSetToDateSet")
    DoctorDTO toDTO(Doctor doctor);

    Doctor toDoctor(DoctorDTO doctorDTO);

    Doctor toDoctor(DoctorDTOwithPassword doctorDTOwithPassword);

    @Named("locationsSetToNameSet")
    static Set<String> locationsSetToNameSet(Set<Location> locations) {
        if (locations == null) {
            return new HashSet<>();
        } else {
            return locations.stream().map(Location::getName).collect(Collectors.toSet());
        }
    }

    @Named("appointmentsSetToDateSet")
    static Set<LocalDateTime> appointmentsSetToDateSet(Set<Appointment> appointments) {
        if (appointments == null) {
            return new HashSet<>();
        } else {
            return appointments.stream().map(Appointment::getStartDateTime).collect(Collectors.toSet());
        }
    }
}
