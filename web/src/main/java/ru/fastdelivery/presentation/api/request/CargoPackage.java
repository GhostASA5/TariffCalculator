package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigInteger;

public record CargoPackage(
        @Schema(description = "Вес упаковки, граммы", example = "5667")
        BigInteger weight,

        @Schema(description = "Длина упаковки, миллиметры", example = "1200")
        BigInteger length,

        @Schema(description = "Ширина упаковки, миллиметры", example = "660")
        BigInteger width,

        @Schema(description = "Высота упаковки, миллиметры", example = "945")
        BigInteger height
) {
}
