package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record Departure(

        @Schema(description = "Координаты широты пункта отправления", example = "55.446008")
        BigDecimal latitude,

        @Schema(description = "Координаты долготы пункта отправления", example = "65.339151")
        BigDecimal longitude
) {
}
