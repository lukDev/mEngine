package mEngine.gameObjects.modules.gui;

import mEngine.core.ObjectController;
import mEngine.gameObjects.modules.gui.modules.GUIModule;
import mEngine.gameObjects.modules.renderable.ModuleRenderable;
import mEngine.graphics.Renderer;
import mEngine.graphics.renderable.materials.Material2D;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

import java.util.ArrayList;
import java.util.List;

public class GUIElement extends ModuleRenderable {

    public Material2D material;
    public List<GUIModule> components = new ArrayList<GUIModule>();
    private Vector2f position; //Values from 0 to 1
    private Vector2f size; //Values from 0 to 1
    private int guiDepartment = -1;

    public GUIElement(Vector2f posInPixels) {

        this(posInPixels, new Vector2f());

    }

    public GUIElement(Vector2f posInPixels, Vector2f sizeInPixels) {

        this(posInPixels, sizeInPixels, Color.white);

    }

    public GUIElement(Vector2f posInPixels, Vector2f sizeInPixels, String textureName) {

        material = new Material2D();
        material.setTextureName(textureName);

        //Absolute position to relative position
        position = new Vector2f(
                posInPixels.x / Display.getWidth(),
                posInPixels.y / Display.getHeight()
        );

        //Absolute size to relative size
        size = new Vector2f(
                sizeInPixels.x / Display.getWidth(),
                sizeInPixels.y / Display.getHeight()
        );

    }

    public GUIElement(Vector2f posInPixels, Vector2f sizeInPixels, Color color) {

        material = new Material2D();
        material.setColor(new Color(color));

        //Absolute size to relative size
        position = new Vector2f();
        position.x = posInPixels.x / Display.getWidth();
        position.y = posInPixels.y / Display.getHeight();

        //Absolute size to relative size
        size = new Vector2f();
        size.x = sizeInPixels.x / Display.getWidth();
        size.y = sizeInPixels.y / Display.getHeight();

    }

    public GUIElement setGUIDepartment(int department) {

        guiDepartment = department;
        return this;

    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (guiDepartment == ObjectController.activeGUIDepartment)
            for (GUIModule component : components)
                component.onUpdate();

    }

    @Override
    public void onSave() {

        super.onSave();
        for (GUIModule component : components) {

            component.onSave();

        }

        material.deleteTexture();
        material.deleteColor();

    }

    @Override
    public void onLoad() {

        super.onLoad();
        for (GUIModule component : components) {

            component.onLoad();

        }

    }

    public GUIElement addComponent(GUIModule component) {

        components.add(component);
        component.onCreation(this);
        return this;

    }

    public void render() {

        if (material.getTexture() == null && material.hasTexture()) material.setTextureFromName();

        for (GUIModule component : components) {
            component.render();
        }

    }

    @Override
    public void addToRenderQueue() {

        if (guiDepartment == ObjectController.activeGUIDepartment) Renderer.currentRenderQueue.addGUIElement(this);

    }

    public Vector2f getSize() {

        return new Vector2f(size.x * Display.getWidth(), size.y * Display.getHeight());

    }

    public void setSize(Vector2f sizeInPixels) {

        size = new Vector2f(sizeInPixels.x / Display.getWidth(), sizeInPixels.y / Display.getHeight());

    }

    public Vector2f getPosition() {

        return new Vector2f(position.x * Display.getWidth(), position.y * Display.getHeight());

    }

    public void setPosition(Vector2f positionInPixels) {

        position = new Vector2f(positionInPixels.x / Display.getWidth(), positionInPixels.y / Display.getHeight());

    }

}