package simulation.entity.dynamic;

import simulation.Coordinates;
import simulation.Map;
import simulation.entity.Entity;

import java.util.HashMap;

/**
 * Хищник
 */
public class Predator extends Creature {

    private final double DEFAULT_DAMAGE_VALUE = 1;

    /**
     * Сила атаки
     */
    protected double damage;

    public Predator(Coordinates coordinates) {
        super(coordinates);
    }

    public Predator(Coordinates coordinates, int speed, double hp, double damage) {
        super(coordinates, speed, hp);
        this.damage = damage;
    }

    /**
     * Двигается в сторону жертвы - травоядного или атакует травоядное
     * Атака травоядного - уменьшение hp травоядного на силу атаки хищника
     */
    @Override
    public void makeMove(Map map) {

        HashMap<Coordinates, Entity> entities = map.getEntities();

        Coordinates resultMoveCoordinate = calculateNewCoordinate(map, Herbivore.class);
        entities.remove(coordinates);
        if (map.contains(resultMoveCoordinate)) {
            // если по результирующим координатам перехода что-то есть, то там может быть только травоядное животное
            // поэтому атакуем травоядное животное
            Herbivore herb = (Herbivore) map.getEntity(resultMoveCoordinate);
            herb.hp = herb.hp - damage;

            if (herb.hp <= 0) {
                entities.put(resultMoveCoordinate, this);
            } else {
                // не добили, поэтому подходим к цели максимально близко
                // на ту же клетку, где цель перейти не можем, потому что не добили
                resultMoveCoordinate = newResultCoordinatesWithOffset(resultMoveCoordinate);
            }
        }
        coordinates = resultMoveCoordinate;
    }

    @Override
    protected boolean isMovePossible(Coordinates coordinates, Map map) {
        if (!map.contains(coordinates)) {
            return true;
        }

        Entity entity = map.getEntity(coordinates);
        return entity instanceof Herbivore;
    }

    private Coordinates newResultCoordinatesWithOffset(Coordinates resultCoordinates) {
        int offsetX = resultCoordinates.getX() - coordinates.getX();

        if (offsetX < 0) {
            offsetX += 1;
        } else if (offsetX > 0) {
            offsetX -= 1;
        }

        int offsetY = resultCoordinates.getY() - coordinates.getY();

        if (offsetY < 0) {
            offsetY += 1;
        } else if (offsetY > 0) {
            offsetY -= 1;
        }

        return new Coordinates(coordinates.getX() + offsetX, coordinates.getY() + offsetY);
    }
}
