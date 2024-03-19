package com.bobi89.medicalclinic.repository;

import com.bobi89.medicalclinic.model.entity.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationJpaRepository extends JpaRepository<Location, Long> {
    Optional<Location> findByName(String name);

}
