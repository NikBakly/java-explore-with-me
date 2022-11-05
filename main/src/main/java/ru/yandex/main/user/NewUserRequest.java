package ru.yandex.main.user;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class NewUserRequest {
    @NotBlank(message = "The email field cannot be blank")
    @Email(message = "The email field doesn't look like email")
    String email;
    @NotBlank(message = "The name field cannot be blank")
    String name;
}
