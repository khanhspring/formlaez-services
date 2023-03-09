package com.formlaez.infrastructure.configuration.exception;

/**
 * @author khanhspring
 */
public class DuplicatedException extends ApplicationException {

    public DuplicatedException() {
        super(CommonError.Duplicated);
    }
}
