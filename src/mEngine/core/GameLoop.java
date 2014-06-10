package mEngine.core;

import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.components.Component;
import mEngine.gameObjects.components.renderable.Camera;
import mEngine.gameObjects.components.renderable.RenderComponent;
import mEngine.graphics.GraphicsController;
import mEngine.util.input.Input;
import mEngine.util.serialization.Serializer;
import mEngine.util.time.TimeHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

public class GameLoop implements Runnable {

    public void run() {

        //noinspection StatementWithEmptyBody
        while (!Display.isCreated()) {
        } //Waiting for Display creation

        while (!Display.isCloseRequested() && !Thread.interrupted()) {

            if (!GameController.isLoading) {

                if (Input.isKeyDown(Keyboard.KEY_ESCAPE)) {

                    if (GameController.isGamePaused) GameController.unPauseGame();
                    else GameController.pauseGame();

                }

                if (Input.isKeyPressed(Keyboard.KEY_Y)) {

                    ObjectController.addGameObject(
                            new GameObject(ObjectController.getGameObject(0).position, new Vector3f())
                                    .addComponent(new RenderComponent("texturedStar"))
                                    .createAllComponents()
                    );

                }

                if (Input.isKeyDown(Keyboard.KEY_T)) TimeHelper.updateTimeModifier();

                if (Input.isKeyDown(Keyboard.KEY_O)) {

                    ObjectController.gameObjects.get(0).position = new Vector3f();
                    ObjectController.gameObjects.get(0).rotation = new Vector3f();
                    ObjectController.gameObjects.get(0).percentRotation = new Vector3f(0, 0, 1);

                    for (Component component : ObjectController.gameObjects.get(0).components) {

                        if (component instanceof Camera) ((Camera) component).zoom = 0;

                    }

                }

                if (Input.isKeyDown(Keyboard.KEY_B))
                    GraphicsController.isBlackAndWhite = !GraphicsController.isBlackAndWhite;

                if (Input.isKeyDown(Keyboard.KEY_N))
                    System.out.println(ObjectController.getGameObject(0).rotation + " / " + ObjectController.getGameObject(0).position);

            }

            TimeHelper.updateDeltaTime();
            if (ObjectController.getLoadingScreen() != null) ObjectController.getLoadingScreen().update();

            if (!GameController.isLoading) {

                for (GameObject gameObject : ObjectController.gameObjects) {

                    if (!Serializer.isSerializing) gameObject.update();

                }

                if (Input.isKeyDown(Keyboard.KEY_F9)) Serializer.serialize();
                if (Input.isKeyDown(Keyboard.KEY_F10)) Serializer.deSerializeLatest();

            }

            TimeHelper.updateTPS();

        }

        GameController.stopGame();

    }

}
