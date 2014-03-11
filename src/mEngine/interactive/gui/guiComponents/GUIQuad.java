package mEngine.interactive.gui.guiComponents;

import mEngine.util.TextureHelper;
import org.newdawn.slick.opengl.Texture;

import static org.lwjgl.opengl.GL11.*;

public class GUIQuad extends GUIComponent {

    protected Texture texture;

    public GUIQuad() {}

    public GUIQuad(String fileName) {

        texture = TextureHelper.getTexture(fileName);

    }

    public void onUpdate() {

        super.onUpdate();
        if(texture != null) {

            texture.bind();

            glBegin(GL_QUADS);
            glTexCoord2f(0, 1);
            glVertex2f(parent.position.x, parent.position.y + parent.size.y);
            glTexCoord2f(1, 1);
            glVertex2f(parent.position.x + parent.size.x, parent.position.y + parent.size.y);
            glTexCoord2f(1, 0);
            glVertex2f(parent.position.x + parent.size.x, parent.position.y);
            glTexCoord2f(0, 0);
            glVertex2f(parent.position.x, parent.position.y);
            glEnd();

        }
        else {

            glBegin(GL_QUADS);
            glVertex2f(parent.position.x, parent.position.y + parent.size.y);
            glVertex2f(parent.position.x + parent.size.x, parent.position.y + parent.size.y);
            glVertex2f(parent.position.x + parent.size.x, parent.position.y);
            glVertex2f(parent.position.x, parent.position.y);
            glEnd();

        }

    }

}