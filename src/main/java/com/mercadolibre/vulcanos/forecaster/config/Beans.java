package com.mercadolibre.vulcanos.forecaster.config;

import com.mercadolibre.vulcanos.galaxy.model.Galaxy;
import com.mercadolibre.vulcanos.galaxy.model.Planet;
import com.mercadolibre.vulcanos.galaxy.model.Velocity;
import com.mercadolibre.vulcanos.galaxy.simulation.GalaxyForecaster;
import com.mercadolibre.vulcanos.galaxy.simulation.GalaxySimulator;
import com.mercadolibre.vulcanos.geometry.util.Geometry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class Beans {

    @Bean
    public Planet ferengi() {
        Velocity velocity = new Velocity(BigDecimal.ONE, Velocity.Direction.CLOCKLWISE);
        BigDecimal distanceInKm = BigDecimal.valueOf(500);

        return new Planet("Ferengi", velocity, distanceInKm);
    }

    @Bean
    public Planet betasoide() {
        Velocity velocity = new Velocity(BigDecimal.valueOf(3), Velocity.Direction.CLOCKLWISE);
        BigDecimal distanceInKm = BigDecimal.valueOf(2000);

        return new Planet("Betasoide", velocity, distanceInKm);
    }

    @Bean
    public Planet vulcano() {
        Velocity velocity = new Velocity(BigDecimal.valueOf(5), Velocity.Direction.COUNTER_CLOCKLWISE);
        BigDecimal distanceInKm = BigDecimal.valueOf(1000);

        return new Planet("Vulcano", velocity, distanceInKm);
    }

    @Bean
    public Galaxy galaxy(Planet ferengi, Planet betasoide, Planet vulcano) {
        return new Galaxy(ferengi, betasoide, vulcano);
    }

    @Bean
    public Geometry geometry(@Value("${decimalPrecision}") Integer decimalPrecision) {
        return new Geometry(decimalPrecision);
    }

    @Bean
    public GalaxyForecaster galaxyForecaster(Geometry geometry) {
        return new GalaxyForecaster(geometry);
    }

    @Bean
    public GalaxySimulator galaxySimulator(Galaxy galaxy, GalaxyForecaster galaxyForecaster) {
        return new GalaxySimulator(galaxy, galaxyForecaster);
    }
}