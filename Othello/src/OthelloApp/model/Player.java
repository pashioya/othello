package OthelloApp.model;

import java.util.ArrayList;
import java.util.Objects;

public abstract class Player {
    private StoneColor stoneColor;
    private String name;

    public Player(StoneColor stoneColor, String name) {
        this.stoneColor = stoneColor;
        this.name = name;
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

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
