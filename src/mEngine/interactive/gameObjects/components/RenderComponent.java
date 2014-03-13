package mEngine.interactive.gameObjects.components;

import mEngine.graphics.Dimension;
import mEngine.graphics.GraphicsController;
import mEngine.graphics.renderable.Model;
import mEngine.interactive.gameObjects.GameObject;

public class RenderComponent extends Component {

    String modelFileName;
    public Model model;

    public RenderComponent(String modelFileName) {

        this.modelFileName = modelFileName;

    }

    public void onCreation(GameObject obj) {

        model = new Model(modelFileName, obj.position, obj.rotation);

    }

    public void onUpdate(GameObject obj) {

        GraphicsController.switchTo3D();
        model.update(obj.position, obj.rotation);

    }

}
