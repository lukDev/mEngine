/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.gameObjects.modules.particles.modules;

public class DeathParticleComponent extends ParticleComponent {

    float deathTimeInSeconds;
    float timeAlive = 0;
    int deathCollisionTimes;

    public DeathParticleComponent(float deathTimeInSeconds) {

        this(deathTimeInSeconds, -1);

    }

    public DeathParticleComponent(int deathCollisionTimes) {

        this(-1, deathCollisionTimes);

    }

    public DeathParticleComponent(float deathTimeInSeconds, int deathCollisionTimes) {

        this.deathTimeInSeconds = deathTimeInSeconds;
        this.deathCollisionTimes = deathCollisionTimes;

    }

    public void onUpdate() {

        if (deathTimeInSeconds != -1) {


        }

        if (deathCollisionTimes != -1) {


        }

    }

    private void kill() {

        parent.parent.modules.remove(parent);

    }

}
