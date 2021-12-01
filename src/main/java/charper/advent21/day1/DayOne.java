package charper.advent21.day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DayOne {

    public DayOne() {
        Path path = Paths.get("src\\main\\java\\charper\\advent21\\day1\\DayOneInput.txt");
        try {
            List<Integer> depths = Files.readAllLines(path).stream().map(x -> Integer.valueOf(x)).collect(Collectors.toList());
            System.out.println(partOne(depths));
            System.out.println(partTwo(depths));
        } 
        catch (IOException e) {
            System.out.println("Oops Exception");
        }
    }

    private Integer partOne(List<Integer> depths) {
        int numIncreasing = 0;
        int previousDepth = Integer.MAX_VALUE;
        for (Integer depth : depths) {
            if (depth > previousDepth) {
                numIncreasing++;  
            }
            previousDepth = depth;
        }
        return numIncreasing;
    }

    private Integer partTwo(List<Integer> depths) {
        int numIncreasing = 0;
        int previousSum = Integer.MAX_VALUE;
        List<Integer> window = new ArrayList<>();

        for (Integer depth : depths) {
            if (window.size() < 3) {
                window.add(depth);
                previousSum = window.stream().collect(Collectors.summingInt(Integer::intValue));
                continue;
            }

            window.remove(0);
            window.add(depth);
            int newSum = window.stream().collect(Collectors.summingInt(Integer::intValue));
            if (newSum > previousSum) {
                numIncreasing++;  
            }
            previousSum = newSum;
        }
        return numIncreasing;
    }
}