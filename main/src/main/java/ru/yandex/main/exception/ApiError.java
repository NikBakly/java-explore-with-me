package ru.yandex.main.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

/**
 * Сведения об ошибке
 */
@Getter
@Setter
@ToString
public class ApiError {
    List<String> errors;

    String message;

    String reason;

    @Enumerated(EnumType.STRING)
    StatusError status;

    String timestamp;
}
