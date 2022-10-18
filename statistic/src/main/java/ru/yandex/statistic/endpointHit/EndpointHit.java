package ru.yandex.statistic.endpointHit;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "endpoint_hit")
@Getter
@Setter
@ToString
public class EndpointHit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // Идентификатор сервиса для которого записывается информация
    String app;

    // URI для которого был осуществлен запрос
    String uri;

    // IP-адрес пользователя, осуществившего запрос
    String ip;

    //Дата и время, когда был совершен запрос к эндпоинту (в формате "yyyy-MM-dd HH:mm:ss")
    @Column(name = "timestamp")
    LocalDateTime time;
}
