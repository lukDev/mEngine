/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.util.debug;

public class RuntimeHelper {

    public static final int TOTAL_MEMORY = 0;
    public static final int USED_MEMORY = 1;
    public static final int FREE_MEMORY = 2;

    private static Runtime runtime;

    /**
     * Loads the current runtime environment
     */
    public static void initialize() {
        runtime = Runtime.getRuntime();
    }

    /**
     * Returns the desired information about the game's memory
     *
     * @param mode Use RuntimeHelper.XXX_MEMORY (total, used, free)
     * @return The desired information
     */
    public static int getMemoryStats(int mode) {

        switch (mode) {

            case TOTAL_MEMORY:
                return (int) (runtime.totalMemory() / 1024 / 1024);

            case USED_MEMORY:
                return (int) ((runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024);

            case FREE_MEMORY:
                return (int) (runtime.freeMemory() / 1024 / 1024);

        }

        return 0;

    }

}
