package ru.yandex.main.user;

import org.springframework.stereotype.Service;
import ru.yandex.main.event.Event;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public List<Event> findUserEventsById(Long userId, Integer from, Integer size) {
        return null;
    }

    @Override
    public Event updateUserEventById(Long userId, Event event) {
        return null;
    }

    @Override
    public Event createUserEvent(Long userId, Event event) {
        return null;
    }

    @Override
    public Event findUserEventByUserIdAndByEventId(Long userId, Long eventId) {
        return null;
    }

    @Override
    public Event eventCancellation(Long userId, Long eventId) {
        return null;
    }

    @Override
    public Request findUserRequestById(Long userId, Long eventId) {
        return null;
    }

    @Override
    public Request confirmRequestById(Long userId, Long eventId, Long requestId) {
        return null;
    }

    @Override
    public Request rejectRequestById(Long userId, Long eventId, Long requestId) {
        return null;
    }

    @Override
    public List<Request> findRequestsById(Long userId) {
        return null;
    }

    @Override
    public Request createRequest(Long userId, Long eventId) {
        return null;
    }

    @Override
    public Request cancelRequest(Long userId, Long requestId) {
        return null;
    }
}
