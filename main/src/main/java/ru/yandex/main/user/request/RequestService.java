package ru.yandex.main.user.request;

import java.util.List;

public interface RequestService {


    /**
     * Получение информации о запросах на участие в событии текущего пользователя.
     *
     * @param userId  id текущего пользователя
     * @param eventId id события
     * @return Запрошенный запрос
     */
    List<ParticipationRequestDto> findUserRequestsById(
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
    ParticipationRequestDto confirmRequestById(
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
    ParticipationRequestDto rejectRequestById(
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
    List<ParticipationRequestDto> findRequestsById(
            Long userId
    );

    /**
     * Добавление запроса от текущего пользователя на участие в событии.
     *
     * @param userId  id текущего пользователя
     * @param eventId id события
     * @return Добавленный запрос
     */
    ParticipationRequestDto createRequest(
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
    ParticipationRequestDto cancelRequest(
            Long userId,
            Long requestId
    );

    /**
     * @param eventId идентификатор события
     * @return количество подтвержденных заявок пользователя
     */
    Long getNumberOfConfirmedRequests(Long eventId);
}
