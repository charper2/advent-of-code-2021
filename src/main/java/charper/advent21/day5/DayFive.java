package charper.advent21.day5;

import static charper.advent21.Utils.Point;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DayFive {
    private static final String FILE_PATH = "src\\main\\java\\charper\\advent21\\day5\\DayFiveInput.txt";

    public DayFive() {
        System.out.println(checkVents(false));
        System.out.println(checkVents(true));
    }

    private long checkVents(boolean diagonalAllowed) {
        Map<Point, Integer> vents = new HashMap<>();
        try {
            List<String> lines = Files.readAllLines(Path.of(FILE_PATH));
            for (String line: lines) {
                String[] ends = line.split(" -> ");
                Point start = getPoint(ends[0]);
                Point end = getPoint(ends[1]);
                for (Point point: getPointsOnLine(start, end, diagonalAllowed)) {
                    if (vents.containsKey(point)) {
                        vents.put(point, vents.get(point) + 1);
                    }
                    else {
                        vents.put(point, 1);
                    }
                }
            }
        
        } catch (IOException exception) {
            System.out.println("Oops");
        }

        return vents.values().stream().filter(x -> x >= 2).count();
    }

    private Point getPoint(String str) {
        String[] coords = str.split(",");
        return new Point(Integer.valueOf(coords[0]), Integer.valueOf(coords[1]));
    }

    // this function is uggs!
    private List<Point> getPointsOnLine(Point start, Point end, boolean diagonalAllowed) {
        List<Point> points = new ArrayList<>();
        int xInc = 0;
        int yInc = 0;
        if (start.getX() == end.getX()) {
            yInc = 1;
            if (start.getY() > end.getY()) {
                 yInc = -1;
            }
        }
        else if (start.getY() == end.getY()) {
            xInc = 1;
            if (start.getX() > end.getX()) {
                xInc = -1;
            }
        }
        else {
            // diagonal line
            if (!diagonalAllowed) {
                return points;
            }
            xInc = 1;
            yInc = 1;
            if (start.getX() > end.getX()) {
                xInc = -1;
            }
            if (start.getY() > end.getY()) {
                yInc = -1;
            }
        }
        int xOffset = 0;
        int yOffset = 0;
        while ((start.getX() + xOffset != end.getX()) || (start.getY() + yOffset != end.getY())) {
            points.add(new Point(start.getX() + xOffset, start.getY() + yOffset));
            xOffset += xInc;
            yOffset += yInc;
        }
        points.add(end);
        return points;
    }
    
}
