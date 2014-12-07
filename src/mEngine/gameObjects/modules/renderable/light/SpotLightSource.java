/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.gameObjects.modules.renderable.light;

import mEngine.util.math.MathHelper;
import org.lwjgl.util.vector.Vector4f;

public class SpotLightSource extends LightSource {

    public float angle;
    public float transition;

    public SpotLightSource(float strength) {
        this(new Vector4f(255, 255, 255, strength));
    }

    public SpotLightSource(Vector4f color) {
        this(color, -1, 1);
    }

    public SpotLightSource(float strength, float angle) {
        this(new Vector4f(255, 255, 255, strength), angle);
    }

    public SpotLightSource(Vector4f color, float angle) {
        this(color, angle, 0);
    }

    public SpotLightSource(Vector4f color, float angle, float transition) {

        super(color);

        if (angle == -1) this.angle = -1;
        else this.angle = (float) Math.toRadians(angle);

        this.transition = (float) MathHelper.clamp(0, 1, transition);

    }

}
