/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.test;

import mEngine.core.events.EventController;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author mtronics_dev (Maxi Schmeller)
 * @version 04.10.2014 02:17
 */
public class TestEvents {

    @Test
    public void testEvents() {
        EventController.addEventHandler("testEvent", () -> assertTrue(true));
        EventController.triggerEvent("testEvent");
    }

}
