package ru.yandex.main;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Широта и долгота места проведения события
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Location {
    // Широта
    private final Double lat;

    // Долгота
    private final Double lon;

}
