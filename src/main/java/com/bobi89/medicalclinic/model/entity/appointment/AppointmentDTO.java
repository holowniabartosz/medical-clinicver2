package com.bobi89.medicalclinic.model.entity.appointment;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
public class AppointmentDTO {

    private Long id;
    private LocalDateTime startDateTime;
    private Duration duration;
    private LocalDateTime endDateTime;

    private Long patientId;

    private Long doctorId;

    public AppointmentDTO(LocalDateTime startDateTime, long durationMinutes, long doctorId) {

        AppointmentDTOValidator.validate(startDateTime, durationMinutes);

        this.startDateTime = startDateTime.truncatedTo(ChronoUnit.MINUTES);
        this.duration = Duration.ofMinutes(durationMinutes);
        this.endDateTime = this.startDateTime.plus(duration).truncatedTo(ChronoUnit.MINUTES);
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