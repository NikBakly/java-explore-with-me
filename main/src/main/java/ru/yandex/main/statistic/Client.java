package ru.yandex.main.statistic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.yandex.main.exception.BadRequestException;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class Client {
    private final RestTemplate rest = new RestTemplate();

    @Value("${explore-statistic.url}")
    private String serverUrl;

    // Сохраняет новые статистические данные
    public void addStatistic(EndpointHit endpointHit) {
        ResponseEntity<Object> result = post(endpointHit);
        if (result.getStatusCode().is2xxSuccessful()) {
            log.info("Statistic was saved successfully");
        } else {
            log.warn("Statistic wasn't saved");
        }
    }

    // находит список по одному определенному url
    public List<ViewStats> findByUrl(String start,
                                     String end,
                                     String url,
                                     Boolean unique) {
        Map<String, Object> parameters = Map.of(
                "start", start,
                "end", end,
                "uris", url,
                "unique", unique
        );
        ResponseEntity<List<ViewStats>> response = get(parameters);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            log.warn("ViewStat wasn't got");
            throw new BadRequestException("ViewStat wasn't got");
        }
    }

    private ResponseEntity<Object> post(EndpointHit body) {
        HttpEntity<EndpointHit> requestEntity = new HttpEntity<>(body, defaultHeaders());

        return rest.exchange(
                serverUrl + "/hit",
                HttpMethod.POST,
                requestEntity,
                Object.class);
    }

    private ResponseEntity<List<ViewStats>> get(Map<String, Object> parameters) {
        HttpEntity<EndpointHit> requestEntity = new HttpEntity<>(null, defaultHeaders());
        return rest.exchange(serverUrl + "/stats?start={start}&end={end}&uris={uris}&unique={unique}",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<ViewStats>>() {},
                parameters);
    }

    private HttpHeaders defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }
}