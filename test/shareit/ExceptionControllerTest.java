package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.exception.*;

import javax.validation.ValidationException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ExceptionControllerTest {
    @Autowired
    private ErrorHandler controller;

    @Test
    void testNotFoundException() {
        ErrorResponse responseEntity = controller.notFoundException(new NotFoundException());
        Assertions.assertNotNull(responseEntity);
        Assertions.assertNotNull(responseEntity.getCode());
    }

    @Test
    void testNotValidateException() {
        ErrorResponse responseEntity = controller.notValidateException(new ValidationException("ValidationException"));
        Assertions.assertNotNull(responseEntity);
        Assertions.assertNotNull(responseEntity.getCode());
        Assertions.assertNotNull(responseEntity.getError());
    }

    @Test
    void testBadRequestException() {
        ErrorResponse responseEntity = controller.badRequestException(new BadRequestException("BadRequestException"));
        Assertions.assertNotNull(responseEntity);
        Assertions.assertNotNull(responseEntity.getCode());
        Assertions.assertNotNull(responseEntity.getError());
    }

    @Test
    void testBadRequestExceptionIsEmpty() {
        ErrorResponse responseEntity = controller.badRequestException(new BadRequestException());
        Assertions.assertNotNull(responseEntity);
        Assertions.assertNotNull(responseEntity.getCode());
    }

    @Test
    void testBadRequestExceptionStateException() {
        ErrorResponse responseEntity = controller.badRequestException(new StateException("StateException"));
        Assertions.assertNotNull(responseEntity);
        Assertions.assertNotNull(responseEntity.getCode());
        Assertions.assertNotNull(responseEntity.getError());
    }

    @Test
    void testThrowable() {
        ErrorResponse responseEntity = controller.throwable(new Throwable("Throwable"));
        Assertions.assertNotNull(responseEntity);
        Assertions.assertNotNull(responseEntity.getCode());
        Assertions.assertNotNull(responseEntity.getError());
    }

}