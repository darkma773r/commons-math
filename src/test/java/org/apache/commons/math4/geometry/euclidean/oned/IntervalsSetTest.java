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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math4.geometry.GeometryTestUtils;
import org.apache.commons.math4.geometry.partitioning.BSPTree;
import org.apache.commons.math4.geometry.partitioning.Region;
import org.apache.commons.math4.geometry.partitioning.RegionFactory;
import org.apache.commons.math4.geometry.partitioning.SubHyperplane;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.numbers.core.Precision;
import org.junit.Assert;
import org.junit.Test;

public class IntervalsSetTest {

    private static final double TEST_TOLERANCE = 1e-15;

    @Test
    public void testInterval_wholeNumberLine() {
        // act
        IntervalsSet set = new IntervalsSet(TEST_TOLERANCE);

        // assert
        Assert.assertEquals(TEST_TOLERANCE, set.getTolerance(), Precision.SAFE_MIN);
        Assert.assertEquals(Double.NEGATIVE_INFINITY, set.getInf(), TEST_TOLERANCE);
        Assert.assertEquals(Double.POSITIVE_INFINITY, set.getSup(), TEST_TOLERANCE);
        Assert.assertEquals(Double.POSITIVE_INFINITY, set.getSize(), TEST_TOLERANCE);
        Assert.assertEquals(0.0, set.getBoundarySize(), TEST_TOLERANCE);
        GeometryTestUtils.assertVectorEquals(Cartesian1D.NaN, (Cartesian1D) set.getBarycenter(), TEST_TOLERANCE);

        BSPTree<Euclidean1D> tree = set.getTree(true);
        Assert.assertEquals(Boolean.TRUE, tree.getAttribute());
        Assert.assertNull(tree.getCut());
        Assert.assertNull(tree.getMinus());
        Assert.assertNull(tree.getPlus());

        List<Interval> intervals = set.asList();
        Assert.assertEquals(1, intervals.size());
        Assert.assertEquals(Double.NEGATIVE_INFINITY, intervals.get(0).getInf(), TEST_TOLERANCE);
        Assert.assertEquals(Double.POSITIVE_INFINITY, intervals.get(0).getSup(), TEST_TOLERANCE);

        assertLocation(Region.Location.INSIDE, set, Double.NEGATIVE_INFINITY);
        assertLocation(Region.Location.INSIDE, set, 0.0);
        assertLocation(Region.Location.INSIDE, set, Double.POSITIVE_INFINITY);
    }

    @Test
    public void testInterval_openInterval_positive() {
        // act
        IntervalsSet set = new IntervalsSet(9.0, Double.POSITIVE_INFINITY, TEST_TOLERANCE);

        // assert
        Assert.assertEquals(TEST_TOLERANCE, set.getTolerance(), Precision.SAFE_MIN);
        Assert.assertEquals(9.0, set.getInf(), TEST_TOLERANCE);
        Assert.assertEquals(Double.POSITIVE_INFINITY, set.getSup(), TEST_TOLERANCE);
        Assert.assertEquals(Double.POSITIVE_INFINITY, set.getSize(), TEST_TOLERANCE);
        Assert.assertEquals(0.0, set.getBoundarySize(), TEST_TOLERANCE);
        GeometryTestUtils.assertVectorEquals(Cartesian1D.NaN, (Cartesian1D) set.getBarycenter(), TEST_TOLERANCE);

        List<Interval> intervals = set.asList();
        Assert.assertEquals(1, intervals.size());
        Assert.assertEquals(9.0, intervals.get(0).getInf(), TEST_TOLERANCE);
        Assert.assertEquals(Double.POSITIVE_INFINITY, intervals.get(0).getSup(), TEST_TOLERANCE);

        assertLocation(Region.Location.OUTSIDE, set, Double.NEGATIVE_INFINITY);
        assertLocation(Region.Location.OUTSIDE, set, 0.0);
        assertLocation(Region.Location.BOUNDARY, set, 9.0 - 1e-16);
        assertLocation(Region.Location.BOUNDARY, set, 9.0 + 1e-16);
        assertLocation(Region.Location.INSIDE, set, 10.0);
        assertLocation(Region.Location.INSIDE, set, Double.POSITIVE_INFINITY);
    }

    @Test
    public void testInterval_openInterval_negative() {
        // act
        IntervalsSet set = new IntervalsSet(Double.NEGATIVE_INFINITY, 9.0, TEST_TOLERANCE);

        // assert
        Assert.assertEquals(TEST_TOLERANCE, set.getTolerance(), Precision.SAFE_MIN);
        Assert.assertEquals(Double.NEGATIVE_INFINITY, set.getInf(), TEST_TOLERANCE);
        Assert.assertEquals(9.0, set.getSup(), TEST_TOLERANCE);
        Assert.assertEquals(Double.POSITIVE_INFINITY, set.getSize(), TEST_TOLERANCE);
        Assert.assertEquals(0.0, set.getBoundarySize(), TEST_TOLERANCE);
        GeometryTestUtils.assertVectorEquals(Cartesian1D.NaN, (Cartesian1D) set.getBarycenter(), TEST_TOLERANCE);

        List<Interval> intervals = set.asList();
        Assert.assertEquals(1, intervals.size());
        Assert.assertEquals(Double.NEGATIVE_INFINITY, intervals.get(0).getInf(), TEST_TOLERANCE);
        Assert.assertEquals(9.0, intervals.get(0).getSup(), TEST_TOLERANCE);

        assertLocation(Region.Location.INSIDE, set, Double.NEGATIVE_INFINITY);
        assertLocation(Region.Location.INSIDE, set, 0.0);
        assertLocation(Region.Location.BOUNDARY, set, 9.0 - 1e-16);
        assertLocation(Region.Location.BOUNDARY, set, 9.0 + 1e-16);
        assertLocation(Region.Location.OUTSIDE, set, 10.0);
        assertLocation(Region.Location.OUTSIDE, set, Double.POSITIVE_INFINITY);
    }

    @Test
    public void testInterval_singleClosedInterval() {
        // act
        IntervalsSet set = new IntervalsSet(-1.0, 9.0, TEST_TOLERANCE);

        // assert
        Assert.assertEquals(TEST_TOLERANCE, set.getTolerance(), Precision.SAFE_MIN);
        Assert.assertEquals(-1.0, set.getInf(), TEST_TOLERANCE);
        Assert.assertEquals(9.0, set.getSup(), TEST_TOLERANCE);
        Assert.assertEquals(10.0, set.getSize(), TEST_TOLERANCE);
        Assert.assertEquals(0.0, set.getBoundarySize(), TEST_TOLERANCE);
        GeometryTestUtils.assertVectorEquals(new Cartesian1D(4.0), (Cartesian1D) set.getBarycenter(), TEST_TOLERANCE);

        List<Interval> intervals = set.asList();
        Assert.assertEquals(1, intervals.size());
        Assert.assertEquals(-1.0, intervals.get(0).getInf(), TEST_TOLERANCE);
        Assert.assertEquals(9.0, intervals.get(0).getSup(), TEST_TOLERANCE);

        assertLocation(Region.Location.OUTSIDE, set, Double.NEGATIVE_INFINITY);
        assertLocation(Region.Location.OUTSIDE, set, -2.0);
        assertLocation(Region.Location.INSIDE, set, 0.0);
        assertLocation(Region.Location.BOUNDARY, set, 9.0 - 1e-16);
        assertLocation(Region.Location.BOUNDARY, set, 9.0 + 1e-16);
        assertLocation(Region.Location.OUTSIDE, set, 10.0);
        assertLocation(Region.Location.OUTSIDE, set, Double.POSITIVE_INFINITY);
    }

    @Test
    public void testFromBoundaries_wholeNumberLine() {
        // arrange
        List<SubHyperplane<Euclidean1D>> boundaries = new ArrayList<>();

        // act
        IntervalsSet set = new IntervalsSet(boundaries, TEST_TOLERANCE);

        // assert
        Assert.assertEquals(TEST_TOLERANCE, set.getTolerance(), Precision.SAFE_MIN);
        Assert.assertEquals(Double.NEGATIVE_INFINITY, set.getInf(), TEST_TOLERANCE);
        Assert.assertEquals(Double.POSITIVE_INFINITY, set.getSup(), TEST_TOLERANCE);
        Assert.assertEquals(Double.POSITIVE_INFINITY, set.getSize(), TEST_TOLERANCE);
        Assert.assertEquals(0.0, set.getBoundarySize(), TEST_TOLERANCE);
        GeometryTestUtils.assertVectorEquals(Cartesian1D.NaN, (Cartesian1D) set.getBarycenter(), TEST_TOLERANCE);

        BSPTree<Euclidean1D> tree = set.getTree(true);
        Assert.assertEquals(Boolean.TRUE, tree.getAttribute());
        Assert.assertNull(tree.getCut());
        Assert.assertNull(tree.getMinus());
        Assert.assertNull(tree.getPlus());

        List<Interval> intervals = set.asList();
        Assert.assertEquals(1, intervals.size());
        Assert.assertEquals(Double.NEGATIVE_INFINITY, intervals.get(0).getInf(), TEST_TOLERANCE);
        Assert.assertEquals(Double.POSITIVE_INFINITY, intervals.get(0).getSup(), TEST_TOLERANCE);

        assertLocation(Region.Location.INSIDE, set, Double.NEGATIVE_INFINITY);
        assertLocation(Region.Location.INSIDE, set, 0.0);
        assertLocation(Region.Location.INSIDE, set, Double.POSITIVE_INFINITY);
    }

    @Test
    public void testFromBoundaries_openInterval_positive() {
        // arrange
        List<SubHyperplane<Euclidean1D>> boundaries = new ArrayList<>();
        boundaries.add(new SubOrientedPoint(
                new OrientedPoint(new Cartesian1D(9.0), false, TEST_TOLERANCE), null));

        // act
        IntervalsSet set = new IntervalsSet(boundaries, TEST_TOLERANCE);

        // assert
        Assert.assertEquals(TEST_TOLERANCE, set.getTolerance(), Precision.SAFE_MIN);
        Assert.assertEquals(9.0, set.getInf(), TEST_TOLERANCE);
        Assert.assertEquals(Double.POSITIVE_INFINITY, set.getSup(), TEST_TOLERANCE);
        Assert.assertEquals(Double.POSITIVE_INFINITY, set.getSize(), TEST_TOLERANCE);
        Assert.assertEquals(0.0, set.getBoundarySize(), TEST_TOLERANCE);
        GeometryTestUtils.assertVectorEquals(Cartesian1D.NaN, (Cartesian1D) set.getBarycenter(), TEST_TOLERANCE);

        List<Interval> intervals = set.asList();
        Assert.assertEquals(1, intervals.size());
        Assert.assertEquals(9.0, intervals.get(0).getInf(), TEST_TOLERANCE);
        Assert.assertEquals(Double.POSITIVE_INFINITY, intervals.get(0).getSup(), TEST_TOLERANCE);

        assertLocation(Region.Location.OUTSIDE, set, Double.NEGATIVE_INFINITY);
        assertLocation(Region.Location.OUTSIDE, set, 0.0);
        assertLocation(Region.Location.BOUNDARY, set, 9.0 - 1e-16);
        assertLocation(Region.Location.BOUNDARY, set, 9.0 + 1e-16);
        assertLocation(Region.Location.INSIDE, set, 10.0);
        assertLocation(Region.Location.INSIDE, set, Double.POSITIVE_INFINITY);
    }

    @Test
    public void testFromBoundaries_openInterval_negative() {
        // arrange
        List<SubHyperplane<Euclidean1D>> boundaries = new ArrayList<>();
        boundaries.add(new SubOrientedPoint(
                new OrientedPoint(new Cartesian1D(9.0), true, TEST_TOLERANCE), null));

        // act
        IntervalsSet set = new IntervalsSet(boundaries, TEST_TOLERANCE);

        // assert
        Assert.assertEquals(TEST_TOLERANCE, set.getTolerance(), Precision.SAFE_MIN);
        Assert.assertEquals(Double.NEGATIVE_INFINITY, set.getInf(), TEST_TOLERANCE);
        Assert.assertEquals(9.0, set.getSup(), TEST_TOLERANCE);
        Assert.assertEquals(Double.POSITIVE_INFINITY, set.getSize(), TEST_TOLERANCE);
        Assert.assertEquals(0.0, set.getBoundarySize(), TEST_TOLERANCE);
        GeometryTestUtils.assertVectorEquals(Cartesian1D.NaN, (Cartesian1D) set.getBarycenter(), TEST_TOLERANCE);

        List<Interval> intervals = set.asList();
        Assert.assertEquals(1, intervals.size());
        Assert.assertEquals(Double.NEGATIVE_INFINITY, intervals.get(0).getInf(), TEST_TOLERANCE);
        Assert.assertEquals(9.0, intervals.get(0).getSup(), TEST_TOLERANCE);

        assertLocation(Region.Location.INSIDE, set, Double.NEGATIVE_INFINITY);
        assertLocation(Region.Location.INSIDE, set, 0.0);
        assertLocation(Region.Location.BOUNDARY, set, 9.0 - 1e-16);
        assertLocation(Region.Location.BOUNDARY, set, 9.0 + 1e-16);
        assertLocation(Region.Location.OUTSIDE, set, 10.0);
        assertLocation(Region.Location.OUTSIDE, set, Double.POSITIVE_INFINITY);
    }

    @Test
    public void testFromBoundaries_singleClosedInterval() {
        // arrange
        List<SubHyperplane<Euclidean1D>> boundaries = new ArrayList<>();
        boundaries.add(new SubOrientedPoint(
                new OrientedPoint(new Cartesian1D(-1.0), false, TEST_TOLERANCE), null));
        boundaries.add(new SubOrientedPoint(
                new OrientedPoint(new Cartesian1D(9.0), true, TEST_TOLERANCE), null));

        // act
        IntervalsSet set = new IntervalsSet(boundaries, TEST_TOLERANCE);

        // assert
        Assert.assertEquals(TEST_TOLERANCE, set.getTolerance(), Precision.SAFE_MIN);
        Assert.assertEquals(-1.0, set.getInf(), TEST_TOLERANCE);
        Assert.assertEquals(9.0, set.getSup(), TEST_TOLERANCE);
        Assert.assertEquals(10.0, set.getSize(), TEST_TOLERANCE);
        Assert.assertEquals(0.0, set.getBoundarySize(), TEST_TOLERANCE);
        GeometryTestUtils.assertVectorEquals(new Cartesian1D(4.0), (Cartesian1D) set.getBarycenter(), TEST_TOLERANCE);

        List<Interval> intervals = set.asList();
        Assert.assertEquals(1, intervals.size());
        Assert.assertEquals(-1.0, intervals.get(0).getInf(), TEST_TOLERANCE);
        Assert.assertEquals(9.0, intervals.get(0).getSup(), TEST_TOLERANCE);

        assertLocation(Region.Location.OUTSIDE, set, Double.NEGATIVE_INFINITY);
        assertLocation(Region.Location.OUTSIDE, set, -2.0);
        assertLocation(Region.Location.INSIDE, set, 0.0);
        assertLocation(Region.Location.BOUNDARY, set, 9.0 - 1e-16);
        assertLocation(Region.Location.BOUNDARY, set, 9.0 + 1e-16);
        assertLocation(Region.Location.OUTSIDE, set, 10.0);
        assertLocation(Region.Location.OUTSIDE, set, Double.POSITIVE_INFINITY);
    }

    @Test
    public void testInterval() {
        IntervalsSet set = new IntervalsSet(2.3, 5.7, 1.0e-10);
        Assert.assertEquals(3.4, set.getSize(), 1.0e-10);
        Assert.assertEquals(4.0, ((Cartesian1D) set.getBarycenter()).getX(), 1.0e-10);
        Assert.assertEquals(Region.Location.BOUNDARY, set.checkPoint(new Cartesian1D(2.3)));
        Assert.assertEquals(Region.Location.BOUNDARY, set.checkPoint(new Cartesian1D(5.7)));
        Assert.assertEquals(Region.Location.OUTSIDE,  set.checkPoint(new Cartesian1D(1.2)));
        Assert.assertEquals(Region.Location.OUTSIDE,  set.checkPoint(new Cartesian1D(8.7)));
        Assert.assertEquals(Region.Location.INSIDE,   set.checkPoint(new Cartesian1D(3.0)));
        Assert.assertEquals(2.3, set.getInf(), 1.0e-10);
        Assert.assertEquals(5.7, set.getSup(), 1.0e-10);
    }

    @Test
    public void testInfinite() {
        IntervalsSet set = new IntervalsSet(9.0, Double.POSITIVE_INFINITY, 1.0e-10);
        Assert.assertEquals(Region.Location.BOUNDARY, set.checkPoint(new Cartesian1D(9.0)));
        Assert.assertEquals(Region.Location.OUTSIDE,  set.checkPoint(new Cartesian1D(8.4)));
        for (double e = 1.0; e <= 6.0; e += 1.0) {
            Assert.assertEquals(Region.Location.INSIDE,
                                set.checkPoint(new Cartesian1D(FastMath.pow(10.0, e))));
        }
        Assert.assertTrue(Double.isInfinite(set.getSize()));
        Assert.assertEquals(9.0, set.getInf(), 1.0e-10);
        Assert.assertTrue(Double.isInfinite(set.getSup()));

        set = (IntervalsSet) new RegionFactory<Euclidean1D>().getComplement(set);
        Assert.assertEquals(9.0, set.getSup(), 1.0e-10);
        Assert.assertTrue(Double.isInfinite(set.getInf()));

    }

    @Test
    public void testMultiple() {
        RegionFactory<Euclidean1D> factory = new RegionFactory<>();
        IntervalsSet set = (IntervalsSet)
        factory.intersection(factory.union(factory.difference(new IntervalsSet(1.0, 6.0, 1.0e-10),
                                                              new IntervalsSet(3.0, 5.0, 1.0e-10)),
                                                              new IntervalsSet(9.0, Double.POSITIVE_INFINITY, 1.0e-10)),
                                                              new IntervalsSet(Double.NEGATIVE_INFINITY, 11.0, 1.0e-10));
        Assert.assertEquals(5.0, set.getSize(), 1.0e-10);
        Assert.assertEquals(5.9, ((Cartesian1D) set.getBarycenter()).getX(), 1.0e-10);
        Assert.assertEquals(Region.Location.OUTSIDE,  set.checkPoint(new Cartesian1D(0.0)));
        Assert.assertEquals(Region.Location.OUTSIDE,  set.checkPoint(new Cartesian1D(4.0)));
        Assert.assertEquals(Region.Location.OUTSIDE,  set.checkPoint(new Cartesian1D(8.0)));
        Assert.assertEquals(Region.Location.OUTSIDE,  set.checkPoint(new Cartesian1D(12.0)));
        Assert.assertEquals(Region.Location.INSIDE,   set.checkPoint(new Cartesian1D(1.2)));
        Assert.assertEquals(Region.Location.INSIDE,   set.checkPoint(new Cartesian1D(5.9)));
        Assert.assertEquals(Region.Location.INSIDE,   set.checkPoint(new Cartesian1D(9.01)));
        Assert.assertEquals(Region.Location.BOUNDARY, set.checkPoint(new Cartesian1D(5.0)));
        Assert.assertEquals(Region.Location.BOUNDARY, set.checkPoint(new Cartesian1D(11.0)));
        Assert.assertEquals( 1.0, set.getInf(), 1.0e-10);
        Assert.assertEquals(11.0, set.getSup(), 1.0e-10);

        List<Interval> list = set.asList();
        Assert.assertEquals(3, list.size());
        Assert.assertEquals( 1.0, list.get(0).getInf(), 1.0e-10);
        Assert.assertEquals( 3.0, list.get(0).getSup(), 1.0e-10);
        Assert.assertEquals( 5.0, list.get(1).getInf(), 1.0e-10);
        Assert.assertEquals( 6.0, list.get(1).getSup(), 1.0e-10);
        Assert.assertEquals( 9.0, list.get(2).getInf(), 1.0e-10);
        Assert.assertEquals(11.0, list.get(2).getSup(), 1.0e-10);

    }

    @Test
    public void testSinglePoint() {
        IntervalsSet set = new IntervalsSet(1.0, 1.0, 1.0e-10);
        Assert.assertEquals(0.0, set.getSize(), Precision.SAFE_MIN);
        Assert.assertEquals(1.0, ((Cartesian1D) set.getBarycenter()).getX(), Precision.EPSILON);
    }

    private void assertLocation(Region.Location location, IntervalsSet set, double pt) {
        Assert.assertEquals(location, set.checkPoint(new Cartesian1D(pt)));
    }
}
