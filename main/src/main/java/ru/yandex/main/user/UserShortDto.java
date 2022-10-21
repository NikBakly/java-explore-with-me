package ru.yandex.main.user;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class UserShortDto {
    Long id;
    String name;
}
