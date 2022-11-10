package ru.yandex.main;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GlobalVariable {

    public static final String PATTERN_DATE = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final String MAIN_APP = "ewm-main-service";

    public static final Integer FIVE_YEARS = 5;
}
