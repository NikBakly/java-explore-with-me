package ru.yandex.statistic.statistics;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatisticController {
    private final StatisticService statisticService;

    @PostMapping("/hit")
    void saveStatistic(@RequestBody EndpointHit endpointHit) {
        statisticService.saveStatistic(endpointHit);
    }

    @GetMapping("/stats")
    List<ViewStats> getStatistic(@RequestParam(name = "start") String start,
                                 @RequestParam(name = "end") String end,
                                 @RequestParam(name = "uris", required = false) List<String> uris,
                                 @RequestParam(name = "unique", defaultValue = "false") Boolean unique) {
        return statisticService.findStatisticsByParameters(start, end, uris, unique);
    }
}
