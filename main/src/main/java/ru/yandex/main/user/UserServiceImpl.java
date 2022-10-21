package ru.yandex.main.user;

import org.springframework.stereotype.Service;
import ru.yandex.main.event.*;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public List<EventShortDto> findUserEventsById(Long userId, Integer from, Integer size) {
        return null;
    }

    @Override
    public UpdateEventRequest updateUserEventById(Long userId, Event event) {
        return null;
    }

    @Override
    public EventFullDto createUserEvent(Long userId, NewEventDto event) {
        return null;
    }

    @Override
    public EventFullDto findUserEventByUserIdAndByEventId(Long userId, Long eventId) {
        return null;
    }

    @Override
    public EventFullDto eventCancellation(Long userId, Long eventId) {
        return null;
    }

    @Override
    public ParticipationRequestDto findUserRequestById(Long userId, Long eventId) {
        return null;
    }

    @Override
    public ParticipationRequestDto confirmRequestById(Long userId, Long eventId, Long requestId) {
        return null;
    }

    @Override
    public ParticipationRequestDto rejectRequestById(Long userId, Long eventId, Long requestId) {
        return null;
    }

    @Override
    public List<ParticipationRequestDto> findRequestsById(Long userId) {
        return null;
    }

    @Override
    public ParticipationRequestDto createRequest(Long userId, Long eventId) {
        return null;
    }

    @Override
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        return null;
    }
}
