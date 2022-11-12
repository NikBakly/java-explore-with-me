package ru.yandex.main.statistic;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class ViewStats {
    // Название сервиса
    private String app;
    // URI сервиса
    private String uri;
    // Количество просмотров
    private Long hits;
}
