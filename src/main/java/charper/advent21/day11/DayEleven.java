package charper.advent21.day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import charper.advent21.Utils;
import charper.advent21.Utils.Point;

public class DayEleven {
    private static final String FILE_PATH = "src\\main\\java\\charper\\advent21\\day11\\DayElevenInput.txt";
    private int totalFlashes = 0;

    public DayEleven() {
        Path path = Paths.get(FILE_PATH);
        partOne(path);
        System.out.println(totalFlashes);
        // System.out.println(partTwo(path));
    }

    private void partOne(Path path) {
        try (Stream<String> lines = Files.lines(path)) {
            List<List<Integer>> octo = lines
                .map(Utils::convertToInts)
                .collect(Collectors.toList());
            for (int i = 0; i < 100; i++) {
                runStep(octo);
            }
        } catch (IOException exception) {
            System.out.println("Oops");
            throw new RuntimeException("Should not be here!");
        }
    }

    private void runStep(List<List<Integer>> octo) {
        int maxX = octo.get(0).size();
        int maxY = octo.size();
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                octo.get(y).set(x, octo.get(y).get(x) + 1);
            }
        }
        Set<Point> resolvedFlashes = checkFlashes(octo);
        totalFlashes += resolvedFlashes.size();
        setFlashedToZero(octo, resolvedFlashes);
    }

    private Set<Point> checkFlashes(List<List<Integer>> octo) {
        Set<Point> previousFlashes = new HashSet<>();
        int maxX = octo.get(0).size();
        int maxY = octo.size();
        while (true) {
            Set<Point> toResolve = new HashSet<>();
            for (int y = 0; y < maxY; y++) {
                for (int x = 0; x < maxX; x++) {
                    if (octo.get(y).get(x) > 9 && !previousFlashes.contains(new Point(x, y))) {
                        toResolve.add(new Point(x, y));
                    }
                }
            }
            resolveFlashes(octo, toResolve, previousFlashes, maxX, maxY);
            if (toResolve.size() == 0) {
                return previousFlashes;
            }
        }
    }

    private void resolveFlashes(List<List<Integer>> octo, Set<Point> toResolve, Set<Point> previousFlashes, int maxX, int maxY) {
        for (Point p: toResolve) {
            int x = p.getX();
            int y = p.getY();
            previousFlashes.add(p);
            for (int j = y - 1; j <= y + 1; j++) {
                for (int i = x - 1; i <= x + 1; i++) {
                    if (j == y && i == x) {
                        continue;
                    }
                    if (j >= 0 && j < maxY && i >= 0 && i < maxX) {
                        octo.get(j).set(i, octo.get(j).get(i) + 1);
                    }
                }
            }
        }
    }

    private void setFlashedToZero(List<List<Integer>> octo, Set<Point> previousFlashes) {
        for (Point p : previousFlashes) {
            octo.get(p.getY()).set(p.getX(), 0);
        }
    }
}
