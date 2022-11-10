package ru.yandex.statistic.statistics;

import java.util.List;

public interface StatisticService {

    /**
     * Сохранение информации о том, что на uri конкретного сервиса был отправлен запрос пользователем.
     * Название сервиса, uri и ip пользователя указаны в теле запроса.
     *
     * @param endpointHit данные запроса
     */
    void saveStatistic(EndpointHit endpointHit);

    /**
     * Получение статистики по посещениям. Обратите внимание: значение даты и времени нужно закодировать
     *
     * @param start  Дата и время начала диапазона за который нужно выгрузить статистику
     * @param end    Дата и время конца диапазона за который нужно выгрузить статистику
     * @param uris   Список uri для которых нужно выгрузить статистику
     * @param unique Нужно ли учитывать только уникальные посещения (только с уникальным ip)
     * @return Собранная статистика
     */
    List<ViewStats> findStatisticsByParameters(String start, String end, List<String> uris, Boolean unique);
}
