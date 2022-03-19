package OthelloApp.model;

import java.util.ArrayList;
import java.util.Objects;

public abstract class Player {
    private StoneColor stoneColor;


    public Player(StoneColor stoneColor) {
        this.stoneColor = stoneColor;
    }

    public StoneColor getPlayerColor() {
        return stoneColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return stoneColor == player.stoneColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(stoneColor);
    }
}
