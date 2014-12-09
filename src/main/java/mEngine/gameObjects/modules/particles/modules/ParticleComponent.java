/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.gameObjects.modules.particles.modules;

import mEngine.gameObjects.modules.renderable.Particle;

public abstract class ParticleComponent {

    public Particle parent;

    public void onCreation(Particle particle) {

        parent = particle;

    }

    public abstract void onUpdate();

}
