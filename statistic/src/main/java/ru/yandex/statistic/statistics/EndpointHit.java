package ru.yandex.statistic.statistics;

import lombok.Builder;
import lombok.Data;
import ru.yandex.statistic.GlobalVariable;

import javax.validation.constraints.Pattern;

@Builder
@Data
public class EndpointHit {
    // Идентификатор сервиса для которого записывается информация
    private String app;

    // URI для которого был осуществлен запрос
    private String uri;

    // IP-адрес пользователя, осуществившего запрос
    private String ip;

    //Дата и время, когда был совершен запрос к эндпоинту (в формате "yyyy-MM-dd HH:mm:ss")
    @Pattern(regexp = GlobalVariable.PATTERN_DATE, message = "The timestamp field must be in a special format.")
    private String timestamp;
}
