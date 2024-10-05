package simulation.action;

import simulation.Map;

/**
 * Действие, совершаемое над миром
 * Реализация паттерна "Команда"
 */
public abstract class Action {

    /**
     * Карта мира
     */
    protected Map map;

    public Action(Map map) {
        this.map = map;
    }

    /**
     * Реализация действия
     */
    public abstract void act();

}
