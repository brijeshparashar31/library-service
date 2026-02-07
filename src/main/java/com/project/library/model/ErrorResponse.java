package com.project.library.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Error response object returned by APIs in case of any exception.
 */
@Data
@Builder
public class ErrorResponse {
    private String errorCode;
    private String errorMessage;
    private List<String> errors;
}
