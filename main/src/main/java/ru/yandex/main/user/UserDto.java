package ru.yandex.main.user;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class UserDto {
    String email;
    Long id;
    String name;
}
