package simulation;

import simulation.action.Action;
import simulation.action.ArrangeAction;
import simulation.action.MoveAction;
import simulation.action.RenderAction;
import simulation.render.MapRenderer;

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
        initActions.add(new ArrangeAction(map));
        turnActions.add(new RenderAction(map));
        turnActions.add(new MoveAction(map));
        turnActions.add(new RenderAction(map));
    }

    /**
     * Выполняет бесконечную симуляцию и рендер
     */
    public void startSimulation() {
        initActions.forEach(Action::act);
        turnActions.forEach(Action::act);
    }

    /**
     * Приостанавливает бесконечную симуляцию и рендер
     */
    public void pauseSimulation() {
        // TODO: realize
    }

    /**
     * Выполняет симуляцию и рендер для одного хода
     */
    private void nextTurn() {
        // TODO: realize
    }

}
