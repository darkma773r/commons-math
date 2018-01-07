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
package org.apache.commons.math4.geometry.euclidean.threed;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Tests to exercise the PolyhedronsSet class with complex
 * 3D models.
 * @author matt
 */
public class PolyhedronsSetComplexModelTest {

    private static final double EPSILON = 1e-6;

    @Test
    public void testLoadTeapot_lowTolerance() throws Exception {
        // arrange
        double tolerance = 1e-12;

        // act
        PolyhedronsSet poly = loadPLYResource("teapot_wt.ply", tolerance);

        // assert
        PerfTimer computeProperties = PerfTimer.create("compute properties");

        Assert.assertEquals(0.829972322, poly.getSize(), EPSILON);

        computeProperties.stop();
    }

    @Test
    public void testLoadTeapot_midTolerance() throws Exception {
        // arrange
        double tolerance = 1e-10;

        // act
        PolyhedronsSet poly = loadPLYResource("teapot_wt.ply", tolerance);

        // assert
        PerfTimer computeProperties = PerfTimer.create("compute properties");

        Assert.assertEquals(0.829972322, poly.getSize(), EPSILON);

        computeProperties.stop();
    }

    @Test
    public void testLoadTeapot_highTolerance() throws Exception {
        // arrange
        double tolerance = 1e-3;

        // act
        PolyhedronsSet poly = loadPLYResource("teapot_wt.ply", tolerance);

        // assert
        PerfTimer computeProperties = PerfTimer.create("compute properties");

        Assert.assertEquals(0.829972322, poly.getSize(), EPSILON);

        computeProperties.stop();
    }

    @Ignore // this test takes too long to run
    @Test
    public void testLoadBunny() throws Exception {
        // arrange
        double tolerance = 1e-10;

        // act
        PolyhedronsSet poly = loadPLYResource("bunny_wt.ply", tolerance);

        // assert
        PerfTimer computeProperties = PerfTimer.create("compute properties");

        Assert.assertEquals(0.829972322, poly.getSize(), tolerance);

        computeProperties.stop();
    }

    private PolyhedronsSet loadPLYResource(String name, double tolerance) throws IOException, ParseException {
        try (InputStream is = this.getClass().getResourceAsStream(name)){
            if (is == null) {
                throw new IOException("Failed to find resource with name " + name);
            }

            PLYParser parser = new PLYParser(is);

            PerfTimer t = PerfTimer.create("PolyhedronsSet from " + name);
            PolyhedronsSet poly = new PolyhedronsSet(parser.getVertices(), parser.getFaces(), tolerance);
            t.stop();

            return poly;
        }
    }

    private static class PerfTimer {
        private String name;
        private long startTime;

        public PerfTimer(String name) {
            this.name = name;
        }

        public void start() {
            startTime = System.nanoTime();
        }

        public void stop() {
            Duration duration = Duration.of(System.nanoTime() - startTime, ChronoUnit.NANOS);
            String durationStr = duration.getSeconds() + " sec " + (duration.getNano() / 1e6) + " ms";

            System.out.println("[PerfTimer] \"" + name + "\" completed in " + durationStr);
        }

        public static PerfTimer create(String name) {
            PerfTimer timer = new PerfTimer(name);
            timer.start();

            return timer;
        }
    }
}
