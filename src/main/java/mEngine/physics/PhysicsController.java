/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.physics;

import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.broadphase.DbvtBroadphase;
import com.bulletphysics.collision.dispatch.CollisionConfiguration;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.DynamicsWorld;
import com.bulletphysics.dynamics.constraintsolver.ConstraintSolver;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import mEngine.util.data.DataTypeHelper;
import mEngine.util.resources.PreferenceHelper;

public class PhysicsController {

    public static DynamicsWorld world; //The physics world

    /**
     * Creates the physics world and sets up collisions.
     */
    public static void initialize() {
        BroadphaseInterface aabbInterface = new DbvtBroadphase(); //Checks if objects could collide
        CollisionConfiguration collisionConfiguration = new DefaultCollisionConfiguration();
        CollisionDispatcher collisionDispatcher = new CollisionDispatcher(collisionConfiguration); //Handles collisions
        ConstraintSolver constraintSolver = new SequentialImpulseConstraintSolver();

        world = new DiscreteDynamicsWorld(collisionDispatcher, aabbInterface, constraintSolver, collisionConfiguration);
        world.setGravity(DataTypeHelper.stringToVector3f(PreferenceHelper.getValue("gravity")));
    }

}
