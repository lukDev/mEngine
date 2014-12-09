/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.util.debug.texts;

import mEngine.gameObjects.modules.gui.modules.GUIText;

import static mEngine.util.debug.RuntimeHelper.*;
import static mEngine.util.math.NumberFormatHelper.cutDecimals;

public class RAMTextModule extends GUIText {

    public RAMTextModule(int fontSize) {
        super("RAM", fontSize);
    }

    @Override
    public void onUpdate() {

        super.onUpdate();
        text = String.valueOf("Memory usage: "
          + cutDecimals((float) getMemoryStats(USED_MEMORY) / getMemoryStats(TOTAL_MEMORY) * 100, 1)
          + "% [" + getMemoryStats(TOTAL_MEMORY) + " MB]");

    }

}
