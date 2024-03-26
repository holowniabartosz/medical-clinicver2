package com.bobi89.medicalclinic.exception;

import com.bobi89.medicalclinic.exception.exc.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EntityRestExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(EntityNotFoundException exc) {

        ErrorResponse error = errorResponseGenerator(
                "Entity not found",
                HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityWithThisIdExistsException.class)
    public ResponseEntity<ErrorResponse> handleException(EntityWithThisIdExistsException exc) {

        ErrorResponse error = errorResponseGenerator(
                "Entity with this ID already exists",
                HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityWithThisEmailExistsException.class)
    public ResponseEntity<ErrorResponse> handleException(EntityWithThisEmailExistsException exc) {

        ErrorResponse error = errorResponseGenerator(
                "Entity with this email already exists",
                HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectOldPasswordException.class)
    public ResponseEntity<ErrorResponse> handleException(IncorrectOldPasswordException exc) {

        ErrorResponse error = errorResponseGenerator(
                "Provided old password is incorrect",
                HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNullFieldsException.class)
    public ResponseEntity<ErrorResponse> handleException(EntityNullFieldsException exc) {

        ErrorResponse error = errorResponseGenerator(
                "Entity fields cannot be empty",
                HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DateInThePastException.class)
    public ResponseEntity<ErrorResponse> handleException(DateInThePastException exc) {

        ErrorResponse error = errorResponseGenerator(
                "Date is in the past",
                HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AppointmentConflictDateException.class)
    public ResponseEntity<ErrorResponse> handleException(AppointmentConflictDateException exc) {

        ErrorResponse error = errorResponseGenerator(
                "This appointment's date is incorrect",
                HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AppointmentNotQuarterException.class)
    public ResponseEntity<ErrorResponse> handleException(AppointmentNotQuarterException exc) {

        ErrorResponse error = errorResponseGenerator(
                "Appointment's duration must be rounded up to a quarter of an hour",
                HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse errorResponseGenerator(String message, HttpStatus httpStatus) {
        ErrorResponse error = new ErrorResponse();

        error.setStatus(httpStatus.value());
        error.setMessage(message);
        error.setTimestamp(System.currentTimeMillis());

        return error;
    }
}
