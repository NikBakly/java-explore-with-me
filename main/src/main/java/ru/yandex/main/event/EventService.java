package ru.yandex.main.event;

import java.util.List;

public interface EventService {

    /**
     * @param text          текст для поиска в содержимом аннотации и подробном описании события,
     * @param arr           список идентификаторов категорий в которых будет вестись поиск,
     * @param paid          поиск только платных/бесплатных событий,
     * @param rangeStart    дата и время не раньше которых должно произойти событие,
     * @param rangeEnd      дата и время не позже которых должно произойти событие,
     * @param onlyAvailable только события у которых не исчерпан лимит запросов на участие,
     * @param sort          Вариант сортировки: по дате события или по количеству просмотров. EVENT_DATE, VIEWS СООТВЕТСТВЕННО,
     * @param from          количество событий, которые нужно пропустить для формирования текущего набора,
     * @param size          количество событий в наборе;
     * @return Получение событий с возможностью фильтрации.
     */
    List<Event> getAll(
            String text,
            int[] arr,
            Boolean paid,
            String rangeStart,
            String rangeEnd,
            Boolean onlyAvailable,
            String sort,
            Integer from,
            Integer size
    );

    /**
     * @param eventId id события;
     * @return Получение подробной информации об опубликованном событии по его идентификатору.
     */
    Event findById(Long eventId);
}
