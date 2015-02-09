/*
 * Copyright (c) 2015 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.development;

import mEngine.gameObjects.modules.gui.GUIElement;
import mEngine.graphics.GraphicsController;
import mEngine.graphics.gui.GUIScreen;
import mEngine.graphics.gui.GUIScreenController;
import mEngine.graphics.renderable.LoadingScreen;
import mEngine.util.input.Input;
import mEngine.util.input.InputEventType;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import static mEngine.core.GameController.*;
import static mEngine.core.ObjectController.setLoadingScreen;
import static mEngine.core.events.EventController.addEventHandler;

/**
 * @author mtronics_dev (Maxi Schmeller)
 * @version 09.12.2014 18:13
 */
class Setup {

    static GUIScreen menuScreen;
    static GUIScreen inGame;
    static GUIScreen alwaysActive;

    static LoadingScreen standardLoadingScreen;

    static void setupDefaults() {

        standardLoadingScreen = new LoadingScreen("loadingScreen");

        setLoadingScreen(standardLoadingScreen);
        Mouse.setGrabbed(true);

        alwaysActive = new GUIScreen(true);
        inGame = new GUIScreen("gameResumed", "gamePaused", true);
        menuScreen = new GUIScreen("gamePaused", "gameResumed");

        runGame();
        addEventHandler("gamePaused", () -> Mouse.setGrabbed(false));
        addEventHandler("gameResumed", () -> Mouse.setGrabbed(true));

        GUIScreenController.addGUIScreen(menuScreen);
        GUIScreenController.addGUIScreen(inGame);
        GUIScreenController.addGUIScreen(alwaysActive);

        Input.assignInputEvent("pauseGame", true, InputEventType.ACTIVATED, Keyboard.KEY_ESCAPE);
        Input.assignInputEvent("screenshot", true, InputEventType.ACTIVATED, Keyboard.KEY_F2);

        alwaysActive.addElement(new GUIElement(new Vector2f()) {
            @Override
            public void onUpdate() {
                super.onUpdate();
                if (Input.inputEventTriggered("pauseGame")) {
                    if (isGamePaused()) resumeGame();
                    else pauseGame();
                }
            }

            @Override
            public void render() {
                super.render();
                if (Input.inputEventTriggered("screenshot")) GraphicsController.takeScreenshot();
            }
        });

    }

}
