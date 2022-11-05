package ru.yandex.main.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.main.GlobalVariable;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError handleForbidden(final ForbiddenException e) {
        return ApiError.builder()
                .message(e.getMessage())
                .reason("For the requested operation the conditions are not met.")
                .status(StatusError.FORBIDDEN)
                .timestamp(getNowTime())
                .build();
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBadRequestException(final BadRequestException e) {
        return ApiError.builder()
                .message(e.getMessage())
                .reason("For the requested operation the conditions are not met.")
                .status(StatusError.BAD_REQUEST)
                .timestamp(getNowTime())
                .build();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(final NotFoundException e) {
        return ApiError.builder()
                .message(e.getMessage())
                .reason("The required object was not found.")
                .status(StatusError.NOT_FOUND)
                .timestamp(getNowTime())
                .build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleConstraintViolationException(final ConstraintViolationException e) {
        log.warn(e.getMessage());
        return ApiError.builder()
                .message(e.getMessage())
                .reason("Error occurred.")
                .status(StatusError.INTERNAL_SERVER_ERROR)
                .timestamp(getNowTime())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.warn(e.getMessage());
        return ApiError.builder()
                .message(e.getMessage())
                .reason("For the requested operation the conditions are not met.")
                .status(StatusError.BAD_REQUEST)
                .timestamp(getNowTime())
                .build();
    }

    @ExceptionHandler(org.hibernate.exception.ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConstraintViolationException(final org.hibernate.exception.ConstraintViolationException e) {
        log.warn(e.getMessage());
        return ApiError.builder()
                .message(e.getMessage())
                .reason("Integrity constraint has been violated")
                .status(StatusError.CONFLICT)
                .timestamp(getNowTime())
                .build();
    }

    private String getNowTime() {
        return LocalDateTime.now().format(GlobalVariable.TIME_FORMATTER);
    }
}
