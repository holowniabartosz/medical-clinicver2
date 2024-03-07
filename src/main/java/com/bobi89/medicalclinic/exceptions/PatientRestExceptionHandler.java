package com.bobi89.medicalclinic.exceptions;

import com.bobi89.medicalclinic.exceptions.exc.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PatientRestExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<PatientErrorResponse> handleException(PatientNotFoundException exc){
        // create a PatientErrorResponse

        PatientErrorResponse error = new PatientErrorResponse();

        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage("Patient not found");
        error.setTimestamp(System.currentTimeMillis());

        // return ResponseEntity

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<PatientErrorResponse> handleException(PatientWithThisEmailExistsException exc){

        PatientErrorResponse error = new PatientErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage("Patient with this email already exists");
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<PatientErrorResponse> handleException(IncorrectOldPasswordException exc){

        PatientErrorResponse error = new PatientErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage("Provided old password is incorrect");
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<PatientErrorResponse> handleException(PatientIdChangeException exc){

        PatientErrorResponse error = new PatientErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage("Cannot change patient ID");
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<PatientErrorResponse> handleException(PatientNullFieldsException exc){

        PatientErrorResponse error = new PatientErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage("Patient fields cannot be empty");
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // default handler here
    @ExceptionHandler
    public ResponseEntity<PatientErrorResponse> handleException(Exception exc){

        PatientErrorResponse error = new PatientErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage("Malformed request");
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
