package simulation.action;

import simulation.Map;
import simulation.render.MapRenderer;

public class RenderAction extends Action {
    public RenderAction(Map map) {
        super(map);
    }

    @Override
    public void act() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        MapRenderer.render(map);
    }
}
