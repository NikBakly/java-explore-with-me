package ru.yandex.main.user.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.main.event.Event;
import ru.yandex.main.event.EventRepository;
import ru.yandex.main.event.State;
import ru.yandex.main.exception.ForbiddenException;
import ru.yandex.main.exception.NotFoundException;
import ru.yandex.main.user.User;
import ru.yandex.main.user.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ParticipationRequestDto> findUserRequestsById(Long userId, Long eventId) {
        findAndCheckUserById(userId);
        Event foundEvent = findAndCheckEventById(eventId);
        if (!foundEvent.getInitiator().getId().equals(userId)) {
            log.warn("User with id={} cannot view information about event requests " +
                    "because he is not the event organizer", userId);
            throw new ForbiddenException("User with id=" + userId + " cannot view information about event requests " +
                    "because he is not the event organizer");
        }
        List<Request> foundRequests = requestRepository.findAllByEventId(eventId);
        log.info("Information about requests were found successfully for user with id={}", userId);
        return RequestMapper.toParticipationRequestsDto(foundRequests);
    }


    @Override
    @Transactional
    public ParticipationRequestDto confirmRequestById(Long userId, Long eventId, Long requestId) {
        findAndCheckUserById(userId);
        Event foundEvent = findAndCheckEventById(eventId);
        Request foundRequest = findAndCheckRequestById(requestId);

        // проверка на организатора события
        checkInitiatorEvent(userId, eventId, foundEvent);
        // если для события лимит заявок равен 0 или отключена пре-модерация заявок, то подтверждение заявок не требуется
        if (foundEvent.getParticipantLimit() == 0 || foundEvent.getRequestModeration().equals(Boolean.FALSE)) {
            return RequestMapper.toParticipationRequestDto(foundRequest);
        }
        Long confirmedRequests = requestRepository.countRequestByEventIdWhenStatusIsConfirmed(eventId);
        // нельзя подтвердить заявку, если уже достигнут лимит по заявкам на данное событие
        if (foundEvent.getParticipantLimit().equals(confirmedRequests)) {
            log.warn("The limit on applications for this event with id={} is exceeded", eventId);
            throw new ForbiddenException("The limit on applications for this event with id=" + eventId + " is exceeded");
        }
        // если при подтверждении данной заявки, лимит заявок для события исчерпан, то все неподтверждённые заявки необходимо отклонить
        if (confirmedRequests + 1 == foundEvent.getParticipantLimit()) {
            List<Long> requests = requestRepository.findAllByEventIdWhenStatusIsPending(eventId);
            requests.forEach(aLong -> rejectRequestById(userId, eventId, requestId));
        }
        foundRequest.setStatus(StatusRequests.CONFIRMED);
        requestRepository.save(foundRequest);
        log.info("Request with id={} was confirm successfully", requestId);
        return RequestMapper.toParticipationRequestDto(foundRequest);
    }

    @Override
    public ParticipationRequestDto rejectRequestById(Long userId, Long eventId, Long requestId) {
        findAndCheckUserById(userId);
        Event foundEvent = findAndCheckEventById(eventId);
        Request foundRequest = findAndCheckRequestById(requestId);
        checkInitiatorEvent(userId, eventId, foundEvent);
        foundRequest.setStatus(StatusRequests.REJECTED);
        requestRepository.save(foundRequest);
        log.info("Requests with id={} was rejected successfully", requestId);
        return RequestMapper.toParticipationRequestDto(foundRequest);
    }

    @Override
    public List<ParticipationRequestDto> findRequestsById(Long userId) {
        findAndCheckUserById(userId);
        List<Request> requests = requestRepository.findRequestByRequesterId(userId);
        log.info("User's requests with id={} have been successfully found", userId);
        return RequestMapper.toParticipationRequestsDto(requests);
    }

    @Override
    @Transactional
    public ParticipationRequestDto createRequest(Long userId, Long eventId) {
        Event foundEvent = findAndCheckEventById(eventId);
        User foundUser = findAndCheckUserById(userId);
        // нельзя добавить повторный запрос
        if (requestRepository.findRequestByRequesterIdAndEventId(userId, eventId).isPresent()) {
            log.warn("User with id={}cannot add a repeat request", userId);
            throw new ForbiddenException("User with id=" + userId + "cannot add a repeat request");
        }
        // нельзя участвовать в неопубликованном событии
        if (!foundEvent.getState().equals(State.PUBLISHED)) {
            log.warn("User with id={}cannot participate in an unpublished event", userId);
            throw new ForbiddenException("User with id=" + userId + "cannot participate in an unpublished event");
        }
        // если у события достигнут лимит запросов на участие - необходимо вернуть ошибку
        if (foundEvent.getParticipantLimit().equals(requestRepository.countRequestByEventIdWhenStatusIsConfirmed(eventId))) {
            log.warn("The event with id={} has reached the limit of participation requests", eventId);
            throw new ForbiddenException("The event with id=" + eventId + " has reached the limit of participation requests");
        }
        // инициатор события не может добавить запрос на участие в своём событии
        if (foundEvent.getInitiator().getId().equals(userId)) {
            log.warn("The initiator with id={} of the event cannot add a request to participate in his event", userId);
            throw new ForbiddenException("The initiator with id=" + userId + " of the event cannot add a request to participate in his event");
        }
        Request request;
        // если для события отключена пре-модерация запросов на участие, то запрос должен автоматически перейти в состояние подтвержденного
        if (foundEvent.getRequestModeration().equals(Boolean.FALSE) || foundEvent.getParticipantLimit() == 0) {
            request = RequestMapper.toRequest(foundEvent, foundUser, StatusRequests.CONFIRMED);
        } else {
            request = RequestMapper.toRequest(foundEvent, foundUser, StatusRequests.PENDING);
        }
        requestRepository.save(request);
        log.info("Request with id={} was created successfully", request.getId());
        return RequestMapper.toParticipationRequestDto(request);
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        findAndCheckUserById(userId);
        Request foundRequest = findAndCheckRequestById(requestId);
        if (!foundRequest.getRequester().getId().equals(userId)) {
            log.warn("User with id={} wasn't requester request with id={}", userId, requestId);
            throw new ForbiddenException("User with id=" + userId + " wasn't requester request with id=" + requestId);
        }
        foundRequest.setStatus(StatusRequests.CANCELED);
        requestRepository.save(foundRequest);
        log.info("Request with id={} was canceled successfully", requestId);
        return RequestMapper.toParticipationRequestDto(foundRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getNumberOfConfirmedRequests(Long eventId) {
        Long result = requestRepository.countRequestByEventIdWhenStatusIsConfirmed(eventId);
        log.info("Number of confirmed requests was got successfully.");
        return result;
    }

    private Event findAndCheckEventById(Long eventId) {
        Optional<Event> foundEvent = eventRepository.findById(eventId);
        if (foundEvent.isEmpty()) {
            log.warn("Event with id={} was not found.", eventId);
            throw new NotFoundException("Event with id=" + eventId + " was not found.");
        }
        return foundEvent.get();
    }

    private User findAndCheckUserById(Long userId) {
        Optional<User> foundUser = userRepository.findById(userId);
        if (foundUser.isEmpty()) {
            log.warn("User with id={} was not found.", userId);
            throw new NotFoundException("User with id=" + userId + " was not found.");
        }
        return foundUser.get();
    }

    private Request findAndCheckRequestById(Long requestId) {
        Optional<Request> foundRequest = requestRepository.findById(requestId);
        if (foundRequest.isEmpty()) {
            log.warn("Request with id={} was not found.", requestId);
            throw new NotFoundException("Request with id=" + requestId + " was not found.");
        }
        return foundRequest.get();
    }

    private static void checkInitiatorEvent(Long userId, Long eventId, Event foundEvent) {
        if (!foundEvent.getInitiator().getId().equals(userId)) {
            log.warn("User with id={} is not initiator event with id={}", userId, eventId);
            throw new ForbiddenException("User with id=" + userId + " is not initiator event with id=" + eventId);
        }
    }
}
