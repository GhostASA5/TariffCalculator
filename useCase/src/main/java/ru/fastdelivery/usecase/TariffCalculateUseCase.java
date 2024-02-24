package ru.fastdelivery.usecase;

import jdk.jshell.Snippet;
import lombok.RequiredArgsConstructor;
import ru.fastdelivery.domain.common.packproperties.price.Price;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

import javax.inject.Named;

@Named
@RequiredArgsConstructor
public class TariffCalculateUseCase {
    private final WeightPriceProvider weightPriceProvider;
    private final VolumePriceProvider volumePriceProvider;

    public Price calc(Shipment shipment) {
        var weightAllPackagesKg = shipment.weightAllPackages().kilograms();
        var minimalPriceForWeight = weightPriceProvider.minimalPrice();
        var volumeAllPackages = shipment.volumeAllPackages();
        var minimalPriceForVolume = volumePriceProvider.minimalPriceForVolume();

        var totalPriceForWeight = weightPriceProvider
                .costPerKg()
                .multiply(weightAllPackagesKg)
                .max(minimalPriceForWeight);

        var totalPriceForVolume = volumePriceProvider
                .costPerM3()
                .multiply(volumeAllPackages.getPackVolume())
                .max(minimalPriceForVolume);
        System.out.println(totalPriceForWeight);
        System.out.println(totalPriceForVolume);

        if (totalPriceForVolume.amount().compareTo(totalPriceForWeight.amount()) > 0){
            return totalPriceForVolume;
        } else {
            return totalPriceForWeight;
        }
    }


    public Price minimalPrice() {
        return weightPriceProvider.minimalPrice();
    }
}
