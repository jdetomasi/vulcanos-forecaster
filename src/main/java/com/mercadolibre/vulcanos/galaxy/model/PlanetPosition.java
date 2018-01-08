package com.mercadolibre.vulcanos.galaxy.model;

import java.math.BigDecimal;

public class PlanetPosition {
    private Planet planet;
    private BigDecimal position;

    public PlanetPosition(Planet planet, BigDecimal position) {
        this.planet = planet;
        this.position = position;
    }

    public Planet getPlanet() {
        return planet;
    }

    public void setPlanet(Planet planet) {
        this.planet = planet;
    }

    public BigDecimal getPosition() {
        return position;
    }

    public void setPosition(BigDecimal position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "PlanetPosition{" +
                "planet=" + planet +
                ", position=" + position +
                '}';
    }
}
