/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.test;

import mEngine.util.time.TimeHelper;
import mEngine.util.time.Timer;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author mtronics_dev (Maxi Schmeller)
 * @version 02.11.2014 21:30
 */
public class TestTimer {

    @Test
    public void testTimer() {
        Timer testTimer = new Timer(100);
        testTimer.start();
        TimeHelper.sleep(100);
        assertTrue(testTimer.isDone() && testTimer.isRunning());

        testTimer = new Timer(1000);
        testTimer.start();
        assertFalse(testTimer.isDone());
    }

}
