/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.graphics.gui;

import mEngine.core.events.EventController;

public class GUIScreen {

    boolean active;

    public GUIScreen(String activationEvent, String deactivationEvent) {

        EventController.addEventHandler(activationEvent, () -> active = true);
        EventController.addEventHandler(deactivationEvent, () -> active = false);

    }

    public GUIScreen(boolean active) {
        this.active = active;
    }

    public GUIScreen(String activationEvent, String deactivationEvent, boolean active) {
        this(activationEvent, deactivationEvent);
        this.active = active;
    }

}
