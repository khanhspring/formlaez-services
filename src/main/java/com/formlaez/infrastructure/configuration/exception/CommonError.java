package com.formlaez.infrastructure.configuration.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author khanhspring
 */
@Getter
@AllArgsConstructor
public enum CommonError implements Error {

    SystemBusy("999", "Something went wrong"),
    Unauthorized("998", "Unauthorized"),
    Forbidden("997", "Forbidden"),
    MissingParams("996", "Missing parameters"),
    ResourceNotfound("995", "Resource not found"),
    Duplicated("994", "Duplicated"),
    InvalidParams("993", "Invalid parameters"),
    UsageLimitExceeded("992", "Usage limit exceeded");

    private final String code;
    private final String message;

}
