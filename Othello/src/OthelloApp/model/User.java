package OthelloApp.model;

import java.util.Scanner;

public class User extends Player{

    public User(StoneColor stoneColor) {
        super(stoneColor);
    }

    public int[] move(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please input a row number: ");
        int row = scanner.nextInt();
        System.out.print("Please input a column number: ");
        int column = scanner.nextInt();
        int[] coordinatePair = {row, column};
        return coordinatePair;
    }

}
