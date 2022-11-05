package ru.yandex.main.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yandex.main.GlobalVariable;
import ru.yandex.main.Location;
import ru.yandex.main.category.Category;
import ru.yandex.main.category.CategoryMapper;
import ru.yandex.main.compilation.Compilation;
import ru.yandex.main.user.User;
import ru.yandex.main.user.UserMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EventMapper {
    public static EventShortDto toEventShortDto(Event event, Long views, Long confirmedRequests) {
        return EventShortDto.builder()
                .id(event.getId())
                .eventDate(event.getEventDate().format(GlobalVariable.TIME_FORMATTER))
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .title(event.getTitle())
                .paid(event.getPaid())
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .confirmedRequests(confirmedRequests)
                .views(views)
                .build();
    }

    public static EventFullDto toEventFullDto(Event event, Long views, Long confirmedRequests) {
        EventFullDto result = new EventFullDto();
        result.setAnnotation(event.getAnnotation());
        result.setCategory(CategoryMapper.toCategoryDto(event.getCategory()));
        result.setConfirmedRequests(confirmedRequests);
        result.setCreatedOn(event.getCreatedOn().format(GlobalVariable.TIME_FORMATTER));
        result.setDescription(event.getDescription());
        result.setEventDate(event.getEventDate().format(GlobalVariable.TIME_FORMATTER));
        result.setId(event.getId());
        result.setInitiator(UserMapper.toUserShortDto(event.getInitiator()));
        result.setPaid(event.getPaid());
        result.setParticipantLimit(event.getParticipantLimit());
        if (event.getPublishedOn() != null) {
            result.setPublishedOn(event.getPublishedOn().format(GlobalVariable.TIME_FORMATTER));
        }
        result.setLocation(new Location(event.getLat(), event.getLon()));
        result.setRequestModeration(event.getRequestModeration());
        result.setState(event.getState());
        result.setTitle(event.getTitle());
        result.setViews(views);

        return result;
    }

    public static Event toEvent(NewEventDto newEventDto, Category category, User initiator) {
        Event event = new Event();
        event.setAnnotation(newEventDto.getAnnotation());
        event.setCategory(category);
        event.setInitiator(initiator);
        event.setDescription(newEventDto.getDescription());
        event.setEventDate(LocalDateTime.parse(newEventDto.getEventDate(), GlobalVariable.TIME_FORMATTER));
        event.setCreatedOn(LocalDateTime.now());
        event.setLat(newEventDto.getLocation().getLat());
        event.setLon(newEventDto.getLocation().getLon());
        event.setPaid(newEventDto.getPaid());
        event.setParticipantLimit(newEventDto.getParticipantLimit());
        event.setRequestModeration(newEventDto.getRequestModeration());
        event.setTitle(newEventDto.getTitle());
        return event;
    }

    public static List<EventShortDto> toEventsShortDto(Compilation compilation, Long view, Long confirmedRequests) {
        List<EventShortDto> eventsShortDto = new ArrayList<>();
        compilation.getEvents().forEach(event -> eventsShortDto.add(
                EventMapper
                        .toEventShortDto(
                                event,
                                view,
                                confirmedRequests)));
        return eventsShortDto;
    }
}
