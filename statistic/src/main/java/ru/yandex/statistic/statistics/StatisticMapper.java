package ru.yandex.statistic.statistics;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yandex.statistic.GlobalVariable;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StatisticMapper {

    public static Statistic toStatistic(EndpointHit endpointHit) {
        Statistic newStatistic = new Statistic();
        newStatistic.setIp(endpointHit.getIp());
        newStatistic.setApp(endpointHit.getApp());
        newStatistic.setUri(endpointHit.getUri());
        newStatistic.setTime(LocalDateTime.parse(endpointHit.getTimestamp(), GlobalVariable.TIME_FORMATTER));
        return newStatistic;
    }
}
