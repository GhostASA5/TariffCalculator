package ru.fastdelivery.usecase;

import ru.fastdelivery.domain.common.packproperties.price.Price;

public interface VolumePriceProvider {

    Price costPerM3();

    Price minimalPriceForVolume();
}
