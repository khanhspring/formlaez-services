package com.formlaez.infrastructure.configuration.exception;

/**
 * @author khanhspring
 */
public class UnauthorizedException extends ApplicationException {

    public UnauthorizedException() {
        super(CommonError.Unauthorized);
    }
}
