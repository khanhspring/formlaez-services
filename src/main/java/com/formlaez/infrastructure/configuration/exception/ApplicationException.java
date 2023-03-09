package com.formlaez.infrastructure.configuration.exception;

import lombok.Getter;

/**
 * @author khanhspring
 */
@Getter
public class ApplicationException extends RuntimeException {

    private String code;
    private String message;

    public ApplicationException(Error error) {
        super(error.getMessage());
        this.code = error.getCode();
        this.message = error.getMessage();
    }

    public ApplicationException(String message) {
        super(message);
        this.message = message;
        this.code = CommonError.SystemBusy.getCode();
    }

    public ApplicationException(Throwable throwable) {
        super(throwable);
        this.message = CommonError.SystemBusy.getMessage();
        this.code = CommonError.SystemBusy.getCode();
    }
}
