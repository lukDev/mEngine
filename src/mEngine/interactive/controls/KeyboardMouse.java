package mEngine.interactive.controls;

import mEngine.util.Input;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector2f;

public class KeyboardMouse extends Controller {

    public int[] keys = new int[Keyboard.getKeyCount()];

    public KeyboardMouse() {

        keys[0] = Keyboard.KEY_W;
        keys[1] = Keyboard.KEY_S;
        keys[2] = Keyboard.KEY_A;
        keys[3] = Keyboard.KEY_D;
        keys[4] = Keyboard.KEY_Q;
        keys[5] = Keyboard.KEY_E;
        keys[6] = Keyboard.KEY_LSHIFT;
        keys[7] = Keyboard.KEY_C;
        keys[8] = Keyboard.KEY_SPACE;


    }

    public boolean[] getInputKeys() {

        boolean[] inputKeys = new boolean[Keyboard.getKeyCount()];

        for(int count = 0; count < inputKeys.length; count++) {

            inputKeys[count] = Input.isKeyDown(keys[count]);

        }

        return inputKeys;

    }

    public Vector2f getRotation() {

        Vector2f rotation = new Vector2f();

        return rotation;

    }

    //Not needed
    public Vector3f getIntelligentMovement() { return new Vector3f(); }

}