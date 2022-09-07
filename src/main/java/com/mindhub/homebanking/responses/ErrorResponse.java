package com.mindhub.homebanking.responses;

public class ErrorResponse {

    private String message;
    private int code;

    public ErrorResponse() {
    }

    public ErrorResponse(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public ErrorResponse(Exception e, int code) {
        this(e.getMessage(), code);
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
