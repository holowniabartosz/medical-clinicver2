package com.bobi89.medicalclinic.exception;

import com.bobi89.medicalclinic.exception.exc.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EntityRestExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(EntityNotFoundException exc){
        // create a PatientErrorResponse

        ErrorResponse error = new ErrorResponse();

        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage("Entity not found");
        error.setTimestamp(System.currentTimeMillis());

        // return ResponseEntity

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityWithThisIdExistsException.class)
    public ResponseEntity<ErrorResponse> handleException(EntityWithThisIdExistsException exc) {

        ErrorResponse error = new ErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage("Entity with this ID already exists");
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityWithThisEmailExistsException.class)
    public ResponseEntity<ErrorResponse> handleException(EntityWithThisEmailExistsException exc){

        ErrorResponse error = new ErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage("Entity with this email already exists");
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectOldPasswordException.class)
    public ResponseEntity<ErrorResponse> handleException(IncorrectOldPasswordException exc){

        ErrorResponse error = new ErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage("Provided old password is incorrect");
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNullFieldsException.class)
    public ResponseEntity<ErrorResponse> handleException(EntityNullFieldsException exc){

        ErrorResponse error = new ErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage("Entity fields cannot be empty");
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

//     default handler here
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<PatientErrorResponse> handleException(Exception exc){
//
//        PatientErrorResponse error = new PatientErrorResponse();
//
//        error.setStatus(HttpStatus.BAD_REQUEST.value());
//        error.setMessage("Malformed request");
//        error.setTimestamp(System.currentTimeMillis());
//
//        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//    }
}
