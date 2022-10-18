package ru.yandex.main.admin.services;

import ru.yandex.main.event.Event;

import java.util.List;

public class AdminEventServiceImpl implements AdminEventService {
    @Override
    public List<Event> findEvents(int[] users, String[] states, int[] categories, String rangeStart, String rangeEnd, Integer from, Integer size) {
        return null;
    }

    @Override
    public Event updateEvent(Long eventId, Event event) {
        return null;
    }

    @Override
    public Event publishEvent(Long eventId) {
        return null;
    }

    @Override
    public Event rejectedEvent(Long eventId) {
        return null;
    }
}
