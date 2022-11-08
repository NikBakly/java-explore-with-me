package ru.yandex.main.admin.event;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.main.event.State;

import java.util.List;

@Builder
@Getter
@Setter
@ToString
public class EventFilterAdmin {
    private List<Long> users;
    private List<State> states;
    private List<Long> categories;
    private String rangeStart;
    private String rangeEnd;
    private Integer from;
    private Integer size;
}
