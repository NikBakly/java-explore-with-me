package ru.yandex.statistic.statistics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Long> {

    // Метод для получения статистики
    @Query("select new ru.yandex.statistic.statistics.ViewStats(s.app, s.uri, count(s.ip))" +
            "from Statistic s " +
            "where s.time between ?1 and ?2 and s.uri in (?3) " +
            "group by s.app, s.uri")
    List<ViewStats> findStatWhenTimeBetweenAndByUris(LocalDateTime start, LocalDateTime end, List<String> uris);

    // Метод для получения статистики с уникальным ip
    @Query("select new ru.yandex.statistic.statistics.ViewStats(s.app, s.uri, count(distinct s.ip))" +
            "from Statistic s " +
            "where s.time between ?1 and ?2 and s.uri in (?3) " +
            "group by s.app, s.uri")
    List<ViewStats> findStatWhenTimeBetweenAndByUrisAndIpIsUnique(LocalDateTime start, LocalDateTime end, List<String> uris);

    // Метод для получения статистики без определенного uri
    @Query("select new ru.yandex.statistic.statistics.ViewStats(s.app, s.uri, count(s.ip))" +
            "from Statistic s " +
            "where s.time between ?1 and ?2 " +
            "group by s.app, s.uri")
    List<ViewStats> findStatWhenTimeBetween(LocalDateTime start, LocalDateTime end);

    //Метод лдя получения статистики без определенного uri, но с уникальным ip
    @Query("select new ru.yandex.statistic.statistics.ViewStats(s.app, s.uri, count(distinct s.ip))" +
            "from Statistic s " +
            "where s.time between ?1 and ?2 " +
            "group by s.app, s.uri")
    List<ViewStats> findStatWhenTimeBetweenAndIpIsUnique(LocalDateTime start, LocalDateTime end);
}
