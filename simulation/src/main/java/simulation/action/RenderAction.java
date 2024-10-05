package simulation.action;

import simulation.Map;
import simulation.render.MapRenderer;

public class RenderAction extends Action {
    public RenderAction(Map map) {
        super(map);
    }

    @Override
    public void act() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        MapRenderer.render(map);
    }
}
