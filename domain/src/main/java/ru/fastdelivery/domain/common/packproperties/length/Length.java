package ru.fastdelivery.domain.common.packproperties.length;

import java.math.BigInteger;

public record Length(BigInteger lengthMillimeters) implements Comparable<Length> {

    public Length {
        if (isLessThanZero(lengthMillimeters)) {
            throw new IllegalArgumentException("Length cannot be below Zero!");
        }
    }

    private static boolean isLessThanZero(BigInteger price) {
        return BigInteger.ZERO.compareTo(price) > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Length length = (Length) o;
        return lengthMillimeters.compareTo(length.lengthMillimeters) == 0;
    }

    @Override
    public int compareTo(Length l) {
        return l.lengthMillimeters().compareTo(lengthMillimeters());
    }

    public boolean greaterThan(Length w) {
        return lengthMillimeters().compareTo(w.lengthMillimeters()) > 0;
    }

}
