package com.bobi89.medicalclinic.exception.exc;

public class EntityWithThisEmailExistsException extends RuntimeException {
    public EntityWithThisEmailExistsException(String message){
        super(message);
    }
}
