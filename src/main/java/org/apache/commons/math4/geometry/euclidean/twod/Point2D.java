package org.apache.commons.math4.geometry.euclidean.twod;

import org.apache.commons.math4.geometry.Point;
import org.apache.commons.math4.geometry.Vector;
import org.apache.commons.math4.util.FastMath;

public class Point2D extends Cartesian2D implements Point<Euclidean2D> {

    public static final Point2D ZERO = new Point2D(0, 0);

    // CHECKSTYLE: stop ConstantName
    /** A point with all coordinates set to NaN. */
    public static final Point2D NaN = new Point2D(Double.NaN, Double.NaN);
    // CHECKSTYLE: resume ConstantName

    public Point2D(double x, double y) {
        super(x, y);
    }

    public Point2D(double a1, Point2D u1, double a2, Point2D u2) {
        super(a1 * u1.x + a2 * u2.x,
                a1 * u1.y + a2 * u2.y);
    }

    @Override
    public double distance(Point<Euclidean2D> p) {
        return distance((Point2D) p);
    }

    public double distance(Point2D other) {
        final double dx = other.x - x;
        final double dy = other.y - y;
        return FastMath.sqrt(dx * dx + dy * dy);
    }

    @Override
    public Vector2D asVector() {
        return new Vector2D(x, y);
    }
}
