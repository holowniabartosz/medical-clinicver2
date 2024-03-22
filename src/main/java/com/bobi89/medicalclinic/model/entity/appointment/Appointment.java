package com.bobi89.medicalclinic.model.entity.appointment;

import com.bobi89.medicalclinic.model.entity.doctor.Doctor;
import com.bobi89.medicalclinic.model.entity.patient.Patient;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@NoArgsConstructor
@Entity
@Data
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;
    private LocalDateTime startDateTime;
    private Duration duration;
    private LocalDateTime endDateTime;

    @ManyToOne
    private Patient patient;

    @ManyToOne
    private Doctor doctor;

    public Appointment(LocalDateTime startDateTime, long durationMinutes, Doctor doctor) {

        AppointmentValidator.validate(startDateTime, durationMinutes, doctor);

        this.startDateTime = startDateTime.truncatedTo(ChronoUnit.MINUTES);
        this.duration = Duration.ofMinutes(durationMinutes);
        this.endDateTime = this.startDateTime.plus(duration).truncatedTo(ChronoUnit.MINUTES);
        this.doctor = doctor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Appointment))
            return false;

        Appointment other = (Appointment) o;

        return id != null &&
                id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}