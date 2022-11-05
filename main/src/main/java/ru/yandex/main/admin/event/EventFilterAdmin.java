package ru.yandex.main.admin.event;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.yandex.main.GlobalVariable;
import ru.yandex.main.event.State;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.List;

@Builder
@Getter
@Setter
public class EventFilterAdmin {
    private List<Long> users;
    private List<State> states;
    private List<Long> categories;
    @Pattern(regexp = GlobalVariable.PATTERN_DATE, message = "The range start field must be in a special format.")
    private String rangeStart;
    @Pattern(regexp = GlobalVariable.PATTERN_DATE, message = "The range end field must be in a special format.")
    private String rangeEnd;
    @Min(value = 0, message = "The from field cannot be negative")
    private Integer from;
    @Min(value = 1, message = "The size field cannot be negative or zero")
    private Integer size;
}
