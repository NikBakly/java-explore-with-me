package ru.yandex.main.user.request;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * Заявка на участие в событии
 */
@Data
@Builder
@ToString
public class ParticipationRequestDto {
    // Дата и время создания заявки
    String created;

    // Идентификатор события
    Long event;

    // Идентификатор заявки
    Long id;

    // Идентификатор пользователя, отправившего заявку
    Long requester;

    // Статус заявки
    StatusRequests status;

}
