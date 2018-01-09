package com.mercadolibre.vulcanos.galaxy.simulation;

import com.mercadolibre.vulcanos.galaxy.model.Galaxy;
import com.mercadolibre.vulcanos.galaxy.model.Planet;
import com.mercadolibre.vulcanos.galaxy.model.Velocity;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class GalaxySimulatorTest {
    private final static Planet FERENGI = new Planet("ferengi", new Velocity(new BigDecimal(1), Velocity.Direction.CLOCKLWISE), new BigDecimal(500));
    private final static Planet BETASOIDE = new Planet("betasoide", new Velocity(new BigDecimal(3), Velocity.Direction.CLOCKLWISE), new BigDecimal(2000));
    private final static Planet VULCANO = new Planet("vulcano", new Velocity(new BigDecimal(5), Velocity.Direction.COUNTER_CLOCKLWISE), new BigDecimal(1000));

    @Test
    public void testPositionOnDay1ShouldBeAsExpected() {
        Galaxy galaxy = new Galaxy(FERENGI, VULCANO, BETASOIDE);
        GalaxySimulator galaxySimulator = new GalaxySimulator(galaxy, null);

        galaxySimulator.advanceOneDay();

        assertEquals(new BigDecimal(1), galaxy.getPlanetPositionA().getPosition());
        assertEquals(new BigDecimal(355), galaxy.getPlanetPositionB().getPosition());
        assertEquals(new BigDecimal(3), galaxy.getPlanetPositionC().getPosition());
    }

    @Test
    public void testPositionOnDay2ShouldBeAsExpected() {
        Galaxy galaxy = new Galaxy(FERENGI, VULCANO, BETASOIDE);
        GalaxySimulator galaxySimulator = new GalaxySimulator(galaxy, null);

        galaxySimulator.advanceOneDay();
        galaxySimulator.advanceOneDay();

        assertEquals(new BigDecimal(2), galaxy.getPlanetPositionA().getPosition());
        assertEquals(new BigDecimal(350), galaxy.getPlanetPositionB().getPosition());
        assertEquals(new BigDecimal(6), galaxy.getPlanetPositionC().getPosition());
    }

    @Test
    public void testPositionAfterOneSpinShouldBeAsExpected() {
        Galaxy galaxy = new Galaxy(FERENGI, VULCANO, BETASOIDE);
        GalaxySimulator galaxySimulator = new GalaxySimulator(galaxy, null);

        for(int i = 1; i <= 73; i++) {
            galaxySimulator.advanceOneDay();
        }

        assertEquals(new BigDecimal(73), galaxy.getPlanetPositionA().getPosition());
        assertEquals(new BigDecimal(355), galaxy.getPlanetPositionB().getPosition());
        assertEquals(new BigDecimal(219), galaxy.getPlanetPositionC().getPosition());
    }
}