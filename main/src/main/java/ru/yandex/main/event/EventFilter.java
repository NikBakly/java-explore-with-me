package ru.yandex.main.event;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@Setter
@ToString
public class EventFilter {
    private final String text;
    private final List<Long> categories;
    private final Boolean paid;
    private final String rangeStart;
    private final String rangeEnd;
    private final Boolean onlyAvailable;
    private final TypesOfSort sort;
    private final Integer from;
    private final Integer size;
}
