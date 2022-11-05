package ru.yandex.main.event;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EventService {

    /**
     * @param eventFilter хранение параметров для поиска
     * @param request     информация об приходящем запросе
     * @return Получение событий с возможностью фильтрации.
     */
    List<EventShortDto> getAll(EventFilter eventFilter, HttpServletRequest request);

    /**
     * @param eventId id события;
     * @param request информация об приходящем запросе
     * @return Получение подробной информации об опубликованном событии по его идентификатору.
     */
    EventFullDto findById(Long eventId, HttpServletRequest request);
}
