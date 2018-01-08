package com.mercadolibre.vulcanos.geometry.util;

import com.mercadolibre.vulcanos.geometry.model.Line;
import com.mercadolibre.vulcanos.geometry.model.Point;
import com.mercadolibre.vulcanos.geometry.model.Triangle;

import java.math.BigDecimal;
import java.math.MathContext;

public class Geometry {
    public static final BigDecimal FULL_ROTATION_DEGREES = BigDecimal.valueOf(360);

    private final Integer decimalPrecision;

    public Geometry(Integer decimalPrecision) {
        this.decimalPrecision = decimalPrecision;
    }

    /**
     * Verifies if three points are in a straight line
     */
    public boolean arePointsCollinear(Point p1, Point p2, Point p3) {
        /* Two points are aligned if:
              y2 - y1     y3 - y2
              -------  =  --------
              x2 - x1     x3 - x2
        */

        BigDecimal m1;
        if (equals(p1.getX(), p2.getX())) {
            m1 = BigDecimal.valueOf(Double.MAX_VALUE);
        } else {
            m1 = p2.getY().subtract(p1.getY()).divide(p2.getX().subtract(p1.getX()), MathContext.DECIMAL32);
        }

        BigDecimal m2;
        if (equals(p2.getX(), p3.getX())) {
            m2 = BigDecimal.valueOf(Double.MAX_VALUE);
        } else {
            m2 = p3.getY().subtract(p2.getY()).divide(p3.getX().subtract(p2.getX()), MathContext.DECIMAL32);
        }

        return equals(m1, m2);
    }


    /**
     * Verifies if a given point is in a triangle using the "Same Side Technique"
     */
    public boolean pointInsideTriangle(Point coordinate, Triangle triangle) {
        Line lineAB = new Line(triangle.getA(), triangle.getB());
        Line lineBC = new Line(triangle.getB(), triangle.getC());
        Line lineCA = new Line(triangle.getC(), triangle.getA());
        return  coordinatesOnSameSide(coordinate, triangle.getA(), lineBC) &&
                coordinatesOnSameSide(coordinate, triangle.getB(), lineCA) &&
                coordinatesOnSameSide(coordinate, triangle.getC(), lineAB);
    }


    /**
     * Verifies if the two given points are on the dame side of the line
     */
    private boolean coordinatesOnSameSide(Point p1, Point p2, Line line) {
        Point vectP1P2 = subtractVectors(line.getP2(), line.getP1());
        double cpd1 = calculateCrossProductDirection(vectP1P2, subtractVectors(p1, line.getP1()));
        Point vectP2P3 = subtractVectors(line.getP2(), line.getP1());
        double cpd2 = calculateCrossProductDirection(vectP2P3, subtractVectors(p2, line.getP1()));


        // if one of the directions is 0 it means that that point is in 'line'... so we can consider that the points are on the same side
        return cpd1 == 0 || cpd2 == 0 || cpd1 == cpd2;
    }

    /**
     * Calculates the direction of the cross product between two vectors
     * @return 0, 1 or -1 (returning 0 if the vectors are aligned)
     */
    private double calculateCrossProductDirection(Point v1, Point v2) {
        return v1.getX().multiply(v2.getY())
                        .subtract(v1.getY().multiply(v2.getX())).signum();
    }

    public BigDecimal calculateTrianglePerimeter(Point pointA, Point pointB, Point pointC) {
        BigDecimal distAB = distanceBetweenPoints(pointA, pointB);
        BigDecimal distBC = distanceBetweenPoints(pointB, pointC);
        BigDecimal distCA = distanceBetweenPoints(pointC, pointA);

        return distAB.add(distBC).add(distCA);
    }

    private BigDecimal distanceBetweenPoints(Point a, Point b) {
        return BigDecimal.valueOf(Math.sqrt(
                Math.pow(a.getX().subtract(b.getX()).doubleValue(), 2)
                    + Math.pow(a.getY().subtract(b.getY()).doubleValue(), 2)
            ));
    }

    public Point getCartesianCoordinates(BigDecimal angle, BigDecimal radius) {
        BigDecimal x = BigDecimal.valueOf(Math.cos(Math.toRadians(FULL_ROTATION_DEGREES.subtract(angle).doubleValue())) * radius.doubleValue());
        BigDecimal y = BigDecimal.valueOf(Math.sin(Math.toRadians(FULL_ROTATION_DEGREES.subtract(angle).doubleValue())) * radius.doubleValue());

        return new Point(x, y);
    }

    public Point roundPointCoordinates(Point point) {
        point.setX(roundBigDecimal(point.getX()));
        point.setY(roundBigDecimal(point.getY()));
        return point;
    }

    public BigDecimal roundBigDecimal(BigDecimal number) {
        return number.setScale(this.decimalPrecision, BigDecimal.ROUND_HALF_UP);
    }

    private Point subtractVectors(Point p1, Point p2) {
        return new Point(p1.getX().subtract(p2.getX()),
                p1.getY().subtract(p2.getY()));
    }

    private boolean equals(BigDecimal x, BigDecimal y) {
        return roundBigDecimal(x).compareTo(roundBigDecimal(y)) == 0;
    }
}
