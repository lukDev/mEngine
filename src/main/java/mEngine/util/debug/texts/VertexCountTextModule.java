/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.util.debug.texts;

import mEngine.core.ObjectController;
import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.modules.Module;
import mEngine.gameObjects.modules.gui.modules.GUIText;
import mEngine.gameObjects.modules.renderable.RenderModule;

public class VertexCountTextModule extends GUIText {

    public VertexCountTextModule(int fontSize) {
        super("VTX", fontSize);
    }

    @Override
    public void onUpdate() {

        super.onUpdate();

        int vertexCount = 0;

        for (GameObject object : ObjectController.gameObjects) {

            for (Module module : object.modules) {

                if (module instanceof RenderModule)
                    vertexCount += ((RenderModule) module).model.getVertices().size() * 3;

            }

        }

        text = "vertices: " + vertexCount;

    }

}
