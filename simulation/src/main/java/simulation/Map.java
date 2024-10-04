package simulation;

import simulation.entity.Entity;
import simulation.entity.dynamic.Herbivore;
import simulation.entity.dynamic.Predator;
import simulation.entity.statical.Grass;
import simulation.entity.statical.Rock;
import simulation.entity.statical.Tree;

import java.util.HashMap;

/**
 * Карта симуляции жизни - представляет плоскость в декартовой системе координат
 */
public class Map {

    /**
     * Мапа координат на карте и расположенных на них сущностей
     */
    private HashMap<Coordinates, Entity> entities;

    /**
     * Размер карты по оси абсцисс - максимальное значение координаты по OX
     */
    private int sizeX = 0;
    /**
     * Размер карты по оси ординат - максимальное значение координаты по OY
     */
    private int sizeY = 0;

    public Map(int sizeX, int sizeY) {
        this.entities = new HashMap<>();
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public static Map getDefaultMap() {
        final int sizeX = 10;
        final int sizeY = 10;
        Map map = new Map(sizeX, sizeY);
        map.entities.put(new Coordinates(0, 10), new Rock());
        map.entities.put(new Coordinates(3, 10), new Grass());
        map.entities.put(new Coordinates(5, 7), new Tree());
        map.entities.put(new Coordinates(7, 5), new Herbivore());
        map.entities.put(new Coordinates(8, 4), new Predator());
        map.entities.put(new Coordinates(9, 2), new Predator());
        map.entities.put(new Coordinates(10, 0), new Herbivore());

        return map;
    }

    /**
     * Возвращает сущность, расположенную на карте по переданным координатам
     * @param coordinates координаты сущности
     * @return Сущность, если она имеется на карте по переданным координатам, или null в противном случае
     */
    public Entity getEntity(Coordinates coordinates) {
        return entities.get(coordinates);
    }

    public boolean contains(Coordinates coordinates) {
        return entities.containsKey(coordinates);
    }

    public HashMap<Coordinates, Entity> getEntities() {
        return entities;
    }

    public int getSizeX() {
        return sizeX;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

}
