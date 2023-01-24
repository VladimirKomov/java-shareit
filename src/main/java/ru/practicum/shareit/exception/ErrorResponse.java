package ru.practicum.shareit.exception;


public class ErrorResponse {
    String code;
    String error;

    public ErrorResponse(String code, String error) {
        this.code = code;
        this.error = error;
    }

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

}
