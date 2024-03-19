package com.bobi89.medicalclinic.model.entity.mapper;

import com.bobi89.medicalclinic.model.entity.doctor.Doctor;
import com.bobi89.medicalclinic.model.entity.doctor.DoctorDTO;
import com.bobi89.medicalclinic.model.entity.doctor.DoctorDTOwithPassword;
import com.bobi89.medicalclinic.model.entity.location.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface DoctorMapper {

    @Mapping(source = "locations",
            target = "locationsNames", qualifiedByName = "locationsSetToNameSet")
    DoctorDTO toDTO(Doctor doctor);
    Doctor toDoctor(DoctorDTO doctorDTO);
    Doctor toDoctor(DoctorDTOwithPassword doctorDTOwithPassword);

    @Named("locationsSetToNameSet")
    static Set<String> locationsSetToNameSet(Set<Location> locations){
        if(locations == null){
            return new HashSet<>();
        }
        else{
            return locations.stream().map(Location::getName).collect(Collectors.toSet());
        }
    }
}
