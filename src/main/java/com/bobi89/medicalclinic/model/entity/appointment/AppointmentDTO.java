package com.bobi89.medicalclinic.model.entity.appointment;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentDTO {

    private Long id;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Long patientId;
    private Long doctorId;

    public AppointmentDTO(LocalDateTime startDateTime, LocalDateTime endDateTime, long doctorId) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.doctorId = doctorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof AppointmentDTO))
            return false;

        AppointmentDTO other = (AppointmentDTO) o;

        return id != null &&
                id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}