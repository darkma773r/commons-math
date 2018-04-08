package org.apache.commons.math4.geometry.euclidean.twod;

import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.geometry.Coordinates;
import org.apache.commons.math4.geometry.Space;
import org.apache.commons.math4.geometry.euclidean.threed.Cartesian3D;
import org.apache.commons.math4.util.FastMath;

public class Cartesian2D implements Coordinates<Euclidean2D> {

    protected final double x;
    protected final double y;

    public Cartesian2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Cartesian2D(double[] v) throws DimensionMismatchException {
        if (v.length != 2) {
            throw new DimensionMismatchException(v.length, 2);
        }
        this.x = v[0];
        this.y = v[1];
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    /** {@inheritDoc} */
    @Override
    public Space getSpace() {
        return Euclidean2D.getInstance();
    }

    /** {@inheritDoc} */
    @Override
    public boolean isNaN() {
        return Double.isNaN(x) || Double.isNaN(y);
    }

    /** Get the coordinates as a dimension 2 array.
     * @return coordinates
     */
    public double[] toArray() {
        return new double[] { x, y };
    }
}
