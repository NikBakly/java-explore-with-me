package ru.yandex.main.statistic;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ViewStats {
    // Название сервиса
    private String app;
    // URI сервиса
    private String uri;
    // Количество просмотров
    private Long hits;
}
