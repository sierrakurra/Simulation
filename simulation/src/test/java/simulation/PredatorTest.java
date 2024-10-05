package simulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simulation.entity.dynamic.Herbivore;
import simulation.entity.dynamic.Predator;
import simulation.entity.statical.Grass;
import simulation.entity.statical.Rock;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PredatorTest {

    private Map map;
    private Predator predator;
    private final Coordinates startUpCoordinates = new Coordinates(0, 0);

    @BeforeEach
    public void setUp() {
        var predatorCoordinates = startUpCoordinates;
        predator = new Predator(predatorCoordinates, 1, 10, 1);

        map = new Map(3, 3);
        var entities = map.getEntities();
        entities.put(predatorCoordinates, predator);
    }

    @Test
    public void makeMoveWhenHerbivoreIsAvailable() {
        var entities = map.getEntities();
        var herbivoreCoordinates = new Coordinates(1, 1);
        entities.put(herbivoreCoordinates, new Herbivore(herbivoreCoordinates));

        predator.makeMove(map);
        assertNotNull(predator);
        assertEquals(herbivoreCoordinates, predator.getCoordinates());
        assertNull(entities.get(startUpCoordinates));

        assertFalse(entities.entrySet().stream().anyMatch(e -> e.getValue() instanceof Grass));
    }

    @Test
    public void makeMoveWhenHerbivoreIsNotAvailableInOneMove() {
        var entities = map.getEntities();
        var herbivoreCoordinates = new Coordinates(2, 2);
        entities.put(herbivoreCoordinates, new Herbivore(herbivoreCoordinates));

        Coordinates oldCoordinates = predator.getCoordinates();
        predator.makeMove(map);
        assertNotNull(predator);
        assertEquals(new Coordinates(1, 1), predator.getCoordinates());
        assertNull(entities.get(oldCoordinates));

        oldCoordinates = predator.getCoordinates();
        predator.makeMove(map);
        assertNotNull(predator);
        assertEquals(herbivoreCoordinates, predator.getCoordinates());
        assertNull(entities.get(oldCoordinates));

        assertFalse(entities.entrySet().stream().anyMatch(e -> e.getValue() instanceof Herbivore));
    }

    @Test
    public void makeMoveWhenHerbivoreInTopOfPredatorAndAvailableInOneMove() {
        var entities = map.getEntities();
        var herbivoreCoordinates = new Coordinates(0, 1);
        entities.put(herbivoreCoordinates, new Herbivore(herbivoreCoordinates));

        Coordinates oldCoordinates = predator.getCoordinates();
        predator.makeMove(map);
        assertNotNull(predator);
        assertEquals(new Coordinates(0, 1), predator.getCoordinates());
        assertNull(entities.get(oldCoordinates));

        assertFalse(entities.entrySet().stream().anyMatch(e -> e.getValue() instanceof Herbivore));
    }

    @Test
    public void makeMoveWhenPredatorIsStucked() {
        var entities = map.getEntities();
        entities.put(new Coordinates(0, 1), new Grass(new Coordinates(0, 1)));
        entities.put(new Coordinates(1, 1), new Rock(new Coordinates(1, 1)));
        entities.put(new Coordinates(1, 0), new Rock(new Coordinates(1, 0)));

        predator.makeMove(map);
        assertNotNull(predator);
        assertEquals(startUpCoordinates, predator.getCoordinates());
    }

    @Test
    public void makeMoveWhenHerbivoreIsBehindRock() {
        var entities = map.getEntities();
        Coordinates herbivoreCoordinates = new Coordinates(2, 2);
        Coordinates rockCoordinates = new Coordinates(1, 1);
        entities.put(herbivoreCoordinates, new Herbivore(herbivoreCoordinates));
        entities.put(rockCoordinates, new Rock(rockCoordinates));

        Coordinates oldCoordinates = predator.getCoordinates();
        predator.makeMove(map);
        assertNotNull(predator);
        assertNull(entities.get(oldCoordinates));
        assertTrue(new Coordinates(0, 1).equals(predator.getCoordinates()) ||
                new Coordinates(1, 0).equals(predator.getCoordinates()));
        assertInstanceOf(Rock.class, entities.get(rockCoordinates));
        assertInstanceOf(Herbivore.class, entities.get(herbivoreCoordinates));

        oldCoordinates = predator.getCoordinates();
        predator.makeMove(map);
        assertNotNull(predator);
        assertNull(entities.get(oldCoordinates));
        assertTrue(new Coordinates(1, 2).equals(predator.getCoordinates()) ||
                new Coordinates(2, 1).equals(predator.getCoordinates()));
        assertInstanceOf(Rock.class, entities.get(rockCoordinates));
        assertInstanceOf(Herbivore.class, entities.get(herbivoreCoordinates));

        oldCoordinates = predator.getCoordinates();
        predator.makeMove(map);
        assertNotNull(predator);
        assertNull(entities.get(oldCoordinates));
        assertEquals(herbivoreCoordinates, predator.getCoordinates());
        assertInstanceOf(Rock.class, entities.get(rockCoordinates));
        assertEquals(predator, entities.get(herbivoreCoordinates));
        assertFalse(entities.entrySet().stream().anyMatch(e -> e.getValue() instanceof Herbivore));
    }

}
