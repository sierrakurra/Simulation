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
        setSizeX(sizeX);
        setSizeY(sizeY);
    }

    public static Map getDefaultMap() {
        final int sizeX = 10;
        final int sizeY = 10;
        Map map = new Map(sizeX, sizeY);
        map.entities.put(new Coordinates(0, 10), new Rock(new Coordinates(0, 10)));
        map.entities.put(new Coordinates(3, 10), new Grass(new Coordinates(3, 10)));
        map.entities.put(new Coordinates(5, 7), new Tree(new Coordinates(5, 7)));
        map.entities.put(new Coordinates(7, 5), new Herbivore(new Coordinates(7, 5)));
        map.entities.put(new Coordinates(8, 4), new Predator(new Coordinates(8, 4)));
        map.entities.put(new Coordinates(9, 2), new Predator(new Coordinates(9, 2)));
        map.entities.put(new Coordinates(10, 0), new Herbivore(new Coordinates(10, 0)));

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
        if (sizeX <= 0) {
            throw new IllegalArgumentException("Size X must be greater than 0");
        }
        this.sizeX = sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void setSizeY(int sizeY) {
        if (sizeY <= 0) {
            throw new IllegalArgumentException("Size Y must be greater than 0");
        }
        this.sizeY = sizeY;
    }

}
