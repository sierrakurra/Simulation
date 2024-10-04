package simulation;

import simulation.render.MapRenderer;

public class App {
    public static void main(String[] args) {
        Map map = Map.getDefaultMap();
        MapRenderer.render(map);
    }
}
