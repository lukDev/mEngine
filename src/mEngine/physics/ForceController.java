package mEngine.physics;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class ForceController {

    public static List<Force> forces = new ArrayList<Force>();

    public static final float X_Y_RATIO = 0.7068913f;

    public static final float X_Y_Z_RATIO = 0.57765603f;

    public static void addForce(Vector3f direction) { forces.add(new Force(direction)); }

    public static Vector3f sumForces(List<Force> forces) {

        Vector3f forceSum = new Vector3f();

        for(Force force : forces) {

            forceSum.x += force.getDirectionalForceIfEnabled().x;
            forceSum.y += force.getDirectionalForceIfEnabled().y;
            forceSum.z += force.getDirectionalForceIfEnabled().z;

        }

        return forceSum;

    }

    public static Vector3f getAcceleration(Vector3f forceDirection, float mass) {

        Vector3f acceleration = new Vector3f();

        acceleration.x = forceDirection.x / mass;
        acceleration.y = forceDirection.y / mass;
        acceleration.z = forceDirection.z / mass;

        return acceleration;

    }

    public static Vector3f getMovedSpace(Vector3f acceleration, Vector3f speed, float timeInSeconds) {

        Vector3f movedSpace = new Vector3f();

        movedSpace.x = 0.5f * acceleration.x * (float)Math.pow(timeInSeconds, 2) + speed.x * timeInSeconds;
        movedSpace.y = 0.5f * acceleration.y * (float)Math.pow(timeInSeconds, 2) + speed.y * timeInSeconds;
        movedSpace.z = 0.5f * acceleration.z * (float)Math.pow(timeInSeconds, 2) + speed.z * timeInSeconds;

        return movedSpace;

    }

    public static Vector3f getSpeed(Vector3f acceleration, Vector3f startSpeed, float timeInSeconds) {

        Vector3f speed = new Vector3f();

        speed.x = acceleration.x * timeInSeconds + startSpeed.x;
        speed.y = acceleration.y * timeInSeconds + startSpeed.y;
        speed.z = acceleration.z * timeInSeconds + startSpeed.z;

        return speed;

    }

    public static Vector2f getCombinedForces(float directionA, float directionB) {

        Vector2f combinedForce = new Vector2f();

        combinedForce.x = directionA * X_Y_RATIO;
        combinedForce.y = directionB * X_Y_RATIO;

        return combinedForce;

    }

    public static Vector3f getCombinedForces(float directionA, float directionB, float directionC) {

        Vector3f combinedForce = new Vector3f();

        combinedForce.x = directionA * X_Y_Z_RATIO;
        combinedForce.y = directionB * X_Y_Z_RATIO;
        combinedForce.z = directionC * X_Y_Z_RATIO;

        return combinedForce;

    }

}
