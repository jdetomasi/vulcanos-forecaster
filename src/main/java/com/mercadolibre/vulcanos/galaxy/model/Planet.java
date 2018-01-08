package com.mercadolibre.vulcanos.galaxy.model;

import java.math.BigDecimal;

public class Planet {
    private String name;
    private Velocity velocity;
    private BigDecimal sunDistanceInKm;

    public Planet(String name, Velocity velocity, BigDecimal sunDistanceInKm) {
        this.name = name;
        this.velocity = velocity;
        this.sunDistanceInKm = sunDistanceInKm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Velocity getVelocity() {
        return velocity;
    }

    public void setVelocity(Velocity velocity) {
        this.velocity = velocity;
    }

    public BigDecimal getSunDistanceInKm() {
        return sunDistanceInKm;
    }

    public void setSunDistanceInKm(BigDecimal sunDistanceInKm) {
        this.sunDistanceInKm = sunDistanceInKm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Planet planet = (Planet) o;

        return name != null ? name.equals(planet.name) : planet.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Planet{" +
                "name='" + name + '\'' +
                '}';
    }
}
