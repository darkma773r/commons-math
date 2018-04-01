package org.apache.commons.math4.geometry.euclidean.threed;

import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.geometry.Cartesian;
import org.apache.commons.math4.geometry.Space;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.numbers.arrays.LinearCombination;

public abstract class Cartesian3D implements Cartesian<Euclidean3D> {

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

    protected Cartesian3D(double a1, Cartesian3D u1) {
        this.x = a1 * u1.x;
        this.y = a1 * u1.y;
        this.z = a1 * u1.z;
    }

    protected Cartesian3D(double a1, Cartesian3D u1, double a2, Cartesian3D u2) {
        this.x = LinearCombination.value(a1, u1.x, a2, u2.x);
        this.y = LinearCombination.value(a1, u1.y, a2, u2.y);
        this.z = LinearCombination.value(a1, u1.z, a2, u2.z);
    }

    protected Cartesian3D(double a1, Cartesian3D u1, double a2, Cartesian3D u2,
            double a3, Cartesian3D u3) {
        this.x = LinearCombination.value(a1, u1.x, a2, u2.x, a3, u3.x);
        this.y = LinearCombination.value(a1, u1.y, a2, u2.y, a3, u3.y);
        this.z = LinearCombination.value(a1, u1.z, a2, u2.z, a3, u3.z);
    }

    protected Cartesian3D(double alpha, double delta) {
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

    protected double cartesianDistance(Cartesian3D other) {
        final double dx = other.x - x;
        final double dy = other.y - y;
        final double dz = other.z - z;
        return FastMath.sqrt(dx * dx + dy * dy + dz * dz);
    }
}
