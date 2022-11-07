package ru.yandex.main.user.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
public class NewCommentDto {
    @NotNull(message = "The text field cannot be undefined")
    @Length(min = 10, max = 1000, message = "The text field should be between 10 and 1000 in length")
    private String text;
    private TypeOfComment type;
}
