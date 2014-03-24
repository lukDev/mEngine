package mEngine.util.debug;

import mEngine.gameObjects.components.gui.guiComponents.GUIText;

import static mEngine.util.RuntimeHelper.*;
import static mEngine.util.math.NumberFormatHelper.cutDecimals;

public class RAMTextComponent extends GUIText {

    public RAMTextComponent(String text, int fontSize) {

        super(text, fontSize);

    }

    @Override
    public void onUpdate() {

        super.onUpdate();
        text = String.valueOf("MemUsage: "
                + cutDecimals((float) getMemoryStats(USED_MEMORY) / getMemoryStats(TOTAL_MEMORY) * 100, 1)
                + "% [" + getMemoryStats(TOTAL_MEMORY) + " MB]");

    }

}