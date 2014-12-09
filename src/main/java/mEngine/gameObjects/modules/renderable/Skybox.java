/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.gameObjects.modules.renderable;

import mEngine.graphics.Renderer;
import mEngine.graphics.renderable.materials.Material3D;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

public class Skybox extends ModuleRenderable3D {

    private Material3D[] materials = new Material3D[6];
    private String textureName;
    private boolean displayListsCreated = false;
    private int[] displayListIndices = new int[6];

    public Skybox(String fileName) {

        textureName = fileName;
        material = new Material3D();

    }

    public void render() {

        glDepthRange(0.9999999f, 1); // <-- 1 as near plane doesn't work, so I have to use 0.9999999f instead

        if (!displayListsCreated) {

            materials[0] = new Material3D();
            materials[0].setTextureName(textureName + "_bottom");
            materials[0].setTextureFromName();
            materials[0].type = 0;

            materials[1] = new Material3D();
            materials[1].setTextureName(textureName + "_top");
            materials[1].setTextureFromName();
            materials[1].type = 0;

            materials[2] = new Material3D();
            materials[2].setTextureName(textureName + "_back");
            materials[2].setTextureFromName();
            materials[2].type = 0;

            materials[3] = new Material3D();
            materials[3].setTextureName(textureName + "_front");
            materials[3].setTextureFromName();
            materials[3].type = 0;

            materials[4] = new Material3D();
            materials[4].setTextureName(textureName + "_left");
            materials[4].setTextureFromName();
            materials[4].type = 0;

            materials[5] = new Material3D();
            materials[5].setTextureName(textureName + "_right");
            materials[5].setTextureFromName();
            materials[5].type = 0;

            List<Vector3f> renderVertices = new ArrayList<Vector3f>();
            List<Vector3f> renderNormals = new ArrayList<Vector3f>();
            List<Vector2f> renderUVs = new ArrayList<Vector2f>();

            //bottom
            renderUVs.add(new Vector2f(0.0001f, 0.9999f));
            renderNormals.add(new Vector3f(0, 1, 0));
            renderVertices.add(new Vector3f(-1, -1, 1));
            renderUVs.add(new Vector2f(0.0001f, 0.0001f));
            renderNormals.add(new Vector3f(0, 1, 0));
            renderVertices.add(new Vector3f(1, -1, 1));
            renderUVs.add(new Vector2f(0.9999f, 0.0001f));
            renderNormals.add(new Vector3f(0, 1, 0));
            renderVertices.add(new Vector3f(1, -1, -1));
            renderUVs.add(new Vector2f(0.9999f, 0.9999f));
            renderNormals.add(new Vector3f(0, 1, 0));
            renderVertices.add(new Vector3f(-1, -1, -1));

            displayListIndices[0] = Renderer.displayListCounter;
            Renderer.addDisplayList(renderVertices, renderNormals, renderUVs, materials[0], Renderer.RENDER_QUADS);

            renderVertices = new ArrayList<Vector3f>();
            renderNormals = new ArrayList<Vector3f>();
            renderUVs = new ArrayList<Vector2f>();

            //top
            renderUVs.add(new Vector2f(0.9999f, 0.0001f));
            renderNormals.add(new Vector3f(0, -1, 0));
            renderVertices.add(new Vector3f(-1, 1, -1));
            renderUVs.add(new Vector2f(0.9999f, 0.9999f));
            renderNormals.add(new Vector3f(0, -1, 0));
            renderVertices.add(new Vector3f(1, 1, -1));
            renderUVs.add(new Vector2f(0.0001f, 0.9999f));
            renderNormals.add(new Vector3f(0, -1, 0));
            renderVertices.add(new Vector3f(1, 1, 1));
            renderUVs.add(new Vector2f(0.0001f, 0.0001f));
            renderNormals.add(new Vector3f(0, -1, 0));
            renderVertices.add(new Vector3f(-1, 1, 1));

            displayListIndices[1] = Renderer.displayListCounter;
            Renderer.addDisplayList(renderVertices, renderNormals, renderUVs, materials[1], Renderer.RENDER_QUADS);

            renderVertices = new ArrayList<Vector3f>();
            renderNormals = new ArrayList<Vector3f>();
            renderUVs = new ArrayList<Vector2f>();

            //back
            renderUVs.add(new Vector2f(0.0001f, 0.0001f));
            renderNormals.add(new Vector3f(0, 0, 1));
            renderVertices.add(new Vector3f(-1, 1, 1));
            renderUVs.add(new Vector2f(0.9999f, 0.0001f));
            renderNormals.add(new Vector3f(0, 0, 1));
            renderVertices.add(new Vector3f(1, 1, 1));
            renderUVs.add(new Vector2f(0.9999f, 0.9999f));
            renderNormals.add(new Vector3f(0, 0, 1));
            renderVertices.add(new Vector3f(1, -1, 1));
            renderUVs.add(new Vector2f(0.0001f, 0.9999f));
            renderNormals.add(new Vector3f(0, 0, 1));
            renderVertices.add(new Vector3f(-1, -1, 1));

            displayListIndices[2] = Renderer.displayListCounter;
            Renderer.addDisplayList(renderVertices, renderNormals, renderUVs, materials[2], Renderer.RENDER_QUADS);

            renderVertices = new ArrayList<Vector3f>();
            renderNormals = new ArrayList<Vector3f>();
            renderUVs = new ArrayList<Vector2f>();

            //front
            renderUVs.add(new Vector2f(0.9999f, 0.9999f));
            renderNormals.add(new Vector3f(0, 0, -1));
            renderVertices.add(new Vector3f(-1, -1, -1));
            renderUVs.add(new Vector2f(0.0001f, 0.9999f));
            renderNormals.add(new Vector3f(0, 0, -1));
            renderVertices.add(new Vector3f(1, -1, -1));
            renderUVs.add(new Vector2f(0.0001f, 0.0001f));
            renderNormals.add(new Vector3f(0, 0, -1));
            renderVertices.add(new Vector3f(1, 1, -1));
            renderUVs.add(new Vector2f(0.9999f, 0.0001f));
            renderNormals.add(new Vector3f(0, 0, -1));
            renderVertices.add(new Vector3f(-1, 1, -1));

            displayListIndices[3] = Renderer.displayListCounter;
            Renderer.addDisplayList(renderVertices, renderNormals, renderUVs, materials[3], Renderer.RENDER_QUADS);

            renderVertices = new ArrayList<Vector3f>();
            renderNormals = new ArrayList<Vector3f>();
            renderUVs = new ArrayList<Vector2f>();

            //left
            renderUVs.add(new Vector2f(0.9999f, 0.9999f));
            renderNormals.add(new Vector3f(1, 0, 0));
            renderVertices.add(new Vector3f(-1, -1, 1));
            renderUVs.add(new Vector2f(0.0001f, 0.9999f));
            renderNormals.add(new Vector3f(1, 0, 0));
            renderVertices.add(new Vector3f(-1, -1, -1));
            renderUVs.add(new Vector2f(0.0001f, 0.0001f));
            renderNormals.add(new Vector3f(1, 0, 0));
            renderVertices.add(new Vector3f(-1, 1, -1));
            renderUVs.add(new Vector2f(0.9999f, 0.0001f));
            renderNormals.add(new Vector3f(1, 0, 0));
            renderVertices.add(new Vector3f(-1, 1, 1));

            displayListIndices[4] = Renderer.displayListCounter;
            Renderer.addDisplayList(renderVertices, renderNormals, renderUVs, materials[4], Renderer.RENDER_QUADS);

            renderVertices = new ArrayList<Vector3f>();
            renderNormals = new ArrayList<Vector3f>();
            renderUVs = new ArrayList<Vector2f>();

            //right
            renderUVs.add(new Vector2f(0.9999f, 0.9999f));
            renderNormals.add(new Vector3f(-1, 0, 0));
            renderVertices.add(new Vector3f(1, -1, -1));
            renderUVs.add(new Vector2f(0.0001f, 0.9999f));
            renderNormals.add(new Vector3f(-1, 0, 0));
            renderVertices.add(new Vector3f(1, -1, 1));
            renderUVs.add(new Vector2f(0.0001f, 0.0001f));
            renderNormals.add(new Vector3f(-1, 0, 0));
            renderVertices.add(new Vector3f(1, 1, 1));
            renderUVs.add(new Vector2f(0.9999f, 0.0001f));
            renderNormals.add(new Vector3f(-1, 0, 0));
            renderVertices.add(new Vector3f(1, 1, -1));

            displayListIndices[5] = Renderer.displayListCounter;
            Renderer.addDisplayList(renderVertices, renderNormals, renderUVs, materials[5], Renderer.RENDER_QUADS);

            displayListsCreated = true;

        }

        int materialCount = 0;
        Vector3f position = parent.position;
        Vector3f rotation = parent.rotation;

        for (int displayListIndex : displayListIndices) {

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
            Renderer.renderObject3D(displayListIndex, position, rotation, materials[materialCount], 1);
            materialCount++;

        }

        glDepthRange(0, 1); // <-- Resetting the depth range

    }

    @Override
    public void onSave() {

        super.onSave();

    }

    @Override
    public void onLoad() {

        super.onLoad();

    }

    @Override
    public void addToRenderQueue() {

        Renderer.currentRenderQueue.setSkybox(this);

    }
}
