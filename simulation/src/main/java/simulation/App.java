package simulation;

import simulation.action.ArrangeAction;
import simulation.action.MoveAction;
import simulation.action.RenderAction;

public class App {
    public static void main(String[] args) {
        Map map = Map.getDefaultMap();
        Simulation sim = new Simulation(map);
        sim.addInitAction(new ArrangeAction(map));
        sim.addTurnAction(new RenderAction(map));
        sim.addTurnAction(new MoveAction(map));
        sim.addTurnAction(new RenderAction(map));
        sim.startSimulation();
    }
}
