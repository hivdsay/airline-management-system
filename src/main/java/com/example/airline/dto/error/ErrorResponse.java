package com.example.airline.dto.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Error response format")
public class ErrorResponse {

    @Schema(description = "Error code", example = "USER_NOT_FOUND")
    private String errorCode;

    @Schema(description = "Error message", example = "User not found")
    private String message;

    @Schema(description = "Timestamp", example = "2024-12-12T10:30:00")
    private LocalDateTime timestamp;

    @Schema(description = "Detailed error information")
    private Object details;

    // Compatible with your existing constructor
    public ErrorResponse(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(String errorCode, String message, Object details) {
        this.errorCode = errorCode;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.details = details;
    }
}