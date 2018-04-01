package org.apache.commons.math4.geometry.euclidean.oned;

import org.apache.commons.math4.geometry.Cartesian;
import org.apache.commons.math4.geometry.Space;
import org.apache.commons.math4.util.FastMath;

public class Cartesian1D implements Cartesian<Euclidean1D> {

    protected final double x;

    public Cartesian1D(double x) {
        this.x = x;
    }

    public double getX() {
        return this.x;
    }

    @Override
    public Space getSpace() {
        return Euclidean1D.getInstance();
    }

    /** {@inheritDoc} */
    @Override
    public boolean isNaN() {
        return Double.isNaN(x);
    }

    /** Compute the distance between the instance and other coordinates.
     * @param c other coordinates
     * @return the distance between the instance and c
     */
    protected double distance(Cartesian1D c) {
        final double dx = c.x - x;
        return FastMath.abs(dx);
    }
}
