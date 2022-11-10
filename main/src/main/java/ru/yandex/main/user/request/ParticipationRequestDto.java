package ru.yandex.main.user.request;

import lombok.*;

/**
 * Заявка на участие в событии
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
