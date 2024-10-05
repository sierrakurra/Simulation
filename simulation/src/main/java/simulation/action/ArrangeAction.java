package simulation.action;

import simulation.Coordinates;
import simulation.Map;
import simulation.entity.Entity;
import simulation.entity.dynamic.Herbivore;
import simulation.entity.dynamic.Predator;
import simulation.entity.statical.Grass;
import simulation.entity.statical.Rock;
import simulation.entity.statical.Tree;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Действие расстановки сущностей по карте
 */
public class ArrangeAction extends Action {

    /**
     * Количество расставляемых сущностей
     */
    private int entitiesCount = 0;

    /**
     * Типы сущностей
     */
    private List<Class<? extends Entity>> entityTypes = List.of(
            Herbivore.class,
            Predator.class,
            Grass.class,
            Rock.class,
            Tree.class
    );

    /**
     * Создает действие с дефолтным значением количества расставляемых сущностей.
     * По умолчанию количество расставляемых сущностей равно = (размер карты по Х * размер карты по Y) / 2
     * @param map карта, на которой нужно расставить сущности
     */
    public ArrangeAction(Map map) {
        super(map);
        entitiesCount = map.getSizeX() * map.getSizeY() / 2;
    }

    /**
     * Создает действие с заданным значением количества расставляемых сущностей
     * @param map карта, на которой нужно расставить сущности
     * @param entitiesCount количество сущностей, которые нужно расставить
     */
    public ArrangeAction(Map map, int entitiesCount) {
        super(map);
        if (entitiesCount >= map.getSizeX() * map.getSizeY()) {
            throw new IllegalArgumentException("entities count must be less than map size");
        }
        this.entitiesCount = entitiesCount;
    }

    @Override
    public void act() {
        HashMap<Coordinates, Entity> mapEntities = map.getEntities();
        mapEntities.clear();
        Random rand = new Random();

        for (int i = 0; i < entitiesCount; i++) {
            Coordinates newEntityCoordinates;
            do {
                newEntityCoordinates = new Coordinates(rand.nextInt(map.getSizeX()), rand.nextInt(map.getSizeY()));
            } while (mapEntities.containsKey(newEntityCoordinates));

            Entity newEntity = generateEntity(newEntityCoordinates);
            mapEntities.put(newEntityCoordinates, newEntity);
        }
    }

    private Entity generateEntity(Coordinates coordinates) {
        Random rand = new Random();
        Class<? extends Entity> newEntityType = entityTypes.get(rand.nextInt(entityTypes.size()));
        Entity generatedEntity = null;
        try {
            generatedEntity = newEntityType.getDeclaredConstructor(Coordinates.class).newInstance(coordinates);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Constructor with coordinates parameter not found for entity type " + newEntityType.getSimpleName());
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Consutract error for entity type " + newEntityType.getSimpleName());
        }

        return generatedEntity;
    }
}
