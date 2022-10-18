package ru.yandex.statistic.endpointHit;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViewStats {
    // Название сервиса
    String app;
    // URI сервиса
    String uri;
    // Количество просмотров
    Integer hits;
}
