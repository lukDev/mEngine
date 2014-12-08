/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.test;

import org.junit.Test;

import static mEngine.util.math.MathHelper.*;
import static org.junit.Assert.assertEquals;

/**
 * @author mtronics_dev (Maxi Schmeller)
 * @version 02.11.2014 21:15
 */
public class TestMathHelper {

    @Test
    public void testClamp() {
        assertEquals(6, clamp(6, 0, 7), 0);
        assertEquals(3, clamp(6, -4, 3), 0);
        assertEquals(7, clamp(6, 7, 9), 0);
    }

    @Test
    public void testClampMin() {
        assertEquals(9, clampMin(9, 7), 0);
        assertEquals(7, clampMin(3, 7), 0);
    }

    @Test
    public void testClampMax() {
        assertEquals(4, clampMax(4, 6), 0);
        assertEquals(2, clampMax(4, 2), 0);
    }

}
