package ru.yandex.main.admin.event;

import ru.yandex.main.event.AdminUpdateEventRequest;
import ru.yandex.main.event.EventFullDto;

import java.util.List;

public class AdminEventServiceImpl implements AdminEventService {
    @Override
    public List<EventFullDto> findEvents(int[] users, String[] states, int[] categories, String rangeStart, String rangeEnd, Integer from, Integer size) {
        return null;
    }

    @Override
    public EventFullDto updateEvent(Long eventId, AdminUpdateEventRequest event) {
        return null;
    }

    @Override
    public EventFullDto publishEvent(Long eventId) {
        return null;
    }

    @Override
    public EventFullDto rejectedEvent(Long eventId) {
        return null;
    }
}
