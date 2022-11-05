package ru.yandex.main.event;

import lombok.Data;

import java.util.List;

@Data
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
