/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.util.serialization;

import mEngine.gameObjects.GameObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SaveObject implements Serializable {

    List<GameObject> gameObjects = new ArrayList<GameObject>();

    public SaveObject(List<GameObject> objects) {

        gameObjects = objects;

    }

}
