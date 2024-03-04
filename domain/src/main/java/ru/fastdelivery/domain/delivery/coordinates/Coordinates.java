package ru.fastdelivery.domain.delivery.coordinates;

import java.math.BigDecimal;

public record Coordinates(

        BigDecimal latitudeDestination,

        BigDecimal longitudeDestination,

        BigDecimal latitudeDeparture,

        BigDecimal longitudeDeparture
) {
}
