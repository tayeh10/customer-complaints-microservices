package com.intuit.apigateway.complaintservice.exception;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    @Getter
    private String errorCode;
    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceNotFoundException.class);

    public ResourceNotFoundException(String errorCode, String resourceName, String fieldName, Object fieldValue){

        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        String errorMsg = String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue);
        LOGGER.error(errorMsg);
        this.errorCode = errorCode;
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
