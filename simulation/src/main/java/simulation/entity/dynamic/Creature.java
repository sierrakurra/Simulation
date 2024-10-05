package simulation.entity.dynamic;

import simulation.Coordinates;
import simulation.Map;
import simulation.entity.Entity;

import java.util.*;

/**
 * Живое существо
 */
public abstract class Creature extends Entity {

    private final int DEFAULT_SPEED_VALUE = 1;
    private final double DEFAULT_HP_VALUE = 10;

    /**
     * Скорость - количество проходимых клеток за 1 ход
     */
    protected int speed = DEFAULT_SPEED_VALUE;
    /**
     * Health points - состояние здоровья у существа
     */
    protected double hp = DEFAULT_HP_VALUE;

    public Creature(Coordinates coordinates) {
        super(coordinates);
    }

    public Creature(Coordinates coordinates, int speed, double hp) {
        super(coordinates);
        this.speed = speed;
        this.hp = hp;
    }

    public int getSpeed() {
        return speed;
    }

    public double getHP() {
        return hp;
    }

    /**
     * Делает ход
     */
    public abstract void makeMove(Map map);

    /**
     * Вычисляет координату
     * @param map
     * @return
     */
    protected Coordinates calculateNewCoordinate(Map map, Class<? extends Entity> targetType) {
        // получить координаты целей (травы или травоядного)
        List<Coordinates> targetCoordinates = new ArrayList<>();
        HashMap<Coordinates, Entity> entities = map.getEntities();
        entities.forEach((coordinates, entity) -> {
            if (targetType.isAssignableFrom(entity.getClass())) {
                targetCoordinates.add(coordinates);
            }
        });

        // bfs из текущих координат, при каждом движении получать возможные перемещения, проверяя их возможность у мапы и у себя
        // записываем все полученные координаты в список
        List<Coordinates> moveCoordinates = getMoveCoordinates(map);

        // из полученных координат выбираем только ту, которая ближе к цели (травоядному или траве) по количеству ходов
        int minDistanceIndex = getMinDistanceIndex(moveCoordinates, targetCoordinates);
        return moveCoordinates.get(minDistanceIndex);
    }

    /**
     * Возвращает возможные движения существа из точки с координатами from
     * @param from координаты точки откуда стартуем
     */
    private List<Coordinates> getPossibleMoves(Coordinates from) {
        List<Coordinates> possibleMoves = new ArrayList<Coordinates>();
        possibleMoves.add(new Coordinates(from.getX(), from.getY() - 1));
        possibleMoves.add(new Coordinates(from.getX() + 1, from.getY() - 1));
        possibleMoves.add(new Coordinates(from.getX() + 1, from.getY()));
        possibleMoves.add(new Coordinates(from.getX() + 1, from.getY() + 1));
        possibleMoves.add(new Coordinates(from.getX(), from.getY() + 1));
        possibleMoves.add(new Coordinates(from.getX() - 1, from.getY() + 1));
        possibleMoves.add(new Coordinates(from.getX() - 1, from.getY()));
        possibleMoves.add(new Coordinates(from.getX() - 1, from.getY() - 1));

        return possibleMoves;
    }

    /**
     * Возвращает список всевозможных координат точек, куда существо может перейти за 1 ход без учета возможности хода
     * @param map карта, на которой происходит передвижение
     */
    private List<Coordinates> getMoveCoordinates(Map map) {
        Queue<Coordinates> moveCoordinatesQueue = new LinkedList<>();
        HashMap<Coordinates, Boolean> visitedCoordinates = new HashMap<>();
        List<Coordinates> moveCoordinatesList = new ArrayList<>(List.of(coordinates));
        moveCoordinatesQueue.add(coordinates);
        int currentMoveNumber = 0;

        while (!moveCoordinatesQueue.isEmpty() && currentMoveNumber < speed) {
            Coordinates startCoordinates = moveCoordinatesQueue.remove();
            if (!visitedCoordinates.containsKey(startCoordinates) || !visitedCoordinates.get(startCoordinates)) {
                visitedCoordinates.put(startCoordinates, true);

                List<Coordinates> possibleMoveCoordinates = getPossibleMoves(startCoordinates);
                List<Coordinates> newMoveCoordinates = getNewMoveCoordinatesFromPossible(possibleMoveCoordinates, map);
                moveCoordinatesQueue.addAll(newMoveCoordinates);
                moveCoordinatesList.addAll(newMoveCoordinates);
            }

            currentMoveNumber++;
        }

        return moveCoordinatesList;
    }

    /**
     * Возвращает список координат куда может перейти существо за 1 ход с учетом всех ограничений
     * @param possibleMoveCoordinates возможные координаты точки перехода
     * @param map карта, где происходит передвижение
     */
    private List<Coordinates> getNewMoveCoordinatesFromPossible(List<Coordinates> possibleMoveCoordinates, Map map) {
        List<Coordinates> newMoveCoordinates = new ArrayList<>();

        possibleMoveCoordinates.forEach((coordinates) -> {
            if (map.isCoordinatesLegal(coordinates) && isMovePossible(coordinates, map)) {
                newMoveCoordinates.add(coordinates);
            }
        });

        return newMoveCoordinates;
    }

    /**
     * Проверяет возможен ли переход
     * @param coordinates координата точки куда ходим перейти
     * @param map карта, где происходит передвижение
     */
    protected abstract boolean isMovePossible(Coordinates coordinates, Map map);

    /**
     * Вычисляет и возвращает индекс координаты передвижения, из которой расстояние до одной из целевых координат минимально
     * @param moveCoordinates координаты передвижения
     * @param targetCoordinates целевые координаты
     * @return индекс координаты передвижения, из которой расстояние до одной из целевых координат минимально
     */
    private int getMinDistanceIndex(List<Coordinates> moveCoordinates, List<Coordinates> targetCoordinates) {
        List<Double> targetMoveDistances = new ArrayList<>();

        for (int i = 0; i < moveCoordinates.size(); i++) {
            double minDistance = Double.MAX_VALUE;
            Coordinates moveCoordinate = moveCoordinates.get(i);

            for (int j = 0; j < targetCoordinates.size(); j++) {
                Coordinates targetCoordinate = targetCoordinates.get(j);

                double xDiff = moveCoordinate.getX() - targetCoordinate.getX();
                double yDiff = moveCoordinate.getY() - targetCoordinate.getY();
                double distance = Math.sqrt(xDiff * xDiff + yDiff * yDiff);

                if (distance < minDistance) {
                    minDistance = distance;
                }
            }

            targetMoveDistances.add(minDistance);
        }

        return targetMoveDistances.indexOf(Collections.min(targetMoveDistances));
    }

}
