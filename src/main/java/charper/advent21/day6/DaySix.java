package charper.advent21.day6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DaySix {
    // private static final int[] AGES = {3,4,3,1,2};
    private static final long[] AGES = {2,5,5,3,2,2,5,1,4,5,2,1,5,5,1,2,3,3,4,1,4,1,4,4,2,1,5,5,3,5,4,3,4,1,5,4,1,5,5,5,4,3,1,2,1,5,1,4,4,1,4,1,3,1,1,1,3,1,1,2,1,3,1,1,1,2,3,5,5,3,2,3,3,2,2,1,3,1,3,1,5,5,1,2,3,2,1,1,2,1,2,1,2,2,1,3,5,4,3,3,2,2,3,1,4,2,2,1,3,4,5,4,2,5,4,1,2,1,3,5,3,3,5,4,1,1,5,2,4,4,1,2,2,5,5,3,1,2,4,3,3,1,4,2,5,1,5,1,2,1,1,1,1,3,5,5,1,5,5,1,2,2,1,2,1,2,1,2,1,4,5,1,2,4,3,3,3,1,5,3,2,2,1,4,2,4,2,3,2,5,1,5,1,1,1,3,1,1,3,5,4,2,5,3,2,2,1,4,5,1,3,2,5,1,2,1,4,1,5,5,1,2,2,1,2,4,5,3,3,1,4,4,3,1,4,2,4,4,3,4,1,4,5,3,1,4,2,2,3,4,4,4,1,4,3,1,3,4,5,1,5,4,4,4,5,5,5,2,1,3,4,3,2,5,3,1,3,2,2,3,1,4,5,3,5,5,3,2,3,1,2,5,2,1,3,1,1,1,5,1};
    public DaySix() {
        System.out.println(spawnFish(80));
        System.out.println(partTwo());
    }

    private long partTwo() {
        List<Long> breedingCycle = populateFishArray(AGES);

        for (int i = 0; i < 256; i++) {
            breedingCycle = spawn(breedingCycle);
        }        

        return breedingCycle.stream().collect(Collectors.summingLong(Long::longValue));
    }

    private List<Long> spawn(List<Long> fish) {
        List<Long> updated = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            updated.add(0L);
        }
        for (int i = fish.size() - 1; i > 0; i--) {
            updated.set(i - 1, fish.get(i));
        }
        updated.set(8, fish.get(0));
        updated.set(6, fish.get(0)+ updated.get(6));
        return updated;
    }

    private List<Long> populateFishArray(long[] fish) {
        List<Long> breedingCycle = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            breedingCycle.add(0L);
        }
        for (long f: AGES) {
            breedingCycle.set((int)f, breedingCycle.get((int)f) + 1);
        }
        return breedingCycle;
    }

    // too slow for part 2. eep
    private long spawnFish(int numberOfDays) {
        List<Long> fishStates = Arrays.stream(AGES).boxed().collect(Collectors.toList());
        for (int i = 0; i < numberOfDays; i++) {
            fishStates = updateFish(fishStates);
        }
        return fishStates.size();
    }

    private List<Long> updateFish(List<Long> fish) {
        List<Long> updated = new ArrayList<>();
        for (Long f: fish) {
            if (f == 0) {
                updated.add(6L);
                updated.add(8L);
            } else {
                updated.add(f - 1);
            }
        }
        return updated;
    }
    
}
