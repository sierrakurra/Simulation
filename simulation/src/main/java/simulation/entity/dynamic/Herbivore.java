package simulation.entity.dynamic;

import simulation.Coordinates;
import simulation.Map;
import simulation.entity.Entity;
import simulation.entity.statical.Grass;

import java.util.*;

/**
 * Травоядное животное
 */
public class Herbivore extends Creature {

    public Herbivore(Coordinates coordinates) {
        super(coordinates);
    }

    public Herbivore(Coordinates coordinates, int speed, double hp) {
        super(coordinates, speed, hp);
    }

    /**
     * Делает ход для движения в сторону травы или для ее поглощения
     * @param map карта
     */
    @Override
    public void makeMove(Map map) {
        HashMap<Coordinates, Entity> entities = map.getEntities();

        Coordinates resultMoveCoordinate = calculateNewCoordinate(map, Grass.class);
        entities.remove(coordinates);
        entities.put(resultMoveCoordinate, this);
        coordinates = resultMoveCoordinate;
    }

    @Override
    protected boolean isMovePossible(Coordinates coordinates, Map map) {
        if (!map.contains(coordinates)) {
            return true;
        }

        Entity entity = map.getEntity(coordinates);
        return entity instanceof Grass;
    }
}
