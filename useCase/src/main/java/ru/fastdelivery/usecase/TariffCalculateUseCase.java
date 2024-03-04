package ru.fastdelivery.usecase;

import lombok.RequiredArgsConstructor;
import ru.fastdelivery.domain.common.packproperties.price.Price;
import ru.fastdelivery.domain.delivery.coordinates.Coordinates;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

import javax.inject.Named;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Named
@RequiredArgsConstructor
public class TariffCalculateUseCase {
    private final WeightPriceProvider weightPriceProvider;
    private final VolumePriceProvider volumePriceProvider;
    private final CoordinatesProvider coordinatesProvider;

    public Price calc(Shipment shipment, Coordinates coordinates) {
        var weightAllPackagesKg = shipment.weightAllPackages().kilograms();
        var minimalPriceForWeight = weightPriceProvider.minimalPriceForWeight();
        var volumeAllPackages = shipment.volumeAllPackages();
        var minimalPriceForVolume = volumePriceProvider.minimalPriceForVolume();

        var totalPriceForWeight = weightPriceProvider
                .costPerKg()
                .multiply(weightAllPackagesKg)
                .max(minimalPriceForWeight)
                .setScale(2);

        var totalPriceForVolume = volumePriceProvider
                .costPerM3()
                .multiply(volumeAllPackages.getPackVolume())
                .max(minimalPriceForVolume);
        System.out.println(totalPriceForWeight);
        System.out.println(totalPriceForVolume);

        coordinatesProvider.isCoordinateCorrect(coordinates);
        BigDecimal dist = calculateDistance(coordinates);

        if (totalPriceForVolume.amount().compareTo(totalPriceForWeight.amount()) > 0){
            return totalPriceForVolume.multiply(dist).setScale(2);
        } else {
            return totalPriceForWeight.multiply(dist).setScale(2);
        }
    }


    public Price minimalPrice() {
        if (volumePriceProvider.minimalPriceForVolume().amount().compareTo(weightPriceProvider.minimalPriceForWeight().amount()) > 0){
            return volumePriceProvider.minimalPriceForVolume();
        } else {
            return weightPriceProvider.minimalPriceForWeight();
        }
    }

    private BigDecimal calculateDistance(Coordinates coordinates){

        //Формула расчета расстояния на сайте: https://gis-lab.info/qa/great-circles.html
        BigDecimal rad = BigDecimal.valueOf(6373);

        // В радианах
        BigDecimal lat1 = coordinates.latitudeDestination().multiply(BigDecimal.valueOf(Math.PI)).divide(BigDecimal.valueOf(180), RoundingMode.HALF_UP);
        BigDecimal lat2 = coordinates.latitudeDeparture().multiply(BigDecimal.valueOf(Math.PI)).divide(BigDecimal.valueOf(180), RoundingMode.HALF_UP);
        BigDecimal long1 = coordinates.longitudeDestination().multiply(BigDecimal.valueOf(Math.PI)).divide(BigDecimal.valueOf(180), RoundingMode.HALF_UP);
        BigDecimal long2 = coordinates.longitudeDeparture().multiply(BigDecimal.valueOf(Math.PI)).divide(BigDecimal.valueOf(180), RoundingMode.HALF_UP);

        // Косинусы и синусы широт и разницы долгот
        BigDecimal cl1 = BigDecimal.valueOf(Math.cos(lat1.doubleValue()));
        BigDecimal cl2 = BigDecimal.valueOf(Math.cos(lat2.doubleValue()));
        BigDecimal sl1 = BigDecimal.valueOf(Math.sin(lat1.doubleValue()));
        BigDecimal sl2 = BigDecimal.valueOf(Math.sin(lat2.doubleValue()));
        BigDecimal delta = long2.subtract(long1);
        BigDecimal cdelta = BigDecimal.valueOf(Math.cos(delta.doubleValue()));
        BigDecimal sdelta = BigDecimal.valueOf(Math.sin(delta.doubleValue()));

        // Вычисления длины большого круга
        BigDecimal y = BigDecimal.valueOf(Math.sqrt(Math.pow(cl2.multiply(sdelta).doubleValue(), 2) + Math.pow(cl1.multiply(sl2).subtract(sl1.multiply(cl2).multiply(cdelta)).doubleValue(), 2)));
        BigDecimal x = sl1.multiply(sl2).add(cl1.multiply(cl2).multiply(cdelta));
        BigDecimal ad = BigDecimal.valueOf(Math.atan2(y.doubleValue(), x.doubleValue()));
        BigDecimal distance = ad.multiply(rad);

        System.out.println(distance);
        if (distance.doubleValue() > 450){
            return distance.divide(BigDecimal.valueOf(450), MathContext.DECIMAL128).setScale(2, RoundingMode.HALF_UP);
        }
        return BigDecimal.valueOf(1.0);
    }
}
