package simulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simulation.entity.dynamic.Herbivore;
import simulation.entity.statical.Grass;
import simulation.entity.statical.Rock;
import simulation.entity.statical.Tree;

import static org.junit.jupiter.api.Assertions.*;

public class HerbivoreTest {

    private Map map;
    private Herbivore herbivore;
    private final Coordinates startUpCoordinates = new Coordinates(0, 0);

    @BeforeEach
    public void setUp() {
        var herbivoreCoordinates = startUpCoordinates;
        herbivore = new Herbivore(herbivoreCoordinates, 1, 10);

        map = new Map(3, 3);
        var entities = map.getEntities();
        entities.put(herbivoreCoordinates, herbivore);
    }


    @Test
    public void makeMoveWhenGrassIsAvailable() {
        var entities = map.getEntities();
        var grassCoordinates = new Coordinates(1, 1);
        entities.put(grassCoordinates, new Grass(grassCoordinates));

        herbivore.makeMove(map);
        assertNotNull(herbivore);
        assertEquals(grassCoordinates, herbivore.getCoordinates());
        assertNull(entities.get(startUpCoordinates));

        assertFalse(entities.entrySet().stream().anyMatch(e -> e.getValue() instanceof Grass));
    }

    @Test
    public void makeMoveWhenGrassIsNotAvailableInOneMove() {
        var entities = map.getEntities();
        var grassCoordinates = new Coordinates(2, 2);
        entities.put(grassCoordinates, new Grass(grassCoordinates));

        Coordinates oldCoordinates = herbivore.getCoordinates();
        herbivore.makeMove(map);
        assertNotNull(herbivore);
        assertEquals(new Coordinates(1, 1), herbivore.getCoordinates());
        assertNull(entities.get(oldCoordinates));

        oldCoordinates = herbivore.getCoordinates();
        herbivore.makeMove(map);
        assertNotNull(herbivore);
        assertEquals(grassCoordinates, herbivore.getCoordinates());
        assertNull(entities.get(oldCoordinates));

        assertFalse(entities.entrySet().stream().anyMatch(e -> e.getValue() instanceof Grass));
    }

    @Test
    public void makeMoveWhenGrassInTopOfHerbivoreAndAvailableInOneMove() {
        var entities = map.getEntities();
        var grassCoordinates = new Coordinates(0, 1);
        entities.put(grassCoordinates, new Grass(grassCoordinates));

        Coordinates oldCoordinates = herbivore.getCoordinates();
        herbivore.makeMove(map);
        assertNotNull(herbivore);
        assertEquals(new Coordinates(0, 1), herbivore.getCoordinates());
        assertNull(entities.get(oldCoordinates));

        assertFalse(entities.entrySet().stream().anyMatch(e -> e.getValue() instanceof Grass));
    }

    @Test
    public void makeMoveWhenHerbivoreIsStucked() {
        var entities = map.getEntities();
        entities.put(new Coordinates(0, 1), new Tree(new Coordinates(0, 1)));
        entities.put(new Coordinates(1, 1), new Rock(new Coordinates(1, 1)));
        entities.put(new Coordinates(1, 0), new Rock(new Coordinates(1, 0)));

        herbivore.makeMove(map);
        assertNotNull(herbivore);
        assertEquals(startUpCoordinates, herbivore.getCoordinates());
    }

    @Test
    public void makeMoveWhenGrassIsBehindRock() {
        var entities = map.getEntities();
        Coordinates grassCoordinates = new Coordinates(2, 2);
        Coordinates rockCoordinates = new Coordinates(1, 1);
        entities.put(grassCoordinates, new Grass(grassCoordinates));
        entities.put(rockCoordinates, new Rock(rockCoordinates));

        Coordinates oldCoordinates = herbivore.getCoordinates();
        herbivore.makeMove(map);
        assertNotNull(herbivore);
        assertNull(entities.get(oldCoordinates));
        assertTrue(new Coordinates(0, 1).equals(herbivore.getCoordinates()) ||
                new Coordinates(1, 0).equals(herbivore.getCoordinates()));
        assertInstanceOf(Rock.class, entities.get(rockCoordinates));
        assertInstanceOf(Grass.class, entities.get(grassCoordinates));

        oldCoordinates = herbivore.getCoordinates();
        herbivore.makeMove(map);
        assertNotNull(herbivore);
        assertNull(entities.get(oldCoordinates));
        assertTrue(new Coordinates(1, 2).equals(herbivore.getCoordinates()) ||
                new Coordinates(2, 1).equals(herbivore.getCoordinates()));
        assertInstanceOf(Rock.class, entities.get(rockCoordinates));
        assertInstanceOf(Grass.class, entities.get(grassCoordinates));

        oldCoordinates = herbivore.getCoordinates();
        herbivore.makeMove(map);
        assertNotNull(herbivore);
        assertNull(entities.get(oldCoordinates));
        assertEquals(grassCoordinates, herbivore.getCoordinates());
        assertInstanceOf(Rock.class, entities.get(rockCoordinates));
        assertEquals(herbivore, entities.get(grassCoordinates));
        assertFalse(entities.entrySet().stream().anyMatch(e -> e.getValue() instanceof Grass));
    }

}
