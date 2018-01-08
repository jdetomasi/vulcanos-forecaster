package com.mercadolibre.vulcanos.galaxy.simulation;

import com.mercadolibre.vulcanos.galaxy.model.Forecast;
import com.mercadolibre.vulcanos.galaxy.model.Galaxy;
import com.mercadolibre.vulcanos.galaxy.model.PlanetPosition;
import com.mercadolibre.vulcanos.geometry.model.Point;
import com.mercadolibre.vulcanos.geometry.model.Triangle;
import com.mercadolibre.vulcanos.geometry.util.Geometry;
import org.apache.log4j.Logger;

import java.math.BigDecimal;

public class GalaxyForecaster {
    private static final Logger logger = Logger.getLogger(GalaxyForecaster.class);

    private final Geometry geometry;

    public GalaxyForecaster(Geometry geometry) {
        this.geometry = geometry;
    }

    public Forecast getForecast(Galaxy galaxy) {
        Forecast forecast;

        PlanetPosition planetPosA = galaxy.getPlanetPositionA();
        PlanetPosition planetPosB = galaxy.getPlanetPositionB();
        PlanetPosition planetPosC = galaxy.getPlanetPositionC();
        Point ccPlanetA = geometry.getCartesianCoordinates(planetPosA.getPosition(), planetPosA.getPlanet().getSunDistanceInKm());
        Point ccPlanetB = geometry.getCartesianCoordinates(planetPosB.getPosition(), planetPosB.getPlanet().getSunDistanceInKm());
        Point ccPlanetC = geometry.getCartesianCoordinates(planetPosC.getPosition(), planetPosC.getPlanet().getSunDistanceInKm());

        if (arePlanetsAlignedWithTheSun(ccPlanetA, ccPlanetB, ccPlanetC)) {
            forecast = Forecast.DROUGHT;
        } else if (areThreePlanetsAligned(ccPlanetA, ccPlanetB, ccPlanetC)) {
            forecast = Forecast.OPTIMAL;
        } else if (isTheSunInsideThreePlanetsTriangle(ccPlanetA, ccPlanetB, ccPlanetC)) {
            forecast = Forecast.RAIN;
            forecast.setValue(geometry.roundBigDecimal(geometry.calculateTrianglePerimeter(ccPlanetA, ccPlanetB, ccPlanetC)));
        } else {
            forecast = Forecast.NORMAL;
        }

        logger.debug("Galaxy: " + galaxy + " - Forecast: " + forecast + "(" + forecast.getValue() + ")");

        return forecast;
    }

    private boolean arePlanetsAlignedWithTheSun(Point ccPlanetA, Point ccPlanetB, Point ccPlanetC) {
        Point sunCoordinates = new Point(BigDecimal.ZERO, BigDecimal.ZERO);

        return geometry.arePointsCollinear(ccPlanetA, ccPlanetB, ccPlanetC)
                && geometry.arePointsCollinear(ccPlanetA, ccPlanetB, sunCoordinates);
    }

    private boolean areThreePlanetsAligned(Point ccPlanetA, Point ccPlanetB, Point ccPlanetC) {
        return geometry.arePointsCollinear(ccPlanetA, ccPlanetB, ccPlanetC);
    }

    private boolean isTheSunInsideThreePlanetsTriangle(Point ccPlanetA, Point ccPlanetB, Point ccPlanetC) {
        Point sunCoordinates = new Point(BigDecimal.ZERO, BigDecimal.ZERO);

        return geometry.pointInsideTriangle(sunCoordinates, new Triangle(ccPlanetA, ccPlanetB, ccPlanetC));
    }
}
