package com.bobi89.medicalclinic.exception.exc;

public class PatientWithThisEmailExistsException extends RuntimeException {
    public PatientWithThisEmailExistsException(String message){
        super(message);
    }
}
