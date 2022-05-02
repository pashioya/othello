//import org.jeasy.rules.annotation.Action;
//import org.jeasy.rules.annotation.Condition;
//import org.jeasy.rules.annotation.Fact;
//import org.jeasy.rules.annotation.Rule;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class Test {
//
//
//    public static void main(String[] args) {
//        int[] coordinate1= {7,7};
//        System.out.println(coordinateIsCorner(coordinate1));
//        int[] coordinate2 = {1,0};
//        System.out.println(coordinateIsSide(coordinate2));
//        int[] coordinate3 = {7,3};
//        System.out.println(coordinateIsSide(coordinate3));
//        int[] coordinate4 = {5,3};
//        System.out.println(coordinateIsSide(coordinate4));
//        int[] coordinate5 = {0,0};
//        System.out.println(coordinateIsSide(coordinate5));
//        ArrayList<int[]> possibleMoves = new ArrayList<>();
//        possibleMoves.add(coordinate1);
//        possibleMoves.add(coordinate2);
//        possibleMoves.add(coordinate3);
//        possibleMoves.add(coordinate4);
//        possibleMoves.add(coordinate5);
//        ArrayList<int[]> filteredMoves = filterPossibleMoves(possibleMoves);
//        System.out.println("Printing contents");
//        for (int[] filteredMove : filteredMoves) {
//            System.out.println(filteredMove[0] + " " + filteredMove[1]);
//        }
//    }
//}
