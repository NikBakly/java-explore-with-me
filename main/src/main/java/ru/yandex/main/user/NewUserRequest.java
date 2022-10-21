package ru.yandex.main.user;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewUserRequest {
    String email;
    String name;
}
