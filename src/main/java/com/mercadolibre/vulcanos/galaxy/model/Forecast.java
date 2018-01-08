package com.mercadolibre.vulcanos.galaxy.model;

import java.math.BigDecimal;

public enum Forecast {
    DROUGHT(BigDecimal.ZERO),
    RAIN(BigDecimal.ZERO),
    OPTIMAL(BigDecimal.ZERO),
    NORMAL(BigDecimal.ZERO);

    private BigDecimal value;

    Forecast(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
