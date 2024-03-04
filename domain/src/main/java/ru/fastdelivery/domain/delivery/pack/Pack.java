package ru.fastdelivery.domain.delivery.pack;

import ru.fastdelivery.domain.common.packproperties.height.Height;
import ru.fastdelivery.domain.common.packproperties.length.Length;
import ru.fastdelivery.domain.common.packproperties.weight.Weight;
import ru.fastdelivery.domain.common.packproperties.width.Width;

import java.math.BigInteger;

/**
 * Упаковка груза
 *
 * @param weight вес товаров в упаковке
 */
public record Pack(Weight weight, Length length, Width width, Height height) {

    private static final Weight maxWeight = new Weight(BigInteger.valueOf(150_000));
    private static final Height maxHeight = new Height(BigInteger.valueOf(1_500));
    private static final Length maxLength = new Length(BigInteger.valueOf(1_500));
    private static final Width maxWidth = new Width(BigInteger.valueOf(1_500));

    public Pack {
        if (weight.greaterThan(maxWeight)) {
            throw new IllegalArgumentException("Package can't be more than " + maxWeight.weightGrams() + " g.");
        }
        if (height.greaterThan(maxHeight) || length.greaterThan(maxLength) || width.greaterThan(maxWidth)) {
            throw new IllegalArgumentException("Package can't be more than " + maxHeight.heightMillimeters() + " mm.");
        }
    }
}
