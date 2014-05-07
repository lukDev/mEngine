package mEngine.gameObjects.components.renderable;

import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.components.particles.particleComponents.MovementParticleComponent;
import mEngine.gameObjects.components.particles.particleComponents.ParticleComponent;
import mEngine.graphics.Renderer;
import mEngine.util.math.vectors.Matrix3d;
import mEngine.util.math.vectors.VectorHelper;
import mEngine.util.rendering.TextureHelper;
import mEngine.util.resources.ResourceHelper;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Particle extends ComponentRenderable {

    public Vector3f position;
    public Vector3f rotation;
    public Vector3f normal;
    public List<Vector3f> vertices = new ArrayList<Vector3f>();
    public List<Vector2f> uvs = new ArrayList<Vector2f>();
    public Vector2f size;
    public Map<String, ParticleComponent> components = new HashMap<String, ParticleComponent>();
    String textureName;
    Texture texture;
    boolean isTextureThere = true;
    boolean[] displayListFactors = new boolean[]{false, false};
    int displayListIndex;

    public Particle(Vector2f size, String textureName) {

        this.size = size;
        this.textureName = textureName;

        vertices.add(VectorHelper.divideVectorByFloat(new Vector3f(-size.x, -size.y, 0), 2));
        vertices.add(VectorHelper.divideVectorByFloat(new Vector3f(size.x, -size.y, 0), 2));
        vertices.add(VectorHelper.divideVectorByFloat(new Vector3f(size.x, size.y, 0), 2));
        vertices.add(VectorHelper.divideVectorByFloat(new Vector3f(-size.x, size.y, 0), 2));

    }

    public void onCreation(GameObject object) {

        super.onCreation(object);

        displayListFactors[0] = true;

        position = new Vector3f(parent.position);
        normal = new Vector3f(parent.percentRotation);

        rotation = new Vector3f(
                VectorHelper.getAngle(new Vector3f(0, 0, 1), new Vector3f(0, normal.y, normal.z)),
                VectorHelper.getAngle(new Vector3f(0, 0, 1), new Vector3f(normal.x, 0, normal.z)),
                VectorHelper.getAngle(new Vector3f(0, 0, 1), new Vector3f(normal.x, normal.y, 0))
        );

    }

    public void onUpdate() {

        for (ParticleComponent particleComponent : components.values()) {

            particleComponent.onUpdate();

        }

    }

    public void render() {

        if (texture == null && isTextureThere) {

            File textureFile = ResourceHelper.getResource(textureName, ResourceHelper.RES_TEXTURE);

            if (!textureFile.exists()) isTextureThere = false;

            else {

                texture = TextureHelper.getTexture(textureName);

                uvs.add(new Vector2f(0, 1));
                uvs.add(new Vector2f(0, 0));
                uvs.add(new Vector2f(1, 0));
                uvs.add(new Vector2f(1, 1));


            }

        }

        if (displayListFactors[0] && !displayListFactors[1]) {

            List<Vector3f> renderVertices = new ArrayList<Vector3f>();

            renderVertices.add(VectorHelper.sumVectors(new Vector3f[]{vertices.get(0), position}));
            renderVertices.add(VectorHelper.sumVectors(new Vector3f[]{vertices.get(1), position}));
            renderVertices.add(VectorHelper.sumVectors(new Vector3f[]{vertices.get(2), position}));
            renderVertices.add(VectorHelper.sumVectors(new Vector3f[]{vertices.get(3), position}));

            List<Vector3f> normals = new ArrayList<Vector3f>();

            normals.add(normal);
            normals.add(normal);
            normals.add(normal);
            normals.add(normal);

            if (isTextureThere) {

                displayListIndex = Renderer.displayListCounter;
                Renderer.addDisplayList(renderVertices, normals, uvs, texture, Renderer.RENDER_QUADS);

            } else {

                displayListIndex = Renderer.displayListCounter;
                Renderer.addDisplayList(renderVertices, normals, Renderer.RENDER_QUADS);

            }

        }

        if (displayListFactors[0] && displayListFactors[1]) {

            Renderer.renderObject3D(displayListIndex, position, rotation, isTextureThere, 0);

        } else {

            List<Vector3f> normals = new ArrayList<Vector3f>();

            normals.add(normal);
            normals.add(normal);
            normals.add(normal);
            normals.add(normal);

            List<Vector3f> renderVertices = new ArrayList<Vector3f>();

            renderVertices.add(VectorHelper.sumVectors(new Vector3f[]{vertices.get(0), position}));
            renderVertices.add(VectorHelper.sumVectors(new Vector3f[]{vertices.get(1), position}));
            renderVertices.add(VectorHelper.sumVectors(new Vector3f[]{vertices.get(2), position}));
            renderVertices.add(VectorHelper.sumVectors(new Vector3f[]{vertices.get(3), position}));

            if (isTextureThere)
                Renderer.renderObject3D(renderVertices, normals, uvs, texture, Renderer.RENDER_QUADS, 0);

            else Renderer.renderObject3D(renderVertices, normals, Renderer.RENDER_QUADS, 0);

        }

    }

    public Particle addComponent(String componentName, ParticleComponent component) {

        components.put(componentName, component);
        component.onCreation(this);

        return this;

    }

    public void addToRenderQueue() {

        Renderer.currentRenderQueue.addParticle(this);

    }

}