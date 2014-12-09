/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.development;

import mEngine.core.GameController;
import mEngine.core.ObjectController;
import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.modules.Module;
import mEngine.gameObjects.modules.audio.AudioListener;
import mEngine.gameObjects.modules.controls.ControllerManual;
import mEngine.gameObjects.modules.gui.GUIElement;
import mEngine.gameObjects.modules.gui.modules.GUIQuad;
import mEngine.gameObjects.modules.physics.MovementModule;
import mEngine.gameObjects.modules.physics.PhysicsModule;
import mEngine.gameObjects.modules.renderable.Camera;
import mEngine.gameObjects.modules.renderable.RenderModule;
import mEngine.gameObjects.modules.renderable.Skybox;
import mEngine.gameObjects.modules.renderable.Terrain;
import mEngine.gameObjects.modules.renderable.light.SpotLightSource;
import mEngine.graphics.renderable.materials.Material2D;
import mEngine.graphics.renderable.materials.Material3D;
import mEngine.util.input.Input;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import java.util.Random;

public class SceneTerrain {

    /**
     * This is only for testing purposes.
     *
     * @param args None.
     */
    public static void main(String[] args) {

        Setup.setupDefaults();

        //GameObject Time ;)
        new GameObject(new Vector3f(0, 5, 0), new Vector3f(0, 0, 0))
          .addModule(new MovementModule())
          .addModule(new RenderModule("sphere"))
          .addModule(
            new ControllerManual(
              new float[]{1200, 1000, 1000, 1000, 1100, 1100, 2900}, //forward, backward, left, right, down, up, jump
              true //Can fly
            )
          )
          .addModule(new Skybox("peaks"))
          .addModule(new Camera())
          .addModule(new GUIElement(new Vector2f(5, 5), new Vector2f(334, 70))
            .setGUIScreen(Setup.alwaysActive)
            .setMaterial((Material2D) new Material2D().setTextureName("mng-anim/mng-anim"))
            .addModule(new GUIQuad()))
          .addModule(new Module() {
              @Override
              public void onUpdate() {
                  super.onUpdate();
                  if (Input.isKeyDown(Keyboard.KEY_C))
                      ((PhysicsModule) ObjectController.getGameObject(0).getModule(PhysicsModule.class))
                        .setVelocity(new javax.vecmath.Vector3f(), new javax.vecmath.Vector3f());
              }
          })
          .addModule(new AudioListener())
          .addModule(
            new PhysicsModule(60, PhysicsModule.CollisionShape.SPHERE)
              .setDamping(.5f, .5f)
              .setMargin(.1f)
              .setInertia(new javax.vecmath.Vector3f(.2f, .2f, .2f)))
          .createModules();

        float[][] heightmap = new float[100][100];
        Random rand = new Random();
        for (int x = 0; x < heightmap.length; x++) {
            for (int y = 0; y < heightmap[0].length; y++) {
                heightmap[x][y] = rand.nextFloat() / 80;
            }
        }

        new GameObject(new Vector3f(), new Vector3f())
          .addModule(new Terrain(heightmap, 40).setMaterial((Material3D) new Material3D().setTextureName("checkerPattern")))
          .createModules();

        new GameObject(new Vector3f(0, 10, 0), new Vector3f())
          .addModule(new SpotLightSource(new Vector4f(255, 255, 255, 700)).setSpecularLighting(false))
          .createModules();

        GameController.setLoading(false);

    }

}
