package domain;

public class Stone {


    private StoneColor stoneColor;

    public Stone(StoneColor stoneColor) {
        this.stoneColor = stoneColor;
    }

    public StoneColor getColor() {
        return stoneColor;
    }

    public void flip() {
        if (this.stoneColor == StoneColor.WHITE){
            this.stoneColor = StoneColor.BLACK;
        }
        else {
            this.stoneColor = StoneColor.WHITE;
        }
    }

    public boolean isPlayerColor(StoneColor stoneColor){
        return this.stoneColor.equals(stoneColor);
    }
}
