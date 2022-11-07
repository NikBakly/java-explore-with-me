package ru.yandex.main.user.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ViewCommentDto {
    private String text;
    private String ownerName;
    private String ownerEmail;
    private TypeOfComment type;
    private String eventUrl;
    private String lastUpdate;
}
