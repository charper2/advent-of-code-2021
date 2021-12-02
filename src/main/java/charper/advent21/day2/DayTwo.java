package charper.advent21.day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class DayTwo {
    private int depth = 0;
    private int horizontal = 0;
    private int aim = 0;

    public DayTwo() {
        Path path = Paths.get("src\\main\\java\\charper\\advent21\\day2\\DayTwoInput.txt");
        System.out.println(partOne(path));
        System.out.println(partTwo(path));
    }

    private int partOne(Path path) {
        resetLocation();
        try (Stream<String> moves = Files.lines(path)) {
            moves.forEach(move -> updateLocation(move));
        } catch (IOException exception) {
            System.out.println("Oops");
        }
        return getDepth() * getHorizontal();
    }

    private int partTwo(Path path) {
        resetLocation();
        try (Stream<String> moves = Files.lines(path)) {
            moves.forEach(move -> updateLocationPartTwo(move));
        } catch (IOException exception) {
            System.out.println("Oops");
        }
        return getDepth() * getHorizontal();
    }

    private void resetLocation() {
        setDepth(0);
        setHorizontal(0);
        setAim(0);
    }

    private void updateLocationPartTwo(String move) {
        String[] parts = move.split("\\s+");
        String direction = parts[0];
        int size = Integer.valueOf(parts[1]);
        switch(direction) {
            case "forward": 
                setHorizontal(getHorizontal() + size);
                setDepth(getDepth() + getAim() * size);
                break;
            case "up":
                setAim(getAim() - size);
                break;
            case "down":
                setAim(getAim() + size);
                break;
            default:
                return;
        }
    }


    private void updateLocation(String move) {
        String[] parts = move.split("\\s+");
        String direction = parts[0];
        int size = Integer.valueOf(parts[1]);
        switch(direction) {
            case "forward": 
                setHorizontal(getHorizontal() + size);
                break;
            case "up":
                setDepth(getDepth() - size);
                break;
            case "down":
                setDepth(getDepth() + size);
                break;
            default:
                return;
        }
    }

    private int getHorizontal() {
        return horizontal;
    }

    private int getDepth() {
        return depth;
    }

    private int getAim() {
        return aim;
    }

    private void setHorizontal(int horizontal) {
        this.horizontal = horizontal;
    }

    private void setDepth(int depth) {
        this.depth = depth;
    }

    private void setAim(int aim) {
        this.aim = aim;
    }
}