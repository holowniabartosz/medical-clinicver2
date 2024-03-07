package com.bobi89.medicalclinic.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientErrorResponse {
    private int status;
    private String message;
    private long timestamp;
}