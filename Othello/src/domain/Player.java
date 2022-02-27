package domain;

import java.util.ArrayList;

public abstract class Player {
    private StoneColor stoneColor;

    public Player(StoneColor stoneColor) {
        this.stoneColor = stoneColor;
    }

    public StoneColor getPlayerColor() {
        return stoneColor;
    }
}
