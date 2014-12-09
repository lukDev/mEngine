/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.audio;

import paulscode.sound.Library;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.codecs.CodecWav;
import paulscode.sound.libraries.LibraryJavaSound;
import paulscode.sound.libraries.LibraryLWJGLOpenAL;

public class AudioController {

    private static SoundSystem soundSystem;

    /**
     * Sets the sound library and codecs
     */
    public static void initialize() {

        Class library;

        if (SoundSystem.libraryCompatible(LibraryLWJGLOpenAL.class))
            library = LibraryLWJGLOpenAL.class;
        else if (SoundSystem.libraryCompatible(LibraryJavaSound.class))
            library = LibraryJavaSound.class;
        else
            library = Library.class;

        try {
            soundSystem = new SoundSystem(library);
            SoundSystemConfig.setCodec("wav", CodecWav.class);
        } catch (SoundSystemException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    /**
     * This is used for interacting with configurations and audio control
     *
     * @return The current SoundSystem
     */
    public static SoundSystem getSoundSystem() {
        return soundSystem;
    }

    /**
     * This gets rid of all AL bindings, used for stopping the audio system
     */
    public static void clear() {
        soundSystem.cleanup();
    }

}
