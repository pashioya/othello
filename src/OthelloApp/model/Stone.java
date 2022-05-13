package OthelloApp.model;

public class Stone {


    private StoneColor stoneColor;

    public Stone(StoneColor stoneColor) {
        this.stoneColor = stoneColor;
    }

    public StoneColor getColor() {
        return stoneColor;
    }

    public void flip() {
        if (this.stoneColor == stoneColor.WHITE){
            this.stoneColor = stoneColor.BLACK;
        }
        else {
            this.stoneColor = stoneColor.WHITE;
        }
    }

    public boolean isPlayerColor(StoneColor stoneColor){
        if (this.stoneColor.equals(stoneColor)){
            return true;
        } else {
            return false;
        }
    }
}
