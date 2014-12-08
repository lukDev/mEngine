/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.test;

import org.junit.Test;

import java.util.Locale;

import static mEngine.util.math.NumberFormatHelper.cutDecimals;
import static org.junit.Assert.assertEquals;

/**
 * @author mtronics_dev (Maxi Schmeller)
 * @version 02.11.2014 21:51
 */
public class TestNumberFormatHelper {

    @Test
    public void testNumberFormat() {
        Locale.setDefault(Locale.US);
        assertEquals("1", cutDecimals(1.2345, 0));
        assertEquals("1.23", cutDecimals(1.2345, 2));
    }

}
