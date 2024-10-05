package simulation;

import simulation.action.Action;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

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
    /**
     * Действия над миром перед началом симуляции
     */
    private List<Action> initActions = new ArrayList<>();
    /**
     * Действия над миром во время каждого хода симуляции
     */
    private List<Action> turnActions = new ArrayList<>();

    private AtomicBoolean isRunning = new AtomicBoolean(false);

    public Simulation(Map map) {
        this.map = map;
    }

    /**
     * Выполняет бесконечную симуляцию и рендер
     */
    public void startSimulation() {
        Thread thread = new Thread(() -> {
            isRunning.set(true);
            while (true) {
                while (isRunning.get()) {
                    nextTurn();
                }
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Приостанавливает бесконечную симуляцию и рендер
     */
    public void pauseSimulation() {
        isRunning.set(false);
    }

    /**
     * Выполняет симуляцию и рендер для одного хода
     */
    public void nextTurn() {
        initActions.forEach(Action::act);
        turnActions.forEach(Action::act);
        movesCount++;
    }

    /**
     * Добавление действия выполняемого перед стартом симуляции
     * @param action действие
     */
    public void addInitAction(Action action) {
        initActions.add(action);
    }

    /**
     * Добавление действия выполняемого каждый ход симуляции
     * @param action действие
     */
    public void addTurnAction(Action action) {
        turnActions.add(action);
    }

}
