package com.bobi89.medicalclinic.model.entity.appointment;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AppointmentRequest {
    private LocalDateTime localDateTime;
    private long durationMinutes;
    private long doctorId;
}
