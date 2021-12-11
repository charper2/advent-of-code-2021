package charper.advent21.day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import charper.advent21.Utils.Point;


public class DayNine {
    private static final int HIGH_POINT = 9;

    public DayNine() {
        Path path = Paths.get("src\\main\\java\\charper\\advent21\\day9\\DayNineInput.txt");
        System.out.println(partOne(path));
        System.out.println(partTwo(path));
    }

    private int partTwo(Path path) {
        try (Stream<String> moves = Files.lines(path)) {
            List<List<Integer>> depths = moves
                .map(this::convertToInts)
                .collect(Collectors.toList());
            List<Point> lowest = findLowestPoints(depths);
            List<Integer> basinSizes = new ArrayList<>();
            for (Point basinStart: lowest) {
                basinSizes.add(findBasinSize(depths, new HashSet<>(), basinStart));
            }
            basinSizes.sort(Comparator.reverseOrder());
            return basinSizes.get(0) * basinSizes.get(1) * basinSizes.get(2);
        } catch (IOException exception) {
            System.out.println("Oops");
        }
        return 1;
    }

    // diagonals don't count.
    private int findBasinSize(List<List<Integer>> depths, Set<Point> visited, Point basin) {
        visited.add(basin);
        int basinSize = 1;
        int x = basin.getX();
        int y = basin.getY();
        int maxX = depths.get(0).size() - 1;
        int maxY = depths.size() - 1;
        for (Offset offset: Offset.values()) {
            int i = x + offset.xOffset;
            int j = y + offset.yOffset;
            if (j >= 0 && j <= maxY && i >= 0 && i <= maxX) {
                Point testPoint = new Point(i, j);
                if (depths.get(j).get(i) != HIGH_POINT) {
                    if (!visited.contains(testPoint)) {
                        basinSize += findBasinSize(depths, visited, testPoint);
                    }
                }
                visited.add(testPoint);
            }
        }
        return basinSize;
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

    private List<Point> findLowestPoints(List<List<Integer>> depths) {
        List<Point> lowestPoints = new ArrayList<>();
        int maxX = depths.get(0).size() - 1;
        int maxY = depths.size() - 1;
        for (int y = 0; y <= maxY; y++) {
            List<Integer> row = depths.get(y);
            for (int x = 0; x <= maxX; x++) {
                int checkVal = row.get(x);
                if (isValueLowPoint(depths, maxX, maxY, x, y, checkVal)) {
                    lowestPoints.add(new Point(x, y));
                }
            }
        }
        return lowestPoints;
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

    private enum Offset {
        ABOVE(0, -1),
        RIGHT(1, 0),
        BELOW(0, 1),
        LEFT(-1, 0);

        // naughty
        public int xOffset;
        public int yOffset;

        private Offset(int xOffset, int yOffset) {
            this.xOffset = xOffset;
            this.yOffset = yOffset;
        }
    }
}
