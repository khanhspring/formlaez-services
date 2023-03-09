package com.formlaez.infrastructure.configuration.exception;

/**
 * @author khanhspring
 */
public class InvalidParamsException extends ApplicationException {

    public InvalidParamsException() {
        super(CommonError.InvalidParams);
    }
}
