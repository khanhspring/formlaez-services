package com.formlaez.infrastructure.configuration.exception;

/**
 * @author khanhspring
 */
public class ForbiddenException extends ApplicationException {

    public ForbiddenException() {
        super(CommonError.Forbidden);
    }
}
