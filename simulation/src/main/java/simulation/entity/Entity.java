package simulation.entity;

import simulation.Coordinates;

/**
 * Абстрактная сущность
 */
public abstract class Entity {

    /**
     * Координаты сущности на карте
     */
    protected Coordinates coordinates;

    public Entity(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

}
