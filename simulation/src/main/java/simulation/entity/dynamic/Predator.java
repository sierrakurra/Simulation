package simulation.entity.dynamic;

import simulation.Coordinates;
import simulation.Map;

/**
 * Хищник
 */
public class Predator extends Creature {

    /**
     * Сила атаки
     */
    protected double damage;

    public Predator(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public void makeMove(Map map) {
        /*
         * TODO: движение в сторону жертвы - травоядного или атаковать травоядное
         * Атака травоядного - уменьшение hp травоядного на силу атаки хищника
         */
    }
}
