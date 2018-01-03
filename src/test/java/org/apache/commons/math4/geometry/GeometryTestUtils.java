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
package org.apache.commons.math4.geometry;

import java.util.Arrays;
import java.util.Stack;

import org.apache.commons.math4.geometry.euclidean.oned.Euclidean1D;
import org.apache.commons.math4.geometry.euclidean.oned.IntervalsSet;
import org.apache.commons.math4.geometry.euclidean.oned.OrientedPoint;
import org.apache.commons.math4.geometry.euclidean.oned.SubOrientedPoint;
import org.apache.commons.math4.geometry.euclidean.oned.Vector1D;
import org.apache.commons.math4.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math4.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math4.geometry.partitioning.BSPTree;
import org.apache.commons.math4.geometry.partitioning.BSPTreeVisitor;
import org.junit.Assert;

public class GeometryTestUtils {

    /**
     * Asserts that corresponding values in the given vectors are equal, using the specific
     * tolerance value.
     * @param expected
     * @param actual
     * @param tolerance
     */
    public static void assertVectorEquals(Vector1D expected, Vector1D actual, double tolerance) {
        String msg = "Expected vector to equal " + expected + " but was " + actual + ";";
        Assert.assertEquals(msg, expected.getX(), actual.getX(), tolerance);
    }

    /**
     * Asserts that corresponding values in the given vectors are equal, using the specific
     * tolerance value.
     * @param expected
     * @param actual
     * @param tolerance
     */
    public static void assertVectorEquals(Vector2D expected, Vector2D actual, double tolerance) {
        String msg = "Expected vector to equal " + expected + " but was " + actual + ";";
        Assert.assertEquals(msg, expected.getX(), actual.getX(), tolerance);
        Assert.assertEquals(msg, expected.getY(), actual.getY(), tolerance);
    }

    /**
     * Asserts that corresponding values in the given vectors are equal, using the specific
     * tolerance value.
     * @param expected
     * @param actual
     * @param tolerance
     */
    public static void assertVectorEquals(Vector3D expected, Vector3D actual, double tolerance) {
        String msg = "Expected vector to equal " + expected + " but was " + actual + ";";
        Assert.assertEquals(msg, expected.getX(), actual.getX(), tolerance);
        Assert.assertEquals(msg, expected.getY(), actual.getY(), tolerance);
        Assert.assertEquals(msg, expected.getZ(), actual.getZ(), tolerance);
    }

    public static void printTree1D(BSPTree<Euclidean1D> tree) {
        TreePrinter1D printer = new TreePrinter1D();
        System.out.println(printer.writeAsString(tree));
    }

    public static abstract class TreePrinter<S extends Space> implements BSPTreeVisitor<S> {

        protected static final String INDENT = "    ";

        protected int depth;

        protected StringBuilder sb = new StringBuilder();

        public String writeAsString(BSPTree<S> tree) {
            sb.delete(0, sb.length());

            tree.visit(this);

            return getString();
        }

        public String getString() {
            return sb.toString();
        }

        @Override
        public Order visitOrder(BSPTree<S> node) {
            return Order.SUB_MINUS_PLUS;
        }

        @Override
        public void visitInternalNode(BSPTree<S> node) {
            writeLinePrefix(node);
            writeInternalNode(node);

            write("\n");

            ++depth;
        }

        @Override
        public void visitLeafNode(BSPTree<S> node) {
            writeLinePrefix(node);
            writeLeafNode(node);

            write("\n");

            BSPTree<S> cur = node;
            while (cur.getParent() != null && cur.getParent().getPlus() == cur) {
                --depth;
                cur = cur.getParent();
            }
        }

        protected void writeLinePrefix(BSPTree<S> node) {
            for (int i=0; i<depth; ++i) {
                write(INDENT);
            }

            if (node.getParent() != null) {
                if (node.getParent().getMinus() == node) {
                    write("[-] ");
                }
                else {
                    write("[+] ");
                }
            }

            write(node.getClass().getSimpleName() + "@" + System.identityHashCode(node) + " | ");
        }

        protected void write(String str) {
            sb.append(str);
        }

        protected abstract void writeInternalNode(BSPTree<S> node);

        protected void writeLeafNode(BSPTree<S> node) {
            write(String.valueOf(node.getAttribute()));
        }
    }

    public static class TreePrinter1D extends TreePrinter<Euclidean1D> {

        @Override
        protected void writeInternalNode(BSPTree<Euclidean1D> node) {
            SubOrientedPoint cut = (SubOrientedPoint) node.getCut();

            OrientedPoint hyper = (OrientedPoint) cut.getHyperplane();
            write("cut = { hyperplane: ");
            if (hyper.isDirect()) {
                write("[" + hyper.getLocation().getX() + ", inf)");
            }
            else {
                write("(-inf, " + hyper.getLocation().getX() + "]");
            }

            IntervalsSet remainingRegion = (IntervalsSet) cut.getRemainingRegion();
            if (remainingRegion != null) {
                write(", region: [");

                boolean isFirst = true;
                for (double[] interval : remainingRegion) {
                    if (isFirst) {
                        isFirst = false;
                    }
                    else {
                        write(", ");
                    }
                    write(Arrays.toString(interval));
                }

                write("]");
            }

            write("}");
        }
    }
}
