package ru.fastdelivery.usecase;

import org.assertj.core.util.BigDecimalComparator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.packproperties.height.Height;
import ru.fastdelivery.domain.common.packproperties.length.Length;
import ru.fastdelivery.domain.common.packproperties.price.Price;
import ru.fastdelivery.domain.common.packproperties.weight.Weight;
import ru.fastdelivery.domain.common.packproperties.width.Width;
import ru.fastdelivery.domain.delivery.coordinates.Coordinates;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TariffCalculateUseCaseTest {

    final WeightPriceProvider weightPriceProvider = mock(WeightPriceProvider.class);
    final VolumePriceProvider volumePriceProvider = mock(VolumePriceProvider.class);
    final CoordinatesProvider coordinatesProvider = mock(CoordinatesProvider.class);
    final Currency currency = new CurrencyFactory(code -> true).create("RUB");

    final TariffCalculateUseCase tariffCalculateUseCase = new TariffCalculateUseCase(weightPriceProvider, volumePriceProvider, coordinatesProvider);

    @Test
    @DisplayName("Расчет стоимости доставки -> успешно")
    void whenCalculatePrice_thenSuccess() {
        var minimalPricePerKg = new Price(BigDecimal.TEN, currency);
        var pricePerKg = new Price(BigDecimal.valueOf(100), currency);
        var minimalPricePerM3 = new Price(BigDecimal.TEN, currency);
        var pricePerM3 = new Price(BigDecimal.valueOf(100), currency);

        var coordinates = new Coordinates(BigDecimal.valueOf(50), BigDecimal.valueOf(55), BigDecimal.valueOf(60), BigDecimal.valueOf(65));

        when(weightPriceProvider.minimalPriceForWeight()).thenReturn(minimalPricePerKg);
        when(weightPriceProvider.costPerKg()).thenReturn(pricePerKg);
        when(volumePriceProvider.minimalPriceForVolume()).thenReturn(minimalPricePerM3);
        when(volumePriceProvider.costPerM3()).thenReturn(pricePerM3);

        var shipment = new Shipment(List.of(new Pack(new Weight(BigInteger.valueOf(1200)),
                new Length(BigInteger.valueOf(1200)),
                new Width(BigInteger.valueOf(1200)),
                new Height(BigInteger.valueOf(1200)))),
                new CurrencyFactory(code -> true).create("RUB"));
        var expectedPrice = new Price(BigDecimal.valueOf(490.75), currency);

        var actualPrice = tariffCalculateUseCase.calc(shipment, coordinates);

        assertThat(actualPrice).usingRecursiveComparison()
                .withComparatorForType(BigDecimalComparator.BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                .isEqualTo(expectedPrice);
    }

    @Test
    @DisplayName("Получение минимальной стоимости -> успешно")
    void whenMinimalPrice_thenSuccess() {
        BigDecimal minimalValuePerKg = BigDecimal.TEN;
        var minimalPricePerKg = new Price(minimalValuePerKg, currency);
        BigDecimal minimalValuePerM3 = BigDecimal.ONE;
        var minimalPricePerM3 = new Price(minimalValuePerM3, currency);
        when(weightPriceProvider.minimalPriceForWeight()).thenReturn(minimalPricePerKg);
        when(volumePriceProvider.minimalPriceForVolume()).thenReturn(minimalPricePerM3);

        var actual = tariffCalculateUseCase.minimalPrice();

        assertThat(actual).isEqualTo(minimalPricePerKg);
    }
}