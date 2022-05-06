import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
    private static boolean coordinateIsNotDangerZone(int[] coordinates){
        List<int[]> dangerZoneTopLeft = List.of(new int[]{1,0}, new int[]{1,1}, new int[]{0,1});
        List<int[]> dangerZoneTopRight = List.of(new int[]{1,6}, new int[]{0,6}, new int[]{1,7});
        List<int[]> dangerZoneBottomLeft = List.of(new int[]{6,1}, new int[]{6,1}, new int[]{7,1});
        List<int[]> dangerZoneBottomRight = List.of(new int[]{7,6}, new int[]{6,7}, new int[]{6,6});
        List<List<int[]>> dangerZones = new ArrayList<List<int[]>>();
        dangerZones.add(dangerZoneTopLeft);
        dangerZones.add(dangerZoneTopRight);
        dangerZones.add(dangerZoneBottomLeft);
        dangerZones.add(dangerZoneBottomRight);
        for (List<int[]> dangerZone : dangerZones){
            for (int[] dangerousCoordinate : dangerZone){
                if (Arrays.equals(dangerousCoordinate, coordinates)){
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(coordinateIsNotDangerZone(new int[] {4,4}));

    }
}
