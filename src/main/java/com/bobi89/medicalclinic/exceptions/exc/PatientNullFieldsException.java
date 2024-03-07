package com.bobi89.medicalclinic.exceptions.exc;

public class PatientNullFieldsException extends RuntimeException {
    public PatientNullFieldsException(String message){
        super(message);
    }
}
