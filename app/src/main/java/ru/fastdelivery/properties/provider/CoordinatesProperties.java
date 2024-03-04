package ru.fastdelivery.properties.provider;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.fastdelivery.domain.delivery.coordinates.Coordinates;
import ru.fastdelivery.usecase.CoordinatesProvider;

import java.math.BigDecimal;

@ConfigurationProperties("coordinates")
@Setter
public class CoordinatesProperties implements CoordinatesProvider {

    private BigDecimal latitudeMin;
    private BigDecimal latitudeMax;
    private BigDecimal longitudeMin;
    private BigDecimal longitudeMax;

    @Override
    public boolean isCorrectLatitude(BigDecimal coordinatesLatitude) {
        return coordinatesLatitude.compareTo(latitudeMin) >= 0 && coordinatesLatitude.compareTo(latitudeMax) <= 0;
    }

    @Override
    public boolean isCorrectLongitude(BigDecimal coordinatesLongitude) {
        return coordinatesLongitude.compareTo(longitudeMin) >= 0 && coordinatesLongitude.compareTo(longitudeMax) <= 0;
    }

    @Override
    public boolean isCoordinateCorrect(Coordinates coordinates) {
        if (!isCorrectLatitude(coordinates.latitudeDestination()) || !isCorrectLatitude(coordinates.latitudeDeparture())){
            throw new IllegalArgumentException("Latitude coordinates must be between " + latitudeMin + " and " + latitudeMax);
        }
        if (!isCorrectLongitude(coordinates.longitudeDestination()) || !isCorrectLongitude(coordinates.longitudeDeparture())){
            throw new IllegalArgumentException("Longitude coordinates must be between " + longitudeMin + " and " + longitudeMax);
        }
        return true;
    }
}
