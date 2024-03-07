package com.bobi89.medicalclinic.exceptions.exc;

public class PatientWithThisEmailExistsException extends RuntimeException {
    public PatientWithThisEmailExistsException(String message){
        super(message);
    }
}
