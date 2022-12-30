package ru.practicum.shareit.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.validation.ValidationException;



@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFoundException(final NotFoundException e) {
        log.info(HttpStatus.NOT_FOUND.toString() + " {}", e.getMessage());
        return new ErrorResponse(HttpStatus.NOT_FOUND.toString(), e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse notValidateException(final ValidationException e) {
        log.info(HttpStatus.CONFLICT.toString() + " {}", e.getMessage());
        return new ErrorResponse(HttpStatus.CONFLICT.toString(), e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse ArgumentNotValidateException(final MethodArgumentNotValidException e) {
        log.info(HttpStatus.BAD_REQUEST.toString() + " {}", e.getMessage());
        return new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse throwable(final Throwable e) {
        log.info(HttpStatus.INTERNAL_SERVER_ERROR.toString() + " {}", e.getMessage());
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage());
    }

}