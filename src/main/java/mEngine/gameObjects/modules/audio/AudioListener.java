/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.gameObjects.modules.audio;

import mEngine.audio.AudioController;
import mEngine.gameObjects.modules.Module;
import mEngine.gameObjects.modules.physics.MovementModule;

public class AudioListener extends Module {

    @Override
    public void onUpdate() {
        super.onUpdate();

        AudioController.getSoundSystem().setListenerPosition(
          parent.position.x,
          parent.position.y,
          parent.position.z);

        AudioController.getSoundSystem().setListenerOrientation(
          parent.percentRotation.x,
          parent.percentRotation.y,
          parent.percentRotation.z,
          0, 1, 0);

        MovementModule module = (MovementModule) parent.getModule(MovementModule.class);
        AudioController.getSoundSystem().setListenerVelocity(
          module.speed.x,
          module.speed.y,
          module.speed.z
        );
    }
}
