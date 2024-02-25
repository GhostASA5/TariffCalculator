package ru.fastdelivery.properties.provider;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.packproperties.price.Price;
import ru.fastdelivery.usecase.VolumePriceProvider;
import ru.fastdelivery.usecase.WeightPriceProvider;

import java.math.BigDecimal;

/**
 * Настройки базовых цен стоимости перевозки из конфига
 */
@ConfigurationProperties("cost.rub")
@Setter
public class PricesRublesProperties implements WeightPriceProvider, VolumePriceProvider {

    private BigDecimal perKg;
    private BigDecimal minimalForWeight;
    private BigDecimal perM3;
    private BigDecimal minimalForVolume;

    @Autowired
    private CurrencyFactory currencyFactory;

    @Override
    public Price costPerKg() {
        return new Price(perKg, currencyFactory.create("RUB"));
    }

    @Override
    public Price minimalPriceForWeight() {
        return new Price(minimalForWeight, currencyFactory.create("RUB"));
    }

    @Override
    public Price costPerM3() {
        return new Price(perM3, currencyFactory.create("RUB"));
    }

    @Override
    public Price minimalPriceForVolume() {
        return new Price(minimalForVolume, currencyFactory.create("RUB"));
    }
}
