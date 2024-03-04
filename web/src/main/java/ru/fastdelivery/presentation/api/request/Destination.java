package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record Destination(

        @Schema(description = "Координаты широты пункта назначения", example = "73.398660")
        BigDecimal latitude,

        @Schema(description = "Координаты долготы пункта назначения", example = "55.027532")
        BigDecimal longitude
) {
}
