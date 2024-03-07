package com.bobi89.medicalclinic.exceptions.exc;

public class IncorrectOldPasswordException extends RuntimeException {
    public IncorrectOldPasswordException (String message){
        super(message);
    }
}
