package ru.yandex.main.user;

import ru.yandex.main.event.Event;

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
    List<Event> findUserEventsById(
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
    Event updateUserEventById(
            Long userId,
            Event event
    );

    /**
     * Добавление нового события.
     *
     * @param userId id текущего пользователя,
     * @param event  сущность, добавляемая пользователем;
     * @return Добавленное новое событие.
     */
    Event createUserEvent(
            Long userId,
            Event event
    );

    /**
     * Получение полной информации о событии добавленном текущим пользователем.
     *
     * @param userId  id текущего пользователя
     * @param eventId id событие
     * @return Запрошенное событие
     */
    Event findUserEventByUserIdAndByEventId(
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
    Event eventCancellation(
            Long userId,
            Long eventId
    );

    /**
     * Получение информации о запросах на участие в событии текущего пользователя.
     *
     * @param userId  id текущего пользователя
     * @param eventId id события
     * @return Запрошенный запрос
     */
    Request findUserRequestById(
            Long userId,
            Long eventId
    );

    /**
     * Подтверждение чужой заявки на участие в событии текущего пользователя.
     *
     * @param userId    id текущего пользователя
     * @param eventId   id событие текущего пользователя
     * @param requestId id заявки, которую подтверждает текущий пользователь
     * @return Подтвержденная чужая заявки на участие в событии текущего пользователя
     */
    Request confirmRequestById(
            Long userId,
            Long eventId,
            Long requestId
    );

    /**
     * Отклонение чужой заявки на участие в событии текущего пользователя.
     *
     * @param userId    id текущего пользователя
     * @param eventId   id событие текущего пользователя
     * @param requestId id заявки, которую подтверждает текущий пользователь
     * @return Подтвержденная чужая заявки на участие в событии текущего пользователя
     */
    Request rejectRequestById(
            Long userId,
            Long eventId,
            Long requestId
    );

    /**
     * Получение информации о заявках текущего пользователя на участие в чужих событиях.
     *
     * @param userId id текущего пользователя
     * @return Заявки текущего пользователя
     */
    List<Request> findRequestsById(
            Long userId
    );

    /**
     * Добавление запроса от текущего пользователя на участие в событии.
     *
     * @param userId  id текущего пользователя
     * @param eventId id события
     * @return Добавленный запрос
     */
    Request createRequest(
            Long userId,
            Long eventId
    );

    /**
     * Отмена своего запроса на участие в событии
     *
     * @param userId    id текущего пользователя
     * @param requestId id запроса на участие
     * @return Отмененный запрос
     */
    Request cancelRequest(
            Long userId,
            Long requestId
    );

}
