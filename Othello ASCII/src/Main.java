import domain.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        GameSession gameSession = new GameSession();
        while (!gameSession.isOver()) {
            gameSession.playRound();
        }
        gameSession.showBoard();
        if (gameSession.userWon()){
            System.out.println("You won!");
        } else {
            System.out.println("You lost!");
        }


    }
}
