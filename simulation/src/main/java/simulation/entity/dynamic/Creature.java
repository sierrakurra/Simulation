package simulation.entity.dynamic;

import simulation.Coordinates;
import simulation.Map;
import simulation.entity.Entity;

/**
 * Живое существо
 */
public abstract class Creature extends Entity {

    /**
     * Скорость - количество проходимых клеток за 1 ход
     */
    protected int speed;
    /**
     * Health points - состояние здоровья у существа
     */
    protected double hp;

    public Creature(Coordinates coordinates) {
        super(coordinates);
    }

    /**
     * Делает ход
     */
    public abstract void makeMove(Map map);

}
