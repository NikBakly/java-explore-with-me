package ru.yandex.main.user.request;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yandex.main.GlobalVariable;
import ru.yandex.main.event.Event;
import ru.yandex.main.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RequestMapper {
    public static ParticipationRequestDto toParticipationRequestDto(Request request) {
        return ParticipationRequestDto.builder()
                .created(request.getCreated().format(GlobalVariable.TIME_FORMATTER))
                .event(request.getEvent().getId())
                .id(request.getId())
                .requester(request.getRequester().getId())
                .status(request.getStatus())
                .build();
    }

    public static List<ParticipationRequestDto> toParticipationRequestsDto(List<Request> requests) {
        List<ParticipationRequestDto> result = new ArrayList<>();
        for (Request request : requests) {
            result.add(toParticipationRequestDto(request));
        }
        return result;
    }

    public static Request toRequest(Event event, User requester, StatusRequests statusRequests) {
        Request request = new Request();
        request.setEvent(event);
        request.setRequester(requester);
        request.setStatus(statusRequests);
        request.setCreated(LocalDateTime.now());
        return request;
    }
}
