package ru.fastdelivery.usecase;

import ru.fastdelivery.domain.delivery.coordinates.Coordinates;

import java.math.BigDecimal;

public interface CoordinatesProvider {

    boolean isCorrectLatitude(BigDecimal coordinatesLatitude);

    boolean isCorrectLongitude(BigDecimal coordinatesLongitude);

    boolean isCoordinateCorrect(Coordinates coordinates);
}
