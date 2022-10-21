package ru.yandex.main.event;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    //todo Реализовать бизнес логику
    @Override
    public List<EventShortDto> getAll(String text, int[] arr, Boolean paid, String rangeStart, String rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size) {
        return null;
    }

    @Override
    public EventFullDto findById(Long eventId) {
        return null;
    }
}
