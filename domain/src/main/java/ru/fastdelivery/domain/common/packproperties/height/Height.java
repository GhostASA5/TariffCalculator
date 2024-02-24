package ru.fastdelivery.domain.common.packproperties.height;

import java.math.BigInteger;

public record Height(BigInteger heightMillimeters) implements Comparable<Height> {

    public Height {
        if (isLessThanZero(heightMillimeters)) {
            throw new IllegalArgumentException("Weight cannot be below Zero!");
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
        Height height = (Height) o;
        return heightMillimeters.compareTo(height.heightMillimeters) == 0;
    }

    @Override
    public int compareTo(Height h) {
        return h.heightMillimeters().compareTo(heightMillimeters());
    }

    public boolean greaterThan(Height w) {
        return heightMillimeters().compareTo(w.heightMillimeters()) > 0;
    }

}
