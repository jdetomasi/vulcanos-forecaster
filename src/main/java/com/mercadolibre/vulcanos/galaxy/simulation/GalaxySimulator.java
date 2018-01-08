package com.mercadolibre.vulcanos.galaxy.simulation;

import com.mercadolibre.vulcanos.galaxy.model.Forecast;
import com.mercadolibre.vulcanos.galaxy.model.Galaxy;
import com.mercadolibre.vulcanos.galaxy.model.PlanetPosition;
import com.mercadolibre.vulcanos.galaxy.model.Velocity;
import org.apache.log4j.Logger;

import java.math.BigDecimal;

import static com.mercadolibre.vulcanos.geometry.util.Geometry.FULL_ROTATION_DEGREES;

public class GalaxySimulator {
    private static final Logger logger = Logger.getLogger(GalaxySimulator.class);

    private final Galaxy galaxy;
    private final GalaxyForecaster galaxyForecaster;

    public GalaxySimulator(Galaxy galaxy, GalaxyForecaster galaxyForecaster) {
        this.galaxy = galaxy;
        this.galaxyForecaster = galaxyForecaster;
    }

    public void advanceOneDay() {
        PlanetPosition planetPositionA = galaxy.getPlanetPositionA();
        PlanetPosition planetPositionB = galaxy.getPlanetPositionB();
        PlanetPosition planetPositionC = galaxy.getPlanetPositionC();

        planetPositionA.setPosition(calculateNextDayPositionForPlanet(planetPositionA));
        planetPositionB.setPosition(calculateNextDayPositionForPlanet(planetPositionB));
        planetPositionC.setPosition(calculateNextDayPositionForPlanet(planetPositionC));
    }

    private BigDecimal calculateNextDayPositionForPlanet(PlanetPosition planetPosition) {
        BigDecimal velocityPerDay = planetPosition.getPlanet().getVelocity().getDegreesPerDay();
        Velocity.Direction direction = planetPosition.getPlanet().getVelocity().getDirection();

        if (Velocity.Direction.COUNTER_CLOCKLWISE == direction) {
            velocityPerDay = velocityPerDay.multiply(new BigDecimal(-1));
        }

        BigDecimal lastPosition = planetPosition.getPosition();
        BigDecimal nextPosition = lastPosition.add(velocityPerDay);

        return normalizePosition(nextPosition);
    }

    private BigDecimal normalizePosition(BigDecimal position) {
        if (position.compareTo(BigDecimal.ZERO) < 0) {
            position = FULL_ROTATION_DEGREES.add(position);

        }

        if (position.compareTo(FULL_ROTATION_DEGREES) >= 0) {
            position = position.subtract(FULL_ROTATION_DEGREES);
        }

        return position;
    }

    public Forecast getCurrentDayForecast() {
        return galaxyForecaster.getForecast(this.galaxy);
    }

    @Override
    public String toString() {
        return "GalaxySimulator{" +
                "galaxy=" + galaxy +
                '}';
    }
}
