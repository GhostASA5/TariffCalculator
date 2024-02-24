package ru.fastdelivery.domain.delivery.shipment;

import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.packproperties.height.Height;
import ru.fastdelivery.domain.common.packproperties.length.Length;
import ru.fastdelivery.domain.common.packproperties.weight.Weight;
import ru.fastdelivery.domain.common.packproperties.width.Width;
import ru.fastdelivery.domain.delivery.pack.Pack;

import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ShipmentTest {

    @Test
    void whenSummarizingAllParamsOfAllPackages_thenReturnSum() {
        var weight1 = new Weight(BigInteger.TEN);
        var weight2 = new Weight(BigInteger.ONE);
        var length1 = new Length(BigInteger.TEN);
        var length2 = new Length(BigInteger.ONE);
        var width1 = new Width(BigInteger.TEN);
        var width2 = new Width(BigInteger.ONE);
        var height1 = new Height(BigInteger.TEN);
        var height2 = new Height(BigInteger.ONE);

        var packages = List.of(new Pack(weight1, length1, width1, height1), new Pack(weight2, length2, width2, height2));
        var shipment = new Shipment(packages, new CurrencyFactory(code -> true).create("RUB"));

        var massOfShipment = shipment.weightAllPackages();

        assertThat(massOfShipment.weightGrams()).isEqualByComparingTo(BigInteger.valueOf(11));
    }
}