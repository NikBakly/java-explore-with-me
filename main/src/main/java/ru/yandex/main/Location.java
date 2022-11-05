package ru.yandex.main;

import lombok.Data;

/**
 * Широта и долгота места проведения события
 */
@Data
public class Location {
    // Широта
    private final Double lat;

    // Долгота
    private final Double lon;

}
