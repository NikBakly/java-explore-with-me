package ru.yandex.statistic.statistics;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.statistic.GlobalVariable;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticServiceImpl implements StatisticService {
    private final StatisticRepository statisticRepository;

    @Override
    public void saveStatistic(EndpointHit endpointHit) {
        Statistic statistic = StatisticMapper.toStatistic(endpointHit);
        statisticRepository.save(statistic);
        log.info("Statistics with id={} is saved successfully", statistic.getId());
    }

    @Override
    public List<ViewStats> findStatisticsByParameters(String start,
                                                      String end,
                                                      List<String> uris,
                                                      Boolean unique) {
        List<ViewStats> result;
        if (unique) {
            if (uris != null) {
                result = statisticRepository.findStatWhenTimeBetweenAndByUrisAndIpIsUnique(
                        LocalDateTime
                                .parse(start, GlobalVariable.TIME_FORMATTER),
                        LocalDateTime
                                .parse(end, GlobalVariable.TIME_FORMATTER),
                        uris);
            } else {
                result = statisticRepository.findStatWhenTimeBetweenAndIpIsUnique(
                        LocalDateTime
                                .parse(start, GlobalVariable.TIME_FORMATTER),
                        LocalDateTime
                                .parse(end, GlobalVariable.TIME_FORMATTER));
            }
        } else {
            if (uris != null) {
                result = statisticRepository.findStatWhenTimeBetweenAndByUris(
                        LocalDateTime
                                .parse(start, GlobalVariable.TIME_FORMATTER),
                        LocalDateTime
                                .parse(end, GlobalVariable.TIME_FORMATTER),
                        uris);
            } else {
                result = statisticRepository.findStatWhenTimeBetween(
                        LocalDateTime
                                .parse(start, GlobalVariable.TIME_FORMATTER),
                        LocalDateTime
                                .parse(end, GlobalVariable.TIME_FORMATTER));
            }
        }
        return result;
    }

}
