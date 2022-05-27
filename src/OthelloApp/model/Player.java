package OthelloApp.model;

import java.util.Objects;

public class Player {
    private StoneColor stoneColor;
    private String name;
    private String type;

    public Player(StoneColor stoneColor, String name, String type) {
        this.stoneColor = stoneColor;
        this.name = name;
        this.type = type;
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


    public boolean isComputer(){
        if (type.equals("computer")){
            return true;
        }
        return false;
    }
}
