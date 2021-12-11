package charper.advent21.day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class DayNine {
    public DayNine() {
        Path path = Paths.get("src\\main\\java\\charper\\advent21\\day9\\DayNineInput.txt");
        System.out.println(partOne(path));
        // System.out.println(partTwo(path));
    }

    private int partOne(Path path) {
        try (Stream<String> moves = Files.lines(path)) {
            List<List<Integer>> depths = moves
                .map(this::convertToInts)
                .collect(Collectors.toList());
            return sumRiskLevels(depths);
        } catch (IOException exception) {
            System.out.println("Oops");
        }
        return 1;
    }

    private List<Integer> convertToInts(String str) {
        return str.chars().mapToObj(c -> Character.getNumericValue((char)c)).collect(Collectors.toList());
    }

    // brute force
    private int sumRiskLevels(List<List<Integer>> depths) {
        int riskLevels = 0;
        int maxX = depths.get(0).size() - 1;
        int maxY = depths.size() - 1;
        for (int y = 0; y <= maxY; y++) {
            List<Integer> row = depths.get(y);
            for (int x = 0; x <= maxX; x++) {
                int checkVal = row.get(x);
                if (isValueLowPoint(depths, maxX, maxY, x, y, checkVal)) {
                    riskLevels += checkVal + 1;
                }
            }
        }
        return riskLevels;
    }

    private boolean isValueLowPoint(List<List<Integer>> depths, int maxX, int maxY, int x, int y, int checkVal) {
        for (int j = y - 1; j <= y + 1; j++) {
            for (int i = x - 1; i <= x + 1; i++) {
                if (j == y && i == x) {
                    continue;
                }
                if (j >= 0 && j <= maxY && i >= 0 && i <= maxX) {
                    if (depths.get(j).get(i) <= checkVal) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
