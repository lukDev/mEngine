/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.gameObjects.modules.renderable;

import mEngine.graphics.Renderer;
import mEngine.util.math.vectors.Matrix3f;
import mEngine.util.math.vectors.VectorHelper;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluLookAt;

public class Camera extends ModuleRenderable {

    public float zoom = 0;
    private Vector3f eye;

    public Camera() {
        super();
    }

    public void onUpdate() {

        if (!(Float.isNaN(parent.position.x) || Float.isNaN(parent.position.y) || Float.isNaN(parent.position.z)))
            eye = VectorHelper.sumVectors(
                    new Vector3f[]{VectorHelper.multiplyVectorByFloat(
                            new Vector3f(parent.percentRotation.x,
                                    parent.percentRotation.y,
                                    -parent.percentRotation.z),
                            -zoom),
                            parent.position});

    }

    public void render() {

        glLoadIdentity();

        if (zoom == 0) {

            glRotatef(parent.rotation.x, 1, 0, 0);
            glRotatef(parent.rotation.y, 0, 1, 0);
            glRotatef(parent.rotation.z, 0, 0, 1);

            glTranslatef(-parent.position.x, -parent.position.y, -parent.position.z);

        } else {

            Vector3f up = new Vector3f(0, 1, 0);

            if (parent.rotation.z != 0) {

                float radiantRotation = (float) -Math.toRadians(parent.rotation.z);

                Matrix3f zAxisRotationMatrix = new Matrix3f(
                  new Vector3f((float) Math.cos(radiantRotation), (float) -Math.sin(radiantRotation), 0),
                  new Vector3f((float) Math.sin(radiantRotation), (float) Math.cos(radiantRotation), 0),
                  new Vector3f(0, 0, 1)
                );

                up = zAxisRotationMatrix.multiplyByVector(up);

            }

            gluLookAt(eye.x, eye.y, eye.z, parent.position.x, parent.position.y, parent.position.z, up.x, up.y, up.z);

        }

    }

    @Override
    public void addToRenderQueue() {

        Renderer.currentRenderQueue.setCamera(this);

    }

}