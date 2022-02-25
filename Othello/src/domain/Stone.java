package domain;

public class Stone {
    public enum Color{
        BLACK, WHITE;
    }

    private Color color;

    public Stone(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void flip() {
        if (this.color == Color.WHITE){
            this.color = Color.BLACK;
        }
        else {
            this.color = Color.WHITE;
        }
    }

    public boolean isPlayerColor(){
        if (this.color == Color.BLACK){
            return true;
        } else {
            return false;
        }
    }
}
