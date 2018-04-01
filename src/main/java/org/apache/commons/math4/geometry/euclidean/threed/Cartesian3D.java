package org.apache.commons.math4.geometry.euclidean.threed;

import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.geometry.Cartesian;
import org.apache.commons.math4.geometry.Space;
import org.apache.commons.math4.util.FastMath;

public class Cartesian3D implements Cartesian<Euclidean3D> {

    protected final double x;
    protected final double y;
    protected final double z;

    protected Cartesian3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    protected Cartesian3D(double[] v) throws DimensionMismatchException {
        if (v.length != 3) {
            throw new DimensionMismatchException(v.length, 3);
        }
        this.x = v[0];
        this.y = v[1];
        this.z = v[2];
    }

    public Cartesian3D(double alpha, double delta) {
        double cosDelta = FastMath.cos(delta);
        this.x = FastMath.cos(alpha) * cosDelta;
        this.y = FastMath.sin(alpha) * cosDelta;
        this.z = FastMath.sin(delta);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double[] toArray() {
        return new double[] { x, y, z };
    }

    @Override
    public boolean isNaN() {
        return Double.isNaN(x) || Double.isNaN(y) || Double.isNaN(z);
    }

    @Override
    public Space getSpace() {
        return Euclidean3D.getInstance();
    }

    protected double distance(Cartesian3D other) {
        final double dx = other.x - x;
        final double dy = other.y - y;
        final double dz = other.z - z;
        return FastMath.sqrt(dx * dx + dy * dy + dz * dz);
    }
}
