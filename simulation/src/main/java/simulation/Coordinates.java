package simulation;

import java.util.Objects;

/**
 * Координаты клетки карты
 */
public class Coordinates {

    /**
     * Координата клетки по оси абсцисс
     */
    private final int x;
    /**
     * Координата клетки по оси ординат
     */
    private final int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
