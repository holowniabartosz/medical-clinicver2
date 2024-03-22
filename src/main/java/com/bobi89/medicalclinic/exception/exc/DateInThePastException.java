package com.bobi89.medicalclinic.exception.exc;

public class DateInThePastException extends RuntimeException {
    public DateInThePastException(String message) {
        super(message);
    }
}
