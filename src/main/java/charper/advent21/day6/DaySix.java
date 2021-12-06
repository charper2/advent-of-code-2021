package charper.advent21.day6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DaySix {
    // private static final int[] AGES = {3,4,3,1,2};
    private static final int[] AGES = {2,5,5,3,2,2,5,1,4,5,2,1,5,5,1,2,3,3,4,1,4,1,4,4,2,1,5,5,3,5,4,3,4,1,5,4,1,5,5,5,4,3,1,2,1,5,1,4,4,1,4,1,3,1,1,1,3,1,1,2,1,3,1,1,1,2,3,5,5,3,2,3,3,2,2,1,3,1,3,1,5,5,1,2,3,2,1,1,2,1,2,1,2,2,1,3,5,4,3,3,2,2,3,1,4,2,2,1,3,4,5,4,2,5,4,1,2,1,3,5,3,3,5,4,1,1,5,2,4,4,1,2,2,5,5,3,1,2,4,3,3,1,4,2,5,1,5,1,2,1,1,1,1,3,5,5,1,5,5,1,2,2,1,2,1,2,1,2,1,4,5,1,2,4,3,3,3,1,5,3,2,2,1,4,2,4,2,3,2,5,1,5,1,1,1,3,1,1,3,5,4,2,5,3,2,2,1,4,5,1,3,2,5,1,2,1,4,1,5,5,1,2,2,1,2,4,5,3,3,1,4,4,3,1,4,2,4,4,3,4,1,4,5,3,1,4,2,2,3,4,4,4,1,4,3,1,3,4,5,1,5,4,4,4,5,5,5,2,1,3,4,3,2,5,3,1,3,2,2,3,1,4,5,3,5,5,3,2,3,1,2,5,2,1,3,1,1,1,5,1};
    public DaySix() {
        System.out.println(spawnFish(80));
        System.out.println(spawnFish(256));
    }

    private long spawnFish(int numberOfDays) {
        List<Integer> fishStates = Arrays.stream(AGES).boxed().collect(Collectors.toList());
        for (int i = 0; i < numberOfDays; i++) {
            fishStates = updateFish(fishStates);
        }
        return fishStates.size();
    }

    private List<Integer> updateFish(List<Integer> fish) {
        List<Integer> updated = new ArrayList<>();
        for (Integer f: fish) {
            if (f == 0) {
                updated.add(6);
                updated.add(8);
            } else {
                updated.add(f - 1);
            }
        }
        return updated;
    }
    
}
