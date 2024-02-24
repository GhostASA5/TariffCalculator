package ru.fastdelivery.domain.delivery.pack;

import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.packproperties.height.Height;
import ru.fastdelivery.domain.common.packproperties.length.Length;
import ru.fastdelivery.domain.common.packproperties.weight.Weight;
import ru.fastdelivery.domain.common.packproperties.width.Width;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PackTest {

    @Test
    void whenParamsMoreThanMaxParams_thenThrowException() {
        var weight = new Weight(BigInteger.valueOf(150_001));
        var length = new Length(BigInteger.valueOf(1501));
        var width = new Width(BigInteger.valueOf(1501));
        var height = new Height(BigInteger.valueOf(1501));
        assertThatThrownBy(() -> new Pack(weight, length, width, height))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenParamsLessThanMaxParams_thenObjectCreated() {
        var actual = new Pack(new Weight(BigInteger.valueOf(1_000)), new Length(BigInteger.valueOf(1000)),
                new Width(BigInteger.valueOf(1000)), new Height(BigInteger.valueOf(1000)));
        assertThat(actual.weight()).isEqualTo(new Weight(BigInteger.valueOf(1_000)));
    }
}