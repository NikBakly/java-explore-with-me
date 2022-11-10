package ru.yandex.statistic.statistics;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "statistics")
@Getter
@Setter
@ToString
public class Statistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    @Column(name = "timestamp")
    private LocalDateTime time;
}
