package ru.yandex.main.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
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
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    /**
     * Список стектрейсов или описания ошибок
     */
    List<String> errors;

    /**
     * Cообщение об ошибке
     */
    String message;

    /**
     * Общее описание причины ошибки
     */
    String reason;

    /**
     * Код статуса HTTP-ответа
     */
    @Enumerated(EnumType.STRING)
    StatusError status;

    /**
     * Дата и время когда произошла ошибка (в формате "yyyy-MM-dd HH:mm:ss")
     */
    String timestamp;
}
