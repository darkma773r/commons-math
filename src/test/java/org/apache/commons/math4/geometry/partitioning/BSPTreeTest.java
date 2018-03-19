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
package org.apache.commons.math4.geometry.partitioning;

import org.apache.commons.math4.geometry.GeometryTestUtils;
import org.apache.commons.math4.geometry.euclidean.oned.Cartesian1D;
import org.apache.commons.math4.geometry.euclidean.oned.Euclidean1D;
import org.apache.commons.math4.geometry.euclidean.oned.OrientedPoint;
import org.apache.commons.math4.geometry.euclidean.oned.SubOrientedPoint;
import org.junit.Assert;
import org.junit.Test;

public class BSPTreeTest {

    private static final double TEST_TOLERANCE = 1e-10;

    @Test
    public void testDefaultConstructor() {
        // act
        BSPTree<Euclidean1D> tree = new BSPTree<>();

        // assert
        Assert.assertNull(tree.getParent());
        Assert.assertNull(tree.getCut());
        Assert.assertNull(tree.getPlus());
        Assert.assertNull(tree.getMinus());
        Assert.assertNull(tree.getAttribute());
    }

    @Test
    public void testSingleArgConstructor() {
        // arrange
        Object attr = new Object();

        // act
        BSPTree<Euclidean1D> tree = new BSPTree<>(attr);

        // assert
        Assert.assertNull(tree.getParent());
        Assert.assertNull(tree.getCut());
        Assert.assertNull(tree.getPlus());
        Assert.assertNull(tree.getMinus());
        Assert.assertSame(attr, tree.getAttribute());
    }

    @Test
    public void testFullConstructor() {
        // arrange
        SubOrientedPoint cut = subOrientedPoint(0, true);
        BSPTree<Euclidean1D> plus = new BSPTree<>();
        BSPTree<Euclidean1D> minus = new BSPTree<>();
        Object attr = new Object();

        // act
        BSPTree<Euclidean1D> tree = new BSPTree<>(cut, plus, minus, attr);

        // assert
        Assert.assertNull(tree.getParent());
        Assert.assertSame(cut, tree.getCut());

        Assert.assertSame(plus, tree.getPlus());
        Assert.assertSame(tree, tree.getPlus().getParent());

        Assert.assertSame(minus, tree.getMinus());
        Assert.assertSame(tree, tree.getMinus().getParent());

        Assert.assertSame(attr, tree.getAttribute());
    }

    @Test
    public void testInsertCut_treeRoot() {
        // arrange
        BSPTree<Euclidean1D> tree = tree1D();
        OrientedPoint hyper = orientedPoint(0, true);

        // act
        boolean result = tree.insertCut(hyper);

        // assert
        Assert.assertTrue(result);

        SubOrientedPoint cut = (SubOrientedPoint) tree.getCut();
        Assert.assertEquals(0, cut.getHyperplane().getOffset(new Cartesian1D(0)), TEST_TOLERANCE);

        Assert.assertNotNull(tree.getPlus());
        Assert.assertSame(tree, tree.getPlus().getParent());

        Assert.assertNotNull(tree.getMinus());
        Assert.assertSame(tree, tree.getMinus().getParent());
    }

    @Test
    public void testInsertCut_internalNode() {
        // arrange
        BSPTree<Euclidean1D> targetNode = tree1D(3, true);

        BSPTree<Euclidean1D> tree = tree1D(0, true,
                    tree1D(5, false,
                            targetNode,
                            tree1D()),
                    tree1D(-1, false)
                );
        OrientedPoint hyper = orientedPoint(4, true);

        // act
        boolean result = targetNode.insertCut(hyper);

        // assert
        Assert.assertTrue(result);

        SubOrientedPoint cut = (SubOrientedPoint) targetNode.getCut();
        Assert.assertEquals(0, cut.getHyperplane().getOffset(new Cartesian1D(4)), TEST_TOLERANCE);

        Assert.assertNotNull(targetNode.getPlus());
        Assert.assertSame(targetNode, targetNode.getPlus().getParent());

        Assert.assertNotNull(targetNode.getMinus());
        Assert.assertSame(targetNode, targetNode.getMinus().getParent());
    }

    private BSPTree<Euclidean1D> tree1D() {
        return new BSPTree<Euclidean1D>();
    }

    private BSPTree<Euclidean1D> tree1D(double location, boolean direct) {
        return tree1D(subOrientedPoint(location, direct), tree1D(), tree1D());
    }

    private BSPTree<Euclidean1D> tree1D(double location, boolean direct, BSPTree<Euclidean1D> plus, BSPTree<Euclidean1D> minus) {
        return tree1D(subOrientedPoint(location, direct), plus, minus);
    }

    private BSPTree<Euclidean1D> tree1D(SubOrientedPoint cut, BSPTree<Euclidean1D> plus, BSPTree<Euclidean1D> minus) {
        return new BSPTree<Euclidean1D>(cut, plus, minus, null);
    }

    private OrientedPoint orientedPoint(double location, boolean direct) {
        return new OrientedPoint(new Cartesian1D(location), direct, TEST_TOLERANCE);
    }

    private SubOrientedPoint subOrientedPoint(double location, boolean direct) {
        // the remaining region isn't necessary for creating 1D boundaries so we can set it to null here
        return new SubOrientedPoint(orientedPoint(location, direct), null);
    }
}
