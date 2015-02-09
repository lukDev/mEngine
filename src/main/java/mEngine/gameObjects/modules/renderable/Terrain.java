/*
 * Copyright (c) 2015 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.gameObjects.modules.renderable;

import mEngine.gameObjects.GameObject;
import mEngine.graphics.renderable.materials.Material3D;
import mEngine.graphics.renderable.models.Face;
import mEngine.graphics.renderable.models.Model;
import mEngine.graphics.renderable.models.SubModel;
import mEngine.util.math.MathHelper;
import mEngine.util.math.vectors.VectorHelper;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;

public class Terrain extends RenderModule {

    private Vector3f size;
    private float[][] heightmap;
    private float resolution;

    public Terrain(float[][] heightmap, float maxHeight, float resolution) {
        super(new Model());
        this.heightmap = heightmap;
        this.resolution = (float) MathHelper.clamp(resolution, 0, 1);
        size = new Vector3f(heightmap.length, maxHeight, heightmap[0].length);
        material = new Material3D();
        material.diffuseReflectivity.set(1, 1, 1);
        material.specularReflectivity.set(0.01f, 0.01f, 0.01f);
    }

    @Override
    public void onCreation(GameObject obj) {
        super.onCreation(obj);
        generateMesh();
    }

    public void setHeight(int x, int z, float height) {
        heightmap[x][z] = (float) MathHelper.clamp(height, 0, 1);
    }

    private void generateMesh() {

        ArrayList<Vector3f> vertices = new ArrayList<>();
        ArrayList<Face> faces = new ArrayList<>();
        ArrayList<Vector3f> normals = new ArrayList<>();
        ArrayList<Vector2f> uvs = new ArrayList<>();

        for (int z = 0; z < heightmap[0].length; z++) {
            for (int x = 0; x < heightmap.length; x++) {
                vertices.add(new Vector3f(x * resolution, heightmap[x][z] * size.y, z * resolution)); //Making all vertices
                uvs.add(new Vector2f(new Vector2f((float) MathHelper.clamp((float) x / size.x, 0, 1),
                  (float) MathHelper.clamp((float) z / size.z, 0, 1))));

                //Generating faces with pattern:
                // |\--|
                // | \ |
                // |--\|
                if (x < size.x - 1 && z < size.z - 1) {
                    faces.add(new Face(
                      new Vector3f(size.x * z + x, size.x * (z + 1) + x + 1, size.x * z + x + 1),  // \---   1  3
                      new Vector3f(size.x * z + x, size.x * (z + 1) + x + 1, size.x * z + x + 1),  //  \ |
                      new Vector3f(size.x * z + x, size.x * (z + 1) + x + 1, size.x * z + x + 1)));//   \|      2

                    faces.add(new Face(
                      new Vector3f(size.x * z + x, size.x * (z + 1) + x, size.x * (z + 1) + x + 1),  // |\   1
                      new Vector3f(size.x * z + x, size.x * (z + 1) + x, size.x * (z + 1) + x + 1),  // | \
                      new Vector3f(size.x * z + x, size.x * (z + 1) + x, size.x * (z + 1) + x + 1)));// ---\ 2  3
                }
            }
        }

        for (Face face : faces) {

            Vector3f vertexA = vertices.get((int) face.vertexIndices.x);
            Vector3f vertexB = vertices.get((int) face.vertexIndices.y);
            Vector3f vertexC = vertices.get((int) face.vertexIndices.z);

            Vector3f directionVectorA = VectorHelper.subtractVectors(vertexB, vertexA);
            Vector3f directionVectorB = VectorHelper.subtractVectors(vertexC, vertexA);

            Vector3f normal = VectorHelper.getVectorProduct(directionVectorA, directionVectorB);
            normal = VectorHelper.normalizeVector(normal);

            if (VectorHelper.getScalarProduct(normal, new Vector3f(0, 1, 0)) < 0)
                normal = VectorHelper.negateVector(normal);

            normals.add(normal);
            normals.add(normal);
            normals.add(normal);

        }

        SubModel subModel = new SubModel(new Material3D());
        subModel.vertices = vertices;
        subModel.normals = normals;
        subModel.uvs = uvs;
        subModel.faces = faces;
        subModel.material = material;

        ArrayList<SubModel> subModels = new ArrayList<>();
        subModels.add(subModel);

        model = new Model(subModels);

    }

}
