package ru.yandex.statistic.statistics;

import lombok.*;
import ru.yandex.statistic.GlobalVariable;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EndpointHit {
    // Идентификатор сервиса для которого записывается информация
    @Size(max = 255)
    private String app;

    // URI для которого был осуществлен запрос
    @Size(max = 511)
    private String uri;

    // IP-адрес пользователя, осуществившего запрос
    @Size(max = 15)
    private String ip;

    //Дата и время, когда был совершен запрос к эндпоинту (в формате "yyyy-MM-dd HH:mm:ss")
    @Pattern(regexp = GlobalVariable.PATTERN_DATE, message = "The timestamp field must be in a special format.")
    private String timestamp;
}
