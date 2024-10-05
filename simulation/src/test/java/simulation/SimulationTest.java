package simulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simulation.action.MoveAction;
import simulation.action.RenderAction;
import simulation.entity.dynamic.Herbivore;
import simulation.entity.dynamic.Predator;
import simulation.entity.statical.Grass;

import static org.junit.jupiter.api.Assertions.*;

public class SimulationTest {

    private Map map;
    private Herbivore herbivore;
    private Predator predator;
    private Simulation simulation;
    private Coordinates herbivoreCoordinates = new Coordinates(0, 0);
    private Coordinates predatorCoordinates = new Coordinates(1, 0);
    private Coordinates grassCoordinates1 = new Coordinates(2, 0);
    private Coordinates grassCoordinates2 = new Coordinates(3, 2);

    @BeforeEach
    public void setUp() {
        herbivore = new Herbivore(herbivoreCoordinates, 1, 10);
        predator = new Predator(predatorCoordinates, 1, 10, 1);
        var grass1 = new Grass(grassCoordinates1);
        var grass2 = new Grass(grassCoordinates2);

        map = new Map(3, 3);
        var entities = map.getEntities();
        entities.put(herbivoreCoordinates, herbivore);
        entities.put(predatorCoordinates, predator);
        entities.put(grassCoordinates1, grass1);
        entities.put(grassCoordinates2, grass2);
    }


    @Test
    public void nextTurn() {
        simulation = new Simulation(map);
        simulation.addTurnAction(new RenderAction(map));
        simulation.addTurnAction(new MoveAction(map));
        simulation.addTurnAction(new RenderAction(map));

        simulation.nextTurn();
        assertNotNull(map);
        assertNotNull(herbivore);
        assertNotNull(predator);

        assertNotNull(map.getEntity(predatorCoordinates));
        assertEquals(predator, map.getEntity(predatorCoordinates));
        assertEquals(herbivore, map.getEntity(new Coordinates(1, 1)));
        assertEquals(9, herbivore.getHP());

        simulation.nextTurn();
        assertNotNull(map);
        assertNotNull(herbivore);
        assertNotNull(predator);

        assertNotNull(map.getEntity(predatorCoordinates));
        assertEquals(predator, map.getEntity(predatorCoordinates));
        assertEquals(herbivore, map.getEntity(grassCoordinates1));
        assertEquals(8, herbivore.getHP());

        simulation.nextTurn();
        assertNotNull(map);
        assertNotNull(herbivore);
        assertNotNull(predator);

        assertNotNull(map.getEntity(predatorCoordinates));
        assertEquals(predator, map.getEntity(predatorCoordinates));
        assertEquals(herbivore, map.getEntity(new Coordinates(3, 1)));
        assertEquals(7, herbivore.getHP());

        simulation.nextTurn();
        assertNotNull(map);
        assertNotNull(herbivore);
        assertNotNull(predator);

        assertNull(map.getEntity(predatorCoordinates));
        assertEquals(predator, map.getEntity(new Coordinates(2, 1)));
        assertEquals(herbivore, map.getEntity(grassCoordinates2));
        assertEquals(7, herbivore.getHP());
    }

}
