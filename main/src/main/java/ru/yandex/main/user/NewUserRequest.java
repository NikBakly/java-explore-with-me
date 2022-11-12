package ru.yandex.main.user;


import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NewUserRequest {
    @NotBlank(message = "The email field cannot be blank")
    @Email(message = "The email field doesn't look like email")
    @Size(max = 511)
    String email;

    @NotBlank(message = "The name field cannot be blank")
    @Size(max = 255)
    String name;
}
