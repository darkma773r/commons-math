package org.apache.commons.math4.geometry.euclidean.threed;

import org.apache.commons.math4.geometry.Point;

public class Point3D extends Cartesian3D implements Point<Euclidean3D> {

    private static final long serialVersionUID = 1L;

    public Point3D(double x, double y, double z) {
        super(x, y, z);
    }

    @Override
    public double distance(Point<Euclidean3D> p) {
        return distance((Cartesian3D) p);
    }

    @Override
    public Vector3D asVector() {
        return new Vector3D(x, y, z);
    }
}
