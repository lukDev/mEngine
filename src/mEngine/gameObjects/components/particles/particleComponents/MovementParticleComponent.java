package mEngine.gameObjects.components.particles.particleComponents;

import mEngine.core.ObjectController;
import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.components.Component;
import mEngine.gameObjects.components.renderable.Camera;
import mEngine.graphics.Renderer;
import mEngine.util.math.vectors.VectorHelper;
import org.lwjgl.util.vector.Vector3f;

public class MovementParticleComponent extends ParticleComponent {

    Vector3f rotationAbility;

    public MovementParticleComponent(Vector3f rotationAbility) {

        this.rotationAbility = rotationAbility;

    }

    public void onUpdate() {

        Camera camera = null;

        if (Renderer.currentRenderQueue.camera != null) camera = Renderer.currentRenderQueue.camera;

        else {

            for (GameObject object : ObjectController.gameObjects) {

                for (Component component : object.components.values()) {

                    if (component instanceof Camera) camera = (Camera) component;

                }

            }

        }

        if (camera != null) {

            Vector3f cameraPosition = camera.position;
            Vector3f idealNormal = VectorHelper.normalizeVector(VectorHelper.subtractVectors(cameraPosition, parent.position));

            parent.normal = idealNormal;

            parent.rotation = new Vector3f(
                    VectorHelper.getAngle(new Vector3f(0, 0, 1), new Vector3f(0, parent.normal.y, parent.normal.z)),
                    VectorHelper.getAngle(new Vector3f(0, 0, 1), new Vector3f(parent.normal.x, 0, parent.normal.z)),
                    VectorHelper.getAngle(new Vector3f(0, 0, 1), new Vector3f(parent.normal.x, parent.normal.y, 0))
            );

        }

    }

}
