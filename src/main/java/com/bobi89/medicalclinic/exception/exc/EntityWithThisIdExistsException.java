package com.bobi89.medicalclinic.exception.exc;

public class EntityWithThisIdExistsException extends RuntimeException {
    public EntityWithThisIdExistsException(String message){
        super(message);
    }
}
