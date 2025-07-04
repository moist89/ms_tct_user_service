package com.interview.technical.exceptions;

public class EmailAlreadyExistsException extends  RuntimeException {


    public EmailAlreadyExistsException(String message) {
        super(message);

    }
}
