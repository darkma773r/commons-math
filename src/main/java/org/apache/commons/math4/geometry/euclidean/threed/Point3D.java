package org.apache.commons.math4.geometry.euclidean.threed;

import org.apache.commons.math4.geometry.Point;
import org.apache.commons.math4.geometry.Vector;
import org.apache.commons.math4.geometry.euclidean.twod.Cartesian2D;
import org.apache.commons.math4.geometry.euclidean.twod.Euclidean2D;
import org.apache.commons.math4.geometry.euclidean.twod.Vector2D;

public class Point3D extends Cartesian3D implements Point<Euclidean3D> {

    private static final long serialVersionUID = 1L;

    public static final Point3D ZERO = new Point3D(0.0, 0.0, 0.0);

    public static final Point3D NaN = new Point3D(Double.NaN, Double.NaN, Double.NaN);

    /** First canonical vector (coordinates: 1, 0, 0). */
    public static final Point3D PLUS_I = new Point3D(1, 0, 0);

    /** Opposite of the first canonical vector (coordinates: -1, 0, 0). */
    public static final Point3D MINUS_I = new Point3D(-1, 0, 0);

    /** Second canonical vector (coordinates: 0, 1, 0). */
    public static final Point3D PLUS_J = new Point3D(0, 1, 0);

    /** Opposite of the second canonical vector (coordinates: 0, -1, 0). */
    public static final Point3D MINUS_J = new Point3D(0, -1, 0);

    /** Third canonical vector (coordinates: 0, 0, 1). */
    public static final Point3D PLUS_K = new Point3D(0, 0, 1);

    /** Opposite of the third canonical vector (coordinates: 0, 0, -1).  */
    public static final Point3D MINUS_K = new Point3D(0, 0, -1);

    public Point3D(double x, double y, double z) {
        super(x, y, z);
    }

    public Point3D(double[] p) {
        super(p);
    }

    public Point3D(double a1, Cartesian3D u1) {
        super(a1, u1);
    }

    public Point3D(double a1, Cartesian3D u1, double a2, Cartesian3D u2) {
        super(a1, u1, a2, u2);
    }

    public Point3D(double a1, Cartesian3D u1, double a2, Cartesian3D u2, double a3, Cartesian3D u3) {
        super(a1, u1, a2, u2, a3, u3);
    }

    public Point3D add(Vector<Euclidean3D> v) {
        final Cartesian3D c = (Cartesian3D) v;
        return new Point3D(this.x + c.x, this.y + c.y, this.z + c.z);
    }

    public Vector3D subtract(Point<Euclidean3D> p) {
        final Cartesian3D c = (Cartesian3D) p;
        return new Vector3D(this.x - c.x, this.y - c.y, this.z - c.z);
    }


    @Override
    public double distance(Point<Euclidean3D> p) {
        return cartesianDistance((Cartesian3D) p);
    }

    public Vector3D asVector() {
        return new Vector3D(x, y, z);
    }
}
