package com.bobi89.medicalclinic.exception.exc;

public class IncorrectOldPasswordException extends RuntimeException {
    public IncorrectOldPasswordException(String message) {
        super(message);
    }
}
