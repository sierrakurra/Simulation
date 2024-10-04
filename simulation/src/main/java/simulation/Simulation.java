package simulation;

import simulation.action.Action;

import java.util.ArrayList;
import java.util.List;

/**
 * Главный класс симуляции
 */
public class Simulation {

    /**
     * Карта симуляции
     */
    private Map map;
    /**
     * Счетчик ходов
     */
    private int movesCount = 0;
    // TODO: add Actions
    /**
     * Действия над миром перед началом симуляции
     */
    private List<Action> initActions = new ArrayList<>();
    /**
     * Действия над миром во время каждого хода симуляции
     */
    private List<Action> turnActions = new ArrayList<>();

    public Simulation(Map map) {
        this.map = map;
    }

    /**
     * Выполняет симуляцию и рендер для одного хода
     */
    public void nextTurn() {
        // TODO: realize
    }

    /**
     * Выполняет бесконечную симуляцию и рендер
     */
    public void startSimulation() {
        // TODO: realize
    }

    /**
     * Приостанавливает бесконечную симуляцию и рендер
     */
    public void pauseSimulation() {
        // TODO: realize
    }

}
