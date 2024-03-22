package com.bobi89.medicalclinic.model.entity.mapper;

import com.bobi89.medicalclinic.model.entity.doctor.Doctor;
import com.bobi89.medicalclinic.model.entity.location.Location;
import com.bobi89.medicalclinic.model.entity.location.LocationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    @Mapping(source = "doctors",
            target = "doctorsEmails", qualifiedByName = "doctorsSetToIdSet")
    LocationDTO toDTO(Location location);

    Location toLocation(LocationDTO locationDTO);

    @Named("doctorsSetToIdSet")
    static Set<String> locationListToIdList(Set<Doctor> doctors) {
        if (doctors == null) {
            return new HashSet<>();
        } else {
            return doctors.stream().map(Doctor::getEmail).collect(Collectors.toSet());
        }
    }
}