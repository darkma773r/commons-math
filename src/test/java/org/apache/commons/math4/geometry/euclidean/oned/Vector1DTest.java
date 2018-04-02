/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.math4.geometry.euclidean.oned;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import org.apache.commons.math4.exception.MathArithmeticException;
import org.apache.commons.math4.geometry.Point;
import org.apache.commons.math4.geometry.Space;
import org.apache.commons.math4.geometry.Vector;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.numbers.core.Precision;
import org.junit.Assert;
import org.junit.Test;

public class Vector1DTest {

    private static final double TEST_TOLERANCE = 1e-15;

    @Test
    public void testConstants() {
        // act/assert
        checkVector(Vector1D.ZERO, 0.0);
        checkVector(Vector1D.ONE, 1.0);
        checkVector(Vector1D.NaN, Double.NaN);
        checkVector(Vector1D.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
        checkVector(Vector1D.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    @Test
    public void testConstructor_simple() {
        // act/assert
        checkVector(new Vector1D(2), 2);
        checkVector(new Vector1D(-2), -2);
        checkVector(new Vector1D(FastMath.PI), FastMath.PI);
    }

    @Test
    public void testConstructor_multiplicative() {
        // act/assert
        checkVector(new Vector1D(2, new Vector1D(3)), 6);
        checkVector(new Vector1D(-2, new Vector1D(3)), -6);
    }

    @Test
    public void testConstructor_linear2() {
        // act/assert
        checkVector(new Vector1D(
                2, new Vector1D(3),
                5, new Vector1D(7)), 41);
        checkVector(new Vector1D(
                2, new Vector1D(3),
                -5, new Vector1D(7)),-29);
    }

    @Test
    public void testConstructor_linear3() {
        // act/assert
        checkVector(new Vector1D(
                2, new Vector1D(3),
                5, new Vector1D(7),
                11, new Vector1D(13)), 184);
        checkVector(new Vector1D(
                2, new Vector1D(3),
                5, new Vector1D(7),
                -11, new Vector1D(13)), -102);
    }

    @Test
    public void testConstructor_linear4() {
        // act/assert
        checkVector(new Vector1D(
                2, new Vector1D(3),
                5, new Vector1D(7),
                11, new Vector1D(13),
                17, new Vector1D(19)), 507);
        checkVector(new Vector1D(
                2, new Vector1D(3),
                5, new Vector1D(7),
                11, new Vector1D(13),
                -17, new Vector1D(19)), -139);
    }

    @Test
    public void testSpace() {
        // act
        Space space = new Vector1D(1).getSpace();

        // assert
        Assert.assertEquals(1, space.getDimension());
    }

    @Test
    public void testZero() {
        // act
        Vector1D zero = new Vector1D(1).getZero();

        // assert
        Assert.assertEquals(0, zero.getX(), TEST_TOLERANCE);
    }

    @Test
    public void testNorm1() {
        // act/assert
        Assert.assertEquals(0.0, Vector1D.ZERO.getNorm1(), TEST_TOLERANCE);
        Assert.assertEquals(6.0, new Vector1D(6).getNorm1(), TEST_TOLERANCE);
        Assert.assertEquals(6.0, new Vector1D(-6).getNorm1(), TEST_TOLERANCE);
    }

    @Test
    public void testNorm() {
        // act/assert
        Assert.assertEquals(0.0, Vector1D.ZERO.getNorm(), TEST_TOLERANCE);
        Assert.assertEquals(3.0, new Vector1D(3).getNorm(), TEST_TOLERANCE);
        Assert.assertEquals(3.0, new Vector1D(-3).getNorm(), TEST_TOLERANCE);
    }

    @Test
    public void testNormSq() {
        // act/assert
        Assert.assertEquals(0.0, new Vector1D(0).getNormSq(), TEST_TOLERANCE);
        Assert.assertEquals(9.0, new Vector1D(3).getNormSq(), TEST_TOLERANCE);
        Assert.assertEquals(9.0, new Vector1D(-3).getNormSq(), TEST_TOLERANCE);
    }

    @Test
    public void testNormInf() {
        // act/assert
        Assert.assertEquals(0.0, Vector1D.ZERO.getNormInf(), TEST_TOLERANCE);
        Assert.assertEquals(3.0, new Vector1D(3).getNormInf(), TEST_TOLERANCE);
        Assert.assertEquals(3.0, new Vector1D(-3).getNormInf(), TEST_TOLERANCE);
    }

    @Test
    public void testAdd() {
        // arrange
        Vector1D v1 = new Vector1D(1);
        Vector1D v2 = new Vector1D(-3);

        // act/assert
        v1 = v1.add(v2);
        checkVector(v1, -2);

        checkVector(v2.add(v1), -5);
        checkVector(v2.add(3, v1), -9);
    }

    @Test
    public void testSubtract() {
        // arrange
        Vector1D v1 = new Vector1D(1);
        Vector1D v2 = new Vector1D(-3);

        // act/assert
        v1 = v1.subtract(v2);
        checkVector(v1, 4);

        checkVector(v2.subtract(v1), -7);
        checkVector(v2.subtract(3, v1), -15);
    }

    @Test
    public void testNormalize() {
        // act/assert
        checkVector(new Vector1D(1).normalize(), 1);
        checkVector(new Vector1D(-1).normalize(), -1);
        checkVector(new Vector1D(5).normalize(), 1);
        checkVector(new Vector1D(-5).normalize(), -1);
    }

    @Test(expected = MathArithmeticException.class)
    public void testNormalize_zeroNorm() {
        // act
        Vector1D.ZERO.normalize();
    }

    @Test
    public void testNegate() {
        // act/assert
        checkVector(new Vector1D(0.1).negate(), -0.1);
        checkVector(new Vector1D(-0.1).negate(), 0.1);
    }

    @Test
    public void testScalarMultiply() {
        // act/assert
        checkVector(new Vector1D(1).scalarMultiply(3), 3);
        checkVector(new Vector1D(1).scalarMultiply(-3), -3);

        checkVector(new Vector1D(1.5).scalarMultiply(7), 10.5);
        checkVector(new Vector1D(-1.5).scalarMultiply(7), -10.5);
    }

    @Test
    public void testNaN() {
        // act/assert
        Assert.assertTrue(new Vector1D(Double.NaN).isNaN());
        Assert.assertFalse(new Vector1D(1).isNaN());
        Assert.assertFalse(new Vector1D(Double.NEGATIVE_INFINITY).isNaN());
    }

    @Test
    public void testInfinite() {
        // act/assert
        Assert.assertTrue(new Vector1D(Double.NEGATIVE_INFINITY).isInfinite());
        Assert.assertTrue(new Vector1D(Double.POSITIVE_INFINITY).isInfinite());
        Assert.assertFalse(new Vector1D(1).isInfinite());
        Assert.assertFalse(new Vector1D(Double.NaN).isInfinite());
    }

    @Test
    public void testDistance1() {
        // arrange
        Vector1D v1 = new Vector1D(1);
        Vector1D v2 = new Vector1D(-4);

        // act/assert
        Assert.assertEquals(0.0, v1.distance1(v1), TEST_TOLERANCE);

        Assert.assertEquals(5.0, v1.distance1(v2), TEST_TOLERANCE);
        Assert.assertEquals(v1.subtract(v2).getNorm1(), v1.distance1(v2), TEST_TOLERANCE);

        Assert.assertEquals(0.0, new Vector1D(-1).distance1(new Vector1D(-1)), TEST_TOLERANCE);
    }

    @Test
    public void testDistance() {
        // arrange
        Vector1D v1 = new Vector1D(1);
        Vector1D v2 = new Vector1D(-4);

        // act/assert
        Assert.assertEquals(0.0, v1.distance(v1), TEST_TOLERANCE);

        Assert.assertEquals(5.0, v1.distance(v2), TEST_TOLERANCE);
        Assert.assertEquals(5.0, v1.distance((Vector<Euclidean1D>) v2), TEST_TOLERANCE);
        Assert.assertEquals(v1.subtract(v2).getNorm(), v1.distance(v2), TEST_TOLERANCE);

        Assert.assertEquals(0.0, new Vector1D(-1).distance(new Vector1D(-1)), TEST_TOLERANCE);
    }

    @Test
    public void testDistance_static() {
        // arrange
        Vector1D v1 = new Vector1D(1);
        Vector1D v2 = new Vector1D(-4);

        // act/assert
        Assert.assertEquals(0.0, Vector1D.distance(v1, v1), TEST_TOLERANCE);

        Assert.assertEquals(5.0, Vector1D.distance(v1, v2), TEST_TOLERANCE);
        Assert.assertEquals(v1.subtract(v2).getNorm(), Vector1D.distance(v1, v2), TEST_TOLERANCE);

        Assert.assertEquals(0.0, Vector1D.distance(new Vector1D(-1), new Vector1D(-1)), TEST_TOLERANCE);
    }

    @Test
    public void testDistanceInf() {
        // arrange
        Vector1D v1 = new Vector1D(1);
        Vector1D v2 = new Vector1D(-4);

        // act/assert
        Assert.assertEquals(0.0, new Vector1D(-1).distanceInf(new Vector1D(-1)), TEST_TOLERANCE);
        Assert.assertEquals(5.0, v1.distanceInf(v2), TEST_TOLERANCE);

        Assert.assertEquals(v1.subtract(v2).getNormInf(), v1.distanceInf(v2), TEST_TOLERANCE);
    }

    @Test
    public void testDistanceInf_static() {
        // arrange
        Vector1D v1 = new Vector1D(1);
        Vector1D v2 = new Vector1D(-4);

        // act/assert
        Assert.assertEquals(0.0, Vector1D.distanceInf(new Vector1D(-1), new Vector1D(-1)), TEST_TOLERANCE);
        Assert.assertEquals(5.0, Vector1D.distanceInf(v1, v2), TEST_TOLERANCE);

        Assert.assertEquals(v1.subtract(v2).getNormInf(), Vector1D.distanceInf(v1, v2), TEST_TOLERANCE);
    }

    @Test
    public void testDistanceSq() {
        // arrange
        Vector1D v1 = new Vector1D(1);
        Vector1D v2 = new Vector1D(-4);

        // act/assert
        Assert.assertEquals(0.0, new Vector1D(-1).distanceSq(new Vector1D(-1)), TEST_TOLERANCE);
        Assert.assertEquals(25.0, v1.distanceSq(v2), TEST_TOLERANCE);

        Assert.assertEquals(Vector1D.distance(v1, v2) * Vector1D.distance(v1, v2),
                            v1.distanceSq(v2), TEST_TOLERANCE);
    }

    @Test
    public void testDistanceSq_static() {
        // arrange
        Vector1D v1 = new Vector1D(1);
        Vector1D v2 = new Vector1D(-4);

        // act/assert
        Assert.assertEquals(0.0, Vector1D.distanceSq(new Vector1D(-1), new Vector1D(-1)), TEST_TOLERANCE);
        Assert.assertEquals(25.0, Vector1D.distanceSq(v1, v2), TEST_TOLERANCE);

        Assert.assertEquals(Vector1D.distance(v1, v2) * Vector1D.distance(v1, v2),
                            Vector1D.distanceSq(v1, v2), TEST_TOLERANCE);
    }

    @Test
    public void testDotProduct() {
        // act/assert
        Assert.assertEquals(6.0, new Vector1D(2).dotProduct(new Vector1D(3)), TEST_TOLERANCE);
        Assert.assertEquals(-6.0, new Vector1D(2).dotProduct(new Vector1D(-3)), TEST_TOLERANCE);
    }

    @Test
    public void testEquals() {
        // arrange
        Vector1D u1 = new Vector1D(1);
        Vector1D u2 = new Vector1D(1);

        // act/assert
        Assert.assertFalse(u1.equals(null));
        Assert.assertFalse(u1.equals(new Object()));

        Assert.assertTrue(u1.equals(u1));
        Assert.assertTrue(u1.equals(u2));

        Assert.assertFalse(u1.equals(new Vector1D(-1)));
        Assert.assertFalse(u1.equals(new Vector1D(1 + 10 * Precision.EPSILON)));

        Assert.assertTrue(new Vector1D(Double.NaN).equals(new Vector1D(Double.NaN)));
    }

    @Test
    public void testHash() {
        // arrange
        Vector1D u = new Vector1D(1);
        Vector1D v = new Vector1D(1 + 10 * Precision.EPSILON);

        // act/assert
        Assert.assertTrue(u.hashCode() != v.hashCode());
        Assert.assertEquals(new Vector1D(Double.NaN).hashCode(), new Vector1D(Double.NaN).hashCode());
    }

    @Test
    public void testToString() {
        // act/assert
        Assert.assertEquals("{3}", new Vector1D(3).toString());
        Assert.assertEquals("{-3}", new Vector1D(-3).toString());
    }

    @Test
    public void testToString_numberFormat() {
        // arrange
        NumberFormat format = new DecimalFormat("0.000", new DecimalFormatSymbols(Locale.US));

        // act/assert
        Assert.assertEquals("{-1.000}", new Vector1D(-1).toString(format));
        Assert.assertEquals("{3.142}", new Vector1D(FastMath.PI).toString(format));
    }

    private void checkVector(Vector1D v, double x) {
        Assert.assertEquals(x, v.getX(), TEST_TOLERANCE);
    }
}
