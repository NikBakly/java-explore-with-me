package ru.yandex.main.admin.event;

import ru.yandex.main.event.AdminUpdateEventRequest;
import ru.yandex.main.event.EventFullDto;

import java.util.List;

public interface AdminEventService {

    /**
     * Поиск событий по переданным параметрам.
     *
     * @param users      список id пользователей, чьи события нужно найти
     * @param states     список состояний в которых находятся искомые события
     * @param categories список id категорий в которых будет вестись поиск
     * @param rangeStart дата и время не раньше которых должно произойти событие
     * @param rangeEnd   дата и время не позже которых должно произойти событие
     * @param from       количество событий, которые нужно пропустить для формирования текущего набора
     * @param size       количество событий в наборе
     * @return Событие подходящие под параметры
     */
    List<EventFullDto> findEvents(
            int[] users,
            String[] states,
            int[] categories,
            String rangeStart,
            String rangeEnd,
            Integer from,
            Integer size
    );

    /**
     * Редактирование события.
     *
     * @param eventId id события
     * @param event   данные для изменения информации о событии
     * @return Измененное событие
     */
    EventFullDto updateEvent(
            Long eventId,
            AdminUpdateEventRequest event
    );

    /**
     * Публикация события.
     *
     * @param eventId id события
     * @return Опубликованное событие
     */
    EventFullDto publishEvent(
            Long eventId
    );

    /**
     * Отклонение события.
     *
     * @param eventId id события
     * @return Отклоненное событие
     */
    EventFullDto rejectedEvent(
            Long eventId
    );
}
