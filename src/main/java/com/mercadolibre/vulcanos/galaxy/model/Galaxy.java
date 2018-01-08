package com.mercadolibre.vulcanos.galaxy.model;

import java.math.BigDecimal;

public class Galaxy {
    private final PlanetPosition planetPositionA;
    private final PlanetPosition planetPositionB;
    private final PlanetPosition planetPositionC;

    public Galaxy (Planet planetA, Planet planetB, Planet planetC) {
        this.planetPositionA = new PlanetPosition(planetA, BigDecimal.ZERO);
        this.planetPositionB = new PlanetPosition(planetB, BigDecimal.ZERO);
        this.planetPositionC = new PlanetPosition(planetC, BigDecimal.ZERO);
    }


    public PlanetPosition getPlanetPositionA() {
        return planetPositionA;
    }

    public PlanetPosition getPlanetPositionB() {
        return planetPositionB;
    }

    public PlanetPosition getPlanetPositionC() {
        return planetPositionC;
    }

    @Override
    public String toString() {
        return "Galaxy{" +
                "planetPositionA=" + planetPositionA +
                ", planetPositionB=" + planetPositionC +
                ", getPlanetPositionC=" + planetPositionC +
                '}';
    }
}
