package com.mercadolibre.vulcanos.galaxy.model;

import java.math.BigDecimal;

public class Velocity {
    private BigDecimal degreesPerDay;
    private Direction direction;

    public Velocity(BigDecimal degreesPerDay, Direction direction) {
        this.degreesPerDay = degreesPerDay;
        this.direction = direction;
    }

    public BigDecimal getDegreesPerDay() {
        return degreesPerDay;
    }

    public void setDegreesPerDay(BigDecimal degreesPerDay) {
        this.degreesPerDay = degreesPerDay;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public enum Direction {
        CLOCKLWISE,
        COUNTER_CLOCKLWISE
    }
}
