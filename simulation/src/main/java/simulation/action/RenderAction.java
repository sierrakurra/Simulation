package simulation.action;

import simulation.Map;
import simulation.render.MapRenderer;

public class RenderAction extends Action {
    public RenderAction(Map map) {
        super(map);
    }

    @Override
    public void act() {
        MapRenderer.render(map);

        System.out.println("\n\n\n");
    }
}
