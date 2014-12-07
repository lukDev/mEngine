/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.gameObjects.modules.physics;

import com.bulletphysics.collision.dispatch.CollisionObject;
import com.bulletphysics.collision.shapes.*;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;
import com.bulletphysics.util.ObjectArrayList;
import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.modules.Module;
import mEngine.gameObjects.modules.renderable.RenderModule;
import mEngine.physics.PhysicsController;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;
import java.util.stream.Collectors;

public class PhysicsModule extends Module {

    private RigidBody body;
    private CollisionShape shape;

    /**
     * Creates a new physics module
     *
     * @param mass  The mass of the module
     * @param shape The collision shape (note that CUSTOM can get very computationally heavy)
     */
    public PhysicsModule(float mass, CollisionShape shape) {
        this.shape = shape;
        body = new RigidBody(mass, new DefaultMotionState(), new SphereShape(0)); //Only temporary data
    }

    /**
     * Sets up the rigid body and the module itself
     *
     * @param obj The parent of this module
     */
    @Override
    public void onCreation(GameObject obj) {

        super.onCreation(obj);

        Quat4f rotation = new Quat4f(
          parent.rotation.x,
          parent.rotation.y,
          parent.rotation.z,
          1
        );

        Vector3f position = new Vector3f(
          parent.position.x,
          parent.position.y,
          parent.position.z
        );

        MotionState state = new DefaultMotionState(new Transform(new Matrix4f(rotation, position, 1)));
        com.bulletphysics.collision.shapes.CollisionShape collisionShape;

        //Gets the difference between point a and point b
        float x = parent.getBoundingBox().getB().x - parent.getBoundingBox().getA().x;
        float y = parent.getBoundingBox().getB().y - parent.getBoundingBox().getA().y;
        float z = parent.getBoundingBox().getB().z - parent.getBoundingBox().getA().z;

        switch (shape) {
            case SPHERE:
                float rSphere = (x + y + z) / 6; // Divide by 3 (3 values) and by 2 (diameter to radius) => div. by 6
                collisionShape = new SphereShape(rSphere);
                break;

            case CAPSULE:
                float rCapsule = x + z / 4; //Div. by 2 (2 vals) and by 2 again (diameter to radius) => div. by 4
                collisionShape = new CapsuleShape(rCapsule, y);
                break;

            case PLANE:
                collisionShape = new StaticPlaneShape(new Vector3f(0, 1, 0), .25f);
                break;

            case CUSTOM:
                ObjectArrayList<Vector3f> points = new ObjectArrayList<>();

                RenderModule module = (RenderModule) parent.getModule(RenderModule.class);

                points.addAll(module.model.getVertices().stream()           //Sexy new Java 8 code here!
                  .map(point -> new Vector3f(point.x, point.y, point.z))    //Lambdas are awesome.
                  .collect(Collectors.toList()));                           //And so is Java 8!

                collisionShape = new ConvexHullShape(points);
                break;

            default:
                collisionShape = new BoxShape(new Vector3f(x / 2, y / 2, z / 2));
                break;

        }

        body.setCollisionShape(collisionShape);
        body.setMotionState(state);
        body.setActivationState(CollisionObject.DISABLE_DEACTIVATION);
        PhysicsController.world.addRigidBody(body);

    }

    /**
     * Transfers the calculated position to the game object
     */
    @Override
    public void onUpdate() {
        super.onUpdate();

        Transform transform = new Transform();
        body.getInterpolationWorldTransform(transform);

        org.lwjgl.util.vector.Vector3f position =
          new org.lwjgl.util.vector.Vector3f(transform.origin.x, transform.origin.y, transform.origin.z);

        parent.position = new org.lwjgl.util.vector.Vector3f(position);
    }

    /**
     * Destroys the module and removes the rigid body
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        PhysicsController.world.removeRigidBody(body);
    }

    /**
     * Sets the friction for sliding (linear) and rolling (angular)
     *
     * @param linear  The "sliding" friction (0 - no friction, 1 - high friction)
     * @param angular The "rolling" friction (0 - no friction, 1 - high friction)
     * @return The physics module, for easy chaining
     */
    public PhysicsModule setDamping(float linear, float angular) {
        body.setDamping(linear, angular);
        return this;
    }

    /**
     * Changes the gravity vector for this specific physics module
     *
     * @param gravity The new gravity vector
     * @return The physics module, for easy chaining
     */
    public PhysicsModule setGravity(Vector3f gravity) {
        body.setGravity(gravity);
        return this;
    }

    /**
     * Sets the collision margin for the rigid body, increases performance significantly
     *
     * @param margin The margin to use, the higher, the more obvious it will be
     * @return The physics module, for easy chaining
     */
    public PhysicsModule setMargin(float margin) {
        body.getCollisionShape().setMargin(margin);
        return this;
    }

    public PhysicsModule setRestitution(float restitution) {
        body.setRestitution(restitution);
        return this;
    }

    public PhysicsModule setInertia(Vector3f inertia) {
        body.setMassProps(1f / body.getInvMass(), inertia);
        return this;
    }

    public PhysicsModule setVelocity(Vector3f linear, Vector3f angular) {
        body.setLinearVelocity(linear);
        body.setAngularVelocity(angular);
        return this;
    }

    /**
     * Apply a force in the center of the object
     *
     * @param force The force in Newton
     */
    public void applyCentralForce(Vector3f force) {
        body.activate(true);
        body.applyCentralForce(force);
    }

    /**
     * Apply a force anywhere on (or in) the body
     *
     * @param force The force in Newton
     * @param point The desired spot where the force should be applied
     */
    public void applyForce(Vector3f force, Vector3f point) {
        body.activate(true);
        body.applyForce(force, point);
    }

    public enum CollisionShape {
        SPHERE, BOX, CAPSULE, PLANE, CUSTOM
    }

}
