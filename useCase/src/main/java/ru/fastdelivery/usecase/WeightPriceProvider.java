package ru.fastdelivery.usecase;

import ru.fastdelivery.domain.common.packproperties.price.Price;

public interface WeightPriceProvider {
    Price costPerKg();

    Price minimalPriceForWeight();
}
