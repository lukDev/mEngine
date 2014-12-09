/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.gameObjects.modules.audio;

import mEngine.audio.AudioController;
import mEngine.gameObjects.modules.Module;
import mEngine.util.input.Input;
import mEngine.util.input.InputEventType;
import mEngine.util.resources.ResourceHelper;
import org.lwjgl.input.Keyboard;
import paulscode.sound.SoundSystemConfig;

import java.util.Random;

public class AudioSource extends Module {

    private String sourceName;

    public AudioSource(String audioFileName, boolean isAmbient, boolean loop) {

        sourceName = audioFileName + new Random().nextInt();

        Input.assignInputEvent("play", true, InputEventType.ACTIVATED, Keyboard.KEY_1);
        Input.assignInputEvent("pause", true, InputEventType.ACTIVATED, Keyboard.KEY_2);
        Input.assignInputEvent("stop", true, InputEventType.ACTIVATED, Keyboard.KEY_3);

        if (isAmbient) //Is the sound background music or a positional sound?
            AudioController.getSoundSystem().backgroundMusic(
              sourceName,
              ResourceHelper.getResourceURL(audioFileName, ResourceHelper.RES_SOUND),
              audioFileName + ".wav",
              loop);
        else
            AudioController.getSoundSystem().newSource(
              false,
              sourceName,
              ResourceHelper.getResourceURL(audioFileName, ResourceHelper.RES_SOUND),
              audioFileName + ".wav",
              false,
              0, 0, 0, //Parent is not accessible yet
              SoundSystemConfig.ATTENUATION_ROLLOFF,
              SoundSystemConfig.getDefaultRolloff()); // <-- Long constructor there :P
    }

    public void play() {
        AudioController.getSoundSystem().play(sourceName);
    }

    public void pause() {
        AudioController.getSoundSystem().pause(sourceName);
    }

    public void stop() {
        AudioController.getSoundSystem().stop(sourceName);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        AudioController.getSoundSystem().setPosition(sourceName,
          parent.position.x,
          parent.position.y,
          parent.position.z);

        if (!AudioController.getSoundSystem().playing(sourceName) && Input.inputEventTriggered("play")) play();
        if (AudioController.getSoundSystem().playing(sourceName) && Input.inputEventTriggered("pause")) pause();
        if (AudioController.getSoundSystem().playing(sourceName) && Input.inputEventTriggered("stop")) stop();
    }
}
