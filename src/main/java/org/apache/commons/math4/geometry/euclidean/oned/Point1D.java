package org.apache.commons.math4.geometry.euclidean.oned;

import org.apache.commons.math4.geometry.Point;

public class Point1D extends Cartesian1D implements Point<Euclidean1D> {

    public Point1D(double x) {
        super(x);
    }

    @Override
    public double distance(Point<Euclidean1D> p) {
        return distance((Cartesian1D) p);
    }

    @Override
    public Vector1D asVector() {
        return new Vector1D(x);
    }
}
