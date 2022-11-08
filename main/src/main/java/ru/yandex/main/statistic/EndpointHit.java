package ru.yandex.main.statistic;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class EndpointHit {
    // Идентификатор сервиса для которого записывается информация
    private String app;

    // URI для которого был осуществлен запрос
    private String uri;

    // IP-адрес пользователя, осуществившего запрос
    private String ip;

    //Дата и время, когда был совершен запрос к эндпоинту (в формате "yyyy-MM-dd HH:mm:ss")
    private String timestamp;
}
