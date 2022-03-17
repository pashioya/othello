package OthelloApp.model;

public enum StoneColor {
    BLACK("black.png"), WHITE("white.png");
    private String url;

    StoneColor(String url) {
        this.url = url;
    }
}
