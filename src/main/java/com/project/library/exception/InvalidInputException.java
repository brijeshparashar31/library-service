package com.project.library.exception;

/**
 * Exception class to handle exceptions related to the Input Payload.
 */
public class InvalidInputException extends RuntimeException {

    public InvalidInputException(String message) {
        super(message);
    }
}
