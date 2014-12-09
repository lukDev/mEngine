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
import mEngine.gameObjects.modules.renderable.Particle;
import mEngine.gameObjects.modules.renderable.RenderModule;
import mEngine.gameObjects.modules.renderable.Skybox;
import mEngine.gameObjects.modules.renderable.Terrain;

public class FaceCountTextModule extends GUIText {

    public FaceCountTextModule(int fontSize) {
        super("FAC", fontSize);
    }

    @Override
    public void onUpdate() {

        super.onUpdate();

        int faceCount = 0;

        for (GameObject object : ObjectController.gameObjects) {

            for (Module module : object.modules) {

                if (module instanceof RenderModule)
                    faceCount += ((RenderModule) module).model.getFaces().size();
                else if (module instanceof Terrain) faceCount += ((Terrain) module).model.getFaces().size();
                else if (module instanceof Particle) faceCount++;
                else if (module instanceof Skybox) faceCount += 6;

            }

        }

        text = "faces: " + faceCount;

    }

}
