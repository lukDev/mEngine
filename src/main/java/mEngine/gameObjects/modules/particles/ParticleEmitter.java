/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.gameObjects.modules.particles;

import mEngine.gameObjects.modules.Module;
import mEngine.gameObjects.modules.particles.modules.ParticleComponent;
import mEngine.gameObjects.modules.renderable.Particle;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class ParticleEmitter extends Module {

    public List<ParticleComponent> particleComponents = new ArrayList<ParticleComponent>();
    public Vector2f size;
    String textureName;
    int particleCount = 0;

    public ParticleEmitter(Vector2f size, String textureName) {

        this.size = size;
        this.textureName = textureName;

    }

    public ParticleEmitter addParticleComponent(ParticleComponent particleComponent) {

        particleComponents.add(particleComponent);

        return this;

    }

    public void addParticle() {

        Particle particle = new Particle(size, textureName);

        for (ParticleComponent particleComponent : particleComponents) {

            particle.addComponent(particleComponent);

        }

        parent.addModule(particle);

        particle.onCreation(parent);

        particleCount++;

    }

}
