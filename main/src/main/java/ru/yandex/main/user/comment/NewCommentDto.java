package ru.yandex.main.user.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Getter
@Setter
public class NewCommentDto {
    @NotNull(message = "The text field cannot be undefined")
    @Size(min = 10, max = 1000, message = "The text field should be between 10 and 1000 in length")
    private String text;
    private TypeOfComment type;
}
