/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.gameObjects.modules.renderable;

import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.modules.particles.modules.ParticleComponent;
import mEngine.graphics.Renderer;
import mEngine.util.math.vectors.VectorHelper;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import java.util.ArrayList;
import java.util.List;

public class Particle extends ModuleRenderable3D {

    public Vector3f position;
    public Vector3f rotation;
    public Vector3f normal;
    public List<Vector3f> vertices = new ArrayList<Vector3f>();
    public List<Vector2f> uvs = new ArrayList<Vector2f>();
    public Vector2f size;
    public List<ParticleComponent> components = new ArrayList<ParticleComponent>();
    String textureName;
    Texture texture;
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

        for (ParticleComponent particleComponent : components) {

            particleComponent.onUpdate();

        }

    }

    public void render() {

        if (material.hasTexture() && material.getTexture() == null) {

            uvs.add(new Vector2f(0, 1));
            uvs.add(new Vector2f(0, 0));
            uvs.add(new Vector2f(1, 0));
            uvs.add(new Vector2f(1, 1));

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

            displayListIndex = Renderer.displayListCounter;
            Renderer.addDisplayList(renderVertices, normals, uvs, material, Renderer.RENDER_QUADS);

        }

        if (displayListFactors[0] && displayListFactors[1]) {

            Renderer.renderObject3D(displayListIndex, position, rotation, material, 0);

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

            Renderer.renderObject3D(renderVertices, normals, uvs, material, Renderer.RENDER_QUADS, 0);

        }

    }

    public Particle addComponent(ParticleComponent component) {

        components.add(component);
        component.onCreation(this);

        return this;

    }

    public void addToRenderQueue() {

        Renderer.currentRenderQueue.addParticle(this);

    }

}
