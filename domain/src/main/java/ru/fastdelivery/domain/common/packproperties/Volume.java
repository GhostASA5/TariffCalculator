package ru.fastdelivery.domain.common.packproperties;

import lombok.Data;
import ru.fastdelivery.domain.common.packproperties.height.Height;
import ru.fastdelivery.domain.common.packproperties.length.Length;
import ru.fastdelivery.domain.common.packproperties.width.Width;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
public class Volume {

    private BigDecimal packVolume;

    public Volume(Length length, Width width, Height height){
        this.packVolume = new BigDecimal(length.lengthMillimeters())
                .multiply(new BigDecimal(width.widthMillimeters()))
                .multiply(new BigDecimal(height.heightMillimeters()))
                .divide(new BigDecimal("1000000000"), 4, RoundingMode.HALF_UP);
    }

    public Volume(BigDecimal packVolume){
        this.packVolume = packVolume;
    }

    public static Volume zero() {
        return new Volume(BigDecimal.ZERO);
    }

    public Volume add(Volume additionalVolume) {
        return new Volume(this.packVolume.add(additionalVolume.packVolume));
    }
}
