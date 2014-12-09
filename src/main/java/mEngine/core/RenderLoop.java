/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.core;

import mEngine.graphics.GraphicsController;
import mEngine.graphics.RenderQueue;
import mEngine.graphics.Renderer;
import mEngine.util.rendering.ShaderHelper;
import mEngine.util.serialization.Serializer;
import mEngine.util.time.TimeHelper;
import org.lwjgl.opengl.Display;

public class RenderLoop {

    /**
     * Creates a new OpenGL window, sets the standard shader and runs the render loop
     */
    public static void startLoop() {

        GraphicsController.createDisplay();
        ShaderHelper.addShader("lighting");

        while (!Display.isCloseRequested() && !Thread.interrupted()) {

            GraphicsController.clearScreen();
            Renderer.currentRenderQueue = new RenderQueue();

            if (!GameController.isLoading()) {
                if (!Serializer.isSerializing) {
                    //Renders all the gameObjects
                    ObjectController.gameObjects.forEach(mEngine.gameObjects.GameObject::addToRenderQueue);
                }
                Renderer.currentRenderQueue.render();
            }

            ObjectController.getLoadingScreen().render();

            TimeHelper.updateFPS();
            GraphicsController.update();

        }

        GameController.stopGame();

    }

}
