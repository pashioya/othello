package domain;

public class Square {
    private Stone stone;
    private boolean hasStone;

    public Square() {
        this.stone = null;
    }

    public Square(Stone stone) {
        this.stone = stone;
    }

    public boolean hasStone() {
        if (this.stone != null){
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
}
