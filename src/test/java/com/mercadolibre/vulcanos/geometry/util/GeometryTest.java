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
                new Point(BigDecimal.ZERO, BigDecimal.ONE),
                geometry.roundPointCoordinates(
                        geometry.getCartesianCoordinates(BigDecimal.valueOf(270), BigDecimal.ONE)));

        assertEquals(
                new Point(BigDecimal.valueOf(12), BigDecimal.valueOf(-5)),
                geometry.roundPointCoordinates(
                        geometry.getCartesianCoordinates(BigDecimal.valueOf(22.6), BigDecimal.valueOf(13))));

        assertEquals(
                new Point(BigDecimal.valueOf(2.57), BigDecimal.valueOf(-1.55)),
                geometry.roundPointCoordinates(
                        geometry.getCartesianCoordinates(BigDecimal.valueOf(31), BigDecimal.valueOf(3))));
    }

    @Test
    public void testTrianglePerimeter() {
        Point pointA = new Point(BigDecimal.valueOf(1), BigDecimal.valueOf(0));
        Point pointB = new Point(BigDecimal.valueOf(1), BigDecimal.valueOf(-3));
        Point pointC = new Point(BigDecimal.valueOf(3), BigDecimal.valueOf(4));

        BigDecimal expectedPerimeter = BigDecimal.valueOf(3)
                .add(BigDecimal.valueOf(Math.sqrt(53)))
                .add(BigDecimal.valueOf(Math.sqrt(20)));
        BigDecimal calculatedPerimeter = geometry.calculateTrianglePerimeter(pointA, pointB, pointC);

        assertEquals(geometry.roundBigDecimal(expectedPerimeter), geometry.roundBigDecimal(calculatedPerimeter));
    }
}