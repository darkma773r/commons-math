package org.apache.commons.math4.geometry.euclidean.oned;

import org.apache.commons.math4.geometry.Point;
import org.apache.commons.math4.geometry.Vector;

public class Point1D extends Cartesian1D implements Point<Euclidean1D> {

    public static final Point1D NaN = new Point1D(Double.NaN);

    public static final Point1D ZERO = new Point1D(0.0);

    public static final Point1D ONE = new Point1D(1.0);

    public Point1D(double x) {
        super(x);
    }

    @Override
    public double distance(Point<Euclidean1D> p) {
        return distance((Cartesian1D) p);
    }

    public Point1D add(Vector<Euclidean1D> v) {
        return new Point1D(this.x + ((Cartesian1D) v).getX());
    }

    public Vector1D subtract(Point<Euclidean1D> p) {
        return new Vector1D(this.x - ((Cartesian1D)p).x);
    }

    public Vector1D asVector() {
        return new Vector1D(x);
    }
}
