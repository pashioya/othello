package OthelloApp.model;

public class Square {
    private Stone stone;

    public Square() {
        this.stone = null;
    }


    public boolean hasStone() {
        if (this.stone != null) {
            return true;
        } else {
            return false;
        }
    }

    public Stone getStone() {
        return stone;
    }

    public void setStone(StoneColor stoneColor) {
        this.stone = new Stone(stoneColor);
    }

    public void flipStone() {
        if (this.stone != null) {
            getStone().flip();
        }
    }

    public boolean hasOppositeColorStone(StoneColor stoneColor) {
        return !getStone().isPlayerColor(stoneColor);
    }

    public StoneColor getStoneColor() {
               return getStone().getColor();
    }
}
