package com.mercadolibre.vulcanos.galaxy.simulation;

import com.mercadolibre.vulcanos.galaxy.model.Forecast;
import com.mercadolibre.vulcanos.galaxy.model.Galaxy;
import com.mercadolibre.vulcanos.galaxy.model.Planet;
import com.mercadolibre.vulcanos.galaxy.model.Velocity;
import com.mercadolibre.vulcanos.geometry.util.Geometry;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class GalaxyForecasterTest {
    private static final Integer DECIMAL_PRECISION = 2;

    private final static Planet FERENGI = new Planet("ferengi", new Velocity(new BigDecimal(1), Velocity.Direction.CLOCKLWISE), new BigDecimal(500));
    private final static Planet BETASOIDE = new Planet("betasoide", new Velocity(new BigDecimal(3), Velocity.Direction.CLOCKLWISE), new BigDecimal(2000));
    private final static Planet VULCANO = new Planet("vulcano", new Velocity(new BigDecimal(5), Velocity.Direction.COUNTER_CLOCKLWISE), new BigDecimal(1000));

    @Test
    public void testForecastShouldBeDroughtWhenPlanetsAndTheSunAreAligned() {
        GalaxyForecaster galaxyForecaster = new GalaxyForecaster(new Geometry(DECIMAL_PRECISION));
        Galaxy galaxy = new Galaxy(BETASOIDE, FERENGI, VULCANO);

        galaxy.getPlanetPositionA().setPosition(BigDecimal.ZERO);
        galaxy.getPlanetPositionB().setPosition(BigDecimal.ZERO);
        galaxy.getPlanetPositionC().setPosition(BigDecimal.ZERO);

        assertEquals(Forecast.DROUGHT, galaxyForecaster.getForecast(galaxy));

        galaxy.getPlanetPositionA().setPosition(new BigDecimal(30));
        galaxy.getPlanetPositionB().setPosition(new BigDecimal(30));
        galaxy.getPlanetPositionC().setPosition(new BigDecimal(210));

        assertEquals(Forecast.DROUGHT, galaxyForecaster.getForecast(galaxy));

        galaxy.getPlanetPositionA().setPosition(new BigDecimal(270));
        galaxy.getPlanetPositionB().setPosition(new BigDecimal(90));
        galaxy.getPlanetPositionC().setPosition(new BigDecimal(270));

        assertEquals(Forecast.DROUGHT, galaxyForecaster.getForecast(galaxy));
    }

    @Test
    public void testForecastShouldBeRainyWhenTheSunIsInsideTriangle() {
        GalaxyForecaster galaxyForecaster = new GalaxyForecaster(new Geometry(DECIMAL_PRECISION));
        Galaxy galaxy = new Galaxy(BETASOIDE, FERENGI, VULCANO);

        // testing day after it starts raining
        galaxy.getPlanetPositionA().setPosition(new BigDecimal(66));
        galaxy.getPlanetPositionB().setPosition(new BigDecimal(22));
        galaxy.getPlanetPositionC().setPosition(new BigDecimal(250));

        assertEquals(Forecast.NORMAL, galaxyForecaster.getForecast(galaxy));

        // simulate one day movement
        galaxy.getPlanetPositionA().setPosition(new BigDecimal(69));
        galaxy.getPlanetPositionB().setPosition(new BigDecimal(23));
        galaxy.getPlanetPositionC().setPosition(new BigDecimal(245));

        assertEquals(Forecast.RAIN, galaxyForecaster.getForecast(galaxy));
    }
}