package com.formlaez.infrastructure.configuration.exception;

/**
 * @author khanhspring
 */
public class MissingParamsException extends ApplicationException {

    public MissingParamsException() {
        super(CommonError.MissingParams);
    }
}
