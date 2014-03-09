package mEngine.interactive.gui.guiComponents;

import mEngine.util.TextureHelper;
import mEngine.util.math.graphs.Graph;
import org.lwjgl.util.vector.Vector2f;

import static mEngine.util.math.MathHelper.clamp;
import static org.lwjgl.opengl.GL11.*;

public class GUIGraph extends GUIComponent {

    public Vector2f size;
    public Graph graph;

    public GUIGraph(Vector2f size, double[] values) {

        this.size = size;
        this.graph = new Graph(values);
        if(values.length == 0) {

            this.graph = new Graph((int)size.x);

        }

    }

    public void onUpdate() {

        super.onUpdate();
        float stepSize = size.x / graph.getLength();

        TextureHelper.getTexture("texturedStar").bind(); //Temporary fix
        glBegin(GL_LINE_STRIP);

        //For every x-value, a vertex is rendered at the appropriate spot
        for(int i = 0; i < graph.getLength(); i++) {

            /*
            StepSize: size of the jumps between x values
            y values: The point is moved to the y-position of the element.
                Then it is moved down by size.y so it is in the bottom left corner of the element.
                Finally, it is moved up again by the y value given.
            */
            glVertex2f(parent.position.x + stepSize * i, parent.position.y + size.y - (float)clamp(graph.getX(i), 0, size.y));

        }

        glEnd();

    }

    public void onExternalUpdate(Object[] args) {

        super.onExternalUpdate(args);
        for(int i = 0; i < args.length; i++) { graph.updateValue(i, (Double)args[i]); }

    }

}
