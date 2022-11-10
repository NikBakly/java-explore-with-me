package ru.yandex.main.user;

import ru.yandex.main.event.EventFullDto;
import ru.yandex.main.event.EventShortDto;
import ru.yandex.main.event.NewEventDto;
import ru.yandex.main.event.UpdateEventRequest;

import java.util.List;

public interface UserService {

    /**
     * Получение событий, добавленных текущим пользователем.
     *
     * @param userId id текущего пользователя,
     * @param from   количество элементов, которые нужно пропустить для формирования текущего набора,
     * @param size   количество элементов в наборе;
     * @return События, добавленные текущим пользователем
     */
    List<EventShortDto> findUserEventsById(
            Long userId,
            Integer from,
            Integer size
    );

    /**
     * Изменение события добавленного текущим пользователем.
     *
     * @param userId id текущего пользователя,
     * @param event  обновленная сущность;
     * @return Измененное событие
     */
    EventFullDto updateUserEventById(
            Long userId,
            UpdateEventRequest event
    );

    /**
     * Добавление нового события.
     *
     * @param userId id текущего пользователя,
     * @param event  сущность, добавляемая пользователем;
     * @return Добавленное новое событие.
     */
    EventFullDto createUserEvent(
            Long userId,
            NewEventDto newEventDto
    );

    /**
     * Получение полной информации о событии добавленном текущим пользователем.
     *
     * @param userId  id текущего пользователя
     * @param eventId id событие
     * @return Запрошенное событие
     */
    EventFullDto findUserEventByUserIdAndByEventId(
            Long userId,
            Long eventId
    );

    /**
     * Отмена события добавленного текущим пользователем.
     *
     * @param userId  id текущего пользователя
     * @param eventId id отменяемого события
     * @return Отменное событие
     */
    EventFullDto eventCancellation(
            Long userId,
            Long eventId
    );
}
