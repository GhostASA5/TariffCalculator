package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigInteger;

public record CargoPackage(
        @Schema(description = "Вес упаковки, граммы", example = "5667.45")
        BigInteger weight,

        @Schema(description = "Вес упаковки, граммы", example = "5667.45")
        BigInteger length,

        @Schema(description = "Вес упаковки, граммы", example = "5667.45")
        BigInteger width,

        @Schema(description = "Вес упаковки, граммы", example = "5667.45")
        BigInteger height
) {
}
