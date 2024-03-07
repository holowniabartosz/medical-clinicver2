package com.bobi89.medicalclinic.exceptions.exc;

public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException(String message) {
        super(message);
    }
}
