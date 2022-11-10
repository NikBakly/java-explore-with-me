package ru.yandex.statistic;

import java.time.format.DateTimeFormatter;

public class GlobalVariable {

    public static final String PATTERN_DATE = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


}
