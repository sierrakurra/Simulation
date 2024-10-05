package simulation.entity.dynamic;

import simulation.Coordinates;
import simulation.Map;

public class Herbivore extends Creature {
    public Herbivore(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public void makeMove(Map map) {
        // TODO: движение в сторону травы или ее поглощение
    }
}
