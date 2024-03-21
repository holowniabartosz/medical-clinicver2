package com.bobi89.medicalclinic.exception.exc;

public class AppointmentConflictDateException extends RuntimeException {
    public AppointmentConflictDateException(String message){
        super(message);
    }
}
