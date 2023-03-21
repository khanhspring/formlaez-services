package com.formlaez.infrastructure.configuration.exception;

/**
 * @author khanhspring
 */
public class UsageLimitExceededException extends ApplicationException {

    public UsageLimitExceededException() {
        super(CommonError.UsageLimitExceeded);
    }

    public UsageLimitExceededException(String message) {
        super(CommonError.UsageLimitExceeded, message);
    }
}
