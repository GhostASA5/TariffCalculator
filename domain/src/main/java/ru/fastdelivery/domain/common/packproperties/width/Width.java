package ru.fastdelivery.domain.common.packproperties.width;

import java.math.BigInteger;

public record Width(
        BigInteger widthMillimeters) implements Comparable<Width> {

    public Width {
        if (isLessThanZero(widthMillimeters)) {
            throw new IllegalArgumentException("Width cannot be below Zero!");
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
        Width width = (Width) o;
        return widthMillimeters.compareTo(width.widthMillimeters) == 0;
    }

    @Override
    public int compareTo(Width w) {
        return w.widthMillimeters().compareTo(widthMillimeters());
    }

    public boolean greaterThan(Width w) {
        return widthMillimeters().compareTo(w.widthMillimeters()) > 0;
    }

}