/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.gameObjects.modules.physics;

import mEngine.core.GameController;
import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.modules.Module;
import mEngine.gameObjects.modules.controls.Controller;
import mEngine.util.math.vectors.Matrix3f;
import mEngine.util.math.vectors.VectorHelper;
import mEngine.util.resources.PreferenceHelper;
import org.lwjgl.util.vector.Vector3f;

public class MovementModule extends Module {

    public Vector3f speed;
    public Vector3f movedSpace;
    Vector3f previousSpeed;
    boolean sprinting;
    boolean sneaking;
    float sprintModifier;
    float sneakModifier;
    private float[] forces;

    public MovementModule() {

        speed = new Vector3f();
        movedSpace = new Vector3f();
        previousSpeed = new Vector3f();

        sprintModifier = PreferenceHelper.getFloat("sprintModifier");
        sneakModifier = PreferenceHelper.getFloat("sneakModifier");

    }

    @Override
    public void onCreation(GameObject obj) {
        super.onCreation(obj);

        Controller controller = (Controller) parent.getModule(Controller.class);
        forces = controller.forceStrengths;
    }

    public void onUpdate() {

        if (!GameController.isGamePaused()) {

            Controller controller = (Controller) parent.getModule(Controller.class);

            if (controller != null) {

                if (!controller.sprintModeToggle) sprinting = false;
                if (!controller.sneakModeToggle) sneaking = false;

                controller.onRemoteUpdate();

            }

            parent.percentRotation = new Vector3f(0, 0, 1);

            Matrix3f xAxisRotationMatrix = new Matrix3f(
              new Vector3f(1, 0, 0),
              new Vector3f(0, (float) Math.cos(Math.toRadians(parent.rotation.x)), (float) -Math.sin(Math.toRadians(parent.rotation.x))),
              new Vector3f(0, (float) Math.sin(Math.toRadians(parent.rotation.x)), (float) Math.cos(Math.toRadians(parent.rotation.x))));
            parent.percentRotation = xAxisRotationMatrix.multiplyByVector(parent.percentRotation);

            Matrix3f yAxisRotationMatrix = new Matrix3f(
              new Vector3f((float) Math.cos(Math.toRadians(parent.rotation.y)), 0, (float) Math.sin(Math.toRadians(parent.rotation.y))),
              new Vector3f(0, 1, 0),
              new Vector3f((float) -Math.sin(Math.toRadians(parent.rotation.y)), 0, (float) Math.cos(Math.toRadians(parent.rotation.y))));
            parent.percentRotation = yAxisRotationMatrix.multiplyByVector(parent.percentRotation);


        }

    }

    private Vector3f calculateForce(Vector3f direction) {
        Vector3f force = new Vector3f();

        force.x = -(direction.x * (float) Math.sin(Math.toRadians(parent.rotation.y - 90)) + direction.z * (float) Math.sin(Math.toRadians(parent.rotation.y)));
        force.z = direction.x * (float) Math.cos(Math.toRadians(parent.rotation.y - 90)) + direction.z * (float) Math.cos(Math.toRadians(parent.rotation.y));

        force.y = direction.y;

        if (sneaking) {

            Vector3f newDirection = VectorHelper.multiplyVectors(new Vector3f[]{force, new Vector3f(0.3f, 1, 0.3f)});

            force.x = newDirection.x;
            force.z = newDirection.z;

        }

        return force;
    }

    private void applyForce(Vector3f direction) {

        if (sprinting) {
            direction.x *= sprintModifier;
            direction.y *= sprintModifier;
            direction.z *= sprintModifier;
        } else if (sneaking) {
            direction.x *= sneakModifier;
            direction.y *= sneakModifier;
            direction.z *= sneakModifier;
        }

        Vector3f force = calculateForce(direction);
        PhysicsModule physics = (PhysicsModule) parent.getModule(PhysicsModule.class);

        physics.applyCentralForce(new javax.vecmath.Vector3f(force.x, force.y, force.z));
    }

    public void moveForward() {
        applyForce(new Vector3f(0, 0, -forces[0]));
    }

    public void moveBackward() {
        applyForce(new Vector3f(0, 0, forces[1]));
    }

    public void moveLeft() {
        applyForce(new Vector3f(forces[2], 0, 0));
    }

    public void moveRight() {
        applyForce(new Vector3f(-forces[3], 0, 0));
    }

    public void moveDown() {
        applyForce(new Vector3f(0, -forces[4], 0));
    }

    public void moveUp() {
        applyForce(new Vector3f(0, forces[5], 0));
    }

    public void jump() {
        applyForce(new Vector3f(0, forces[6], 0));
    }

    public void sprint() {

        Controller controller = (Controller) parent.getModule(Controller.class);

        if (controller != null) {

            if (!controller.sprintModeToggle) {

                sprinting = true;
                sneaking = false;

            } else {

                sprinting = !sprinting;
                sneaking = false;

            }

        }

    }

    public void sneak() {

        Controller controller = (Controller) parent.getModule(Controller.class);


        if (controller != null)
            if (!sprinting)
                sneaking = !controller.sneakModeToggle || !sneaking;

    }

    public void rotate(float pitch, float yaw) {

        parent.rotation.x = pitch;
        parent.rotation.y = yaw;

    }

}
