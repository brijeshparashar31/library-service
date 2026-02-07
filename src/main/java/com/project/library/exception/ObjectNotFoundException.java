package com.project.library.exception;

/**
 * Exception class  to handle Project NOT found exception.
 */
public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException(String message) {
        super(message);
    }
}
