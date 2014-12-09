/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.util.serialization;

import mEngine.core.ObjectController;
import mEngine.gameObjects.GameObject;
import mEngine.util.resources.ResourceHelper;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

public class Serializer {

    public static boolean isSerializing = false;

    /**
     * Serializes [saves] the current scene.
     */
    public static void serialize() {

        isSerializing = true;

        SaveObject obj = new SaveObject(ObjectController.gameObjects);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = new Date();
        String saveFileName = format.format(date);
        for (int i = 0; i < ObjectController.gameObjects.size(); i++) {

            ObjectController.getGameObject(i).save();

        }

        try {
            File file = ResourceHelper.getResource(saveFileName, ResourceHelper.RES_SAVE);
            if (!file.exists()) //noinspection ResultOfMethodCallIgnored
                file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(obj);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * De-serializes [loads] a serialized scene.
     *
     * @param fileName The save file to de-serialize.
     * @param delete   Should the save file be deleted after de-serialization?
     */
    public static void deSerialize(String fileName, boolean delete) {

        SaveObject object = null;

        try {
            File file = ResourceHelper.getResource(fileName, ResourceHelper.RES_SAVE);
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            object = (SaveObject) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            if (delete) //noinspection ResultOfMethodCallIgnored
                file.delete();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        for (GameObject obj : object.gameObjects) {

            obj.load();
            ObjectController.gameObjects.add(obj);

        }

        isSerializing = false;

    }

    /**
     * De-serializes the latest save file and deletes it.
     */
    public static void deSerializeLatest() {

        File file = new File("res/saves");
        File[] saves = file.listFiles();
        Arrays.sort(saves, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                long last0 = o1.lastModified(), last1 = o2.lastModified();
                if (last0 > last1) {
                    return 1;
                } else if (last0 < last1) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        if (saves != null) deSerialize(saves[0].getName().replace(".mess", ""), true);

    }

}
