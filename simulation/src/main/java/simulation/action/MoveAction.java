package simulation.action;

import simulation.Coordinates;
import simulation.Map;
import simulation.entity.Entity;
import simulation.entity.dynamic.Creature;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Действие перемещения живых существ по карте
 */
public class MoveAction extends Action {

    public MoveAction(Map map) {
        super(map);
    }

    @Override
    public void act() {
        HashMap<Coordinates, Entity> entities = map.getEntities();
        HashMap<Coordinates, Entity> movedEntities = new HashMap<>();

        Iterator<java.util.Map.Entry<Coordinates, Entity>> iterator = entities.entrySet().iterator();

        while (iterator.hasNext()) {
            java.util.Map.Entry<Coordinates, Entity> entry = iterator.next();
            Entity entity = entry.getValue();

            if (entity instanceof Creature creature) {
                creature.makeMove(makeNewMapWithChanges(map, movedEntities));
                Coordinates newCreatureCoordinates = creature.getCoordinates();

                iterator.remove();
                movedEntities.put(newCreatureCoordinates, entity);
            }

        }

        updateMapEntities(map, movedEntities);
    }

    private Map makeNewMapWithChanges(Map map, HashMap<Coordinates, Entity> changes) {
        Map newMap = new Map(map.getSizeX(), map.getSizeY());

        HashMap<Coordinates, Entity> newMapEntities = newMap.getEntities();
        HashMap<Coordinates, Entity> oldMapEntities = map.getEntities();
        newMapEntities.clear();
        newMapEntities.putAll(oldMapEntities);
        newMapEntities.putAll(changes);

        return newMap;
    }

    private void updateMapEntities(Map map, HashMap<Coordinates, Entity> movedEntities) {
        HashMap<Coordinates, Entity> mapEntities = map.getEntities();
        mapEntities.putAll(movedEntities);
    }
}
