/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.gameObjects.modules.renderable.light;

import org.lwjgl.util.vector.Vector4f;

public class DirectionalLightSource extends LightSource {

    public float radius;

    public DirectionalLightSource(float strength) {
        this(new Vector4f(255, 255, 255, strength), -1);
    }

    public DirectionalLightSource(float strength, float radius) {
        this(new Vector4f(255, 255, 255, strength), radius);
    }

    public DirectionalLightSource(Vector4f color) {
        this(color, -1);
    }

    public DirectionalLightSource(Vector4f color, float radius) {
        super(color);
        this.radius = radius;
    }

}
