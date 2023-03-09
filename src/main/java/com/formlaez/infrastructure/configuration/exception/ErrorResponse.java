package com.formlaez.infrastructure.configuration.exception;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author khanhspring
 */
@Data
@Builder
public class ErrorResponse {
    private String code;
    private String message;
    private List<ErrorDetail> details;

    public static class ErrorDetail {
        private String field;
        private String message;
    }
}
