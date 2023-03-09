package com.formlaez.infrastructure.configuration.exception;

/**
 * @author khanhspring
 */
public class ResourceNotFoundException extends ApplicationException {

    public ResourceNotFoundException() {
        super(CommonError.ResourceNotfound);
    }
}
