package simulation.entity.dynamic;

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

    /**
     * Делает ход
     */
    public abstract void makeMove();

}
