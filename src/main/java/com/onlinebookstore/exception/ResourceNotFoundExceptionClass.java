package com.onlinebookstore.exception;


//A custom exception for missing resources
public class ResourceNotFoundExceptionClass extends RuntimeException {
    public ResourceNotFoundExceptionClass(String message) {
        super(message);
    }
}
