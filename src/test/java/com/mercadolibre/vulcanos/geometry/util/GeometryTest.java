package com.mercadolibre.vulcanos.geometry.util;

import com.mercadolibre.vulcanos.geometry.model.Point;
import com.mercadolibre.vulcanos.geometry.model.Triangle;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class GeometryTest {
    private static final Integer DECIMAL_PRECISION = 2;

    private Geometry geometry = new Geometry(DECIMAL_PRECISION);

    @Test
    public void testCollinearWithAlignedPoints() {
        Point p1 = new Point(new BigDecimal(-1),new BigDecimal(-1));
        Point p2 = new Point(new BigDecimal(0),new BigDecimal(0));
        Point p3 = new Point(new BigDecimal(2),new BigDecimal(2));
        assertTrue(geometry.arePointsCollinear(p1, p2, p3));
    }

    @Test
    public void testCollinearWithNotAlignedPoints() {
        Point p1 = new Point(new BigDecimal(1),new BigDecimal(0));
        Point p2 = new Point(new BigDecimal(0),new BigDecimal(1));
        Point p3 = new Point(new BigDecimal(1),new BigDecimal(1));
        assertFalse(geometry.arePointsCollinear(p1, p2, p3));
    }

    @Test
    public void testPointInsideTriangleSuccess() {
        Triangle triangle = new Triangle(
                new Point(new BigDecimal(0),new BigDecimal(0)),
                new Point(new BigDecimal(0),new BigDecimal(5)),
                new Point(new BigDecimal(5),new BigDecimal(0)));

        Point p = new Point(new BigDecimal(1), new BigDecimal(1));

        assertTrue(geometry.pointInsideTriangle(p, triangle));
    }

    @Test
    public void testPointInsideTriangleFailure() {
        Triangle triangle = new Triangle(
                new Point(new BigDecimal(0),new BigDecimal(0)),
                new Point(new BigDecimal(0),new BigDecimal(1)),
                new Point(new BigDecimal(1),new BigDecimal(0)));

        Point p = new Point(new BigDecimal(1), new BigDecimal(1));

        assertFalse(geometry.pointInsideTriangle(p, triangle));
    }

    @Test
    public void testPointInsideTriangleShouldSucceedWithPointOnVertex() {
        Triangle triangle = new Triangle(
                new Point(new BigDecimal(0),new BigDecimal(0)),
                new Point(new BigDecimal(0),new BigDecimal(1)),
                new Point(new BigDecimal(1),new BigDecimal(0)));

        Point p = new Point(new BigDecimal(0), new BigDecimal(1));

        assertTrue(geometry.pointInsideTriangle(p, triangle));
    }

    @Test
    public void testPointInsideTriangleShouldSucceedWithPointOnEdge() {
        Triangle triangle = new Triangle(
                new Point(new BigDecimal(0),new BigDecimal(0)),
                new Point(new BigDecimal(0),new BigDecimal(2)),
                new Point(new BigDecimal(2),new BigDecimal(0)));

        Point p = new Point(new BigDecimal(0), new BigDecimal(1));

        assertTrue(geometry.pointInsideTriangle(p, triangle));
    }

    @Test
    public void testCoordinateConversionIsCorrect() {
        assertEquals(
                new Point(BigDecimal.ZERO, new BigDecimal(2000)),
                geometry.roundPointCoordinates(
                        geometry.getCartesianCoordinates(new BigDecimal(270), new BigDecimal(2000))));

        assertEquals(
                new Point(new BigDecimal(500), BigDecimal.ZERO),
                geometry.roundPointCoordinates(
                        geometry.getCartesianCoordinates(BigDecimal.ZERO, new BigDecimal(500))));

        assertEquals(
                new Point(new BigDecimal("353.55"), new BigDecimal("-353.55")),
                geometry.roundPointCoordinates(
                        geometry.getCartesianCoordinates(new BigDecimal(45), new BigDecimal(500))));

        assertEquals(
                new Point(new BigDecimal("-415.82"), new BigDecimal("-1956.30")),
                geometry.roundPointCoordinates(
                        geometry.getCartesianCoordinates(new BigDecimal(102), new BigDecimal(2000))));

        assertEquals(
                new Point(new BigDecimal("-1989.04"), new BigDecimal("209.06")),
                geometry.roundPointCoordinates(
                        geometry.getCartesianCoordinates(new BigDecimal(186), new BigDecimal(2000))));

        assertEquals(
                new Point(new BigDecimal("996.19"), new BigDecimal("87.16")),
                geometry.roundPointCoordinates(
                        geometry.getCartesianCoordinates(new BigDecimal(355), new BigDecimal(1000))));
    }

    @Test
    public void testTrianglePerimeter() {
        Point pointA = new Point(new BigDecimal(1), new BigDecimal(0));
        Point pointB = new Point(new BigDecimal(1), new BigDecimal(-3));
        Point pointC = new Point(new BigDecimal(3), new BigDecimal(4));

        BigDecimal expectedPerimeter = new BigDecimal(3)
                .add(new BigDecimal(Math.sqrt(53)))
                .add(new BigDecimal(Math.sqrt(20)));
        BigDecimal calculatedPerimeter = geometry.calculateTrianglePerimeter(pointA, pointB, pointC);

        assertEquals(geometry.roundBigDecimal(expectedPerimeter), geometry.roundBigDecimal(calculatedPerimeter));
    }
}