package charper.advent21.day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DayTen {
    private static final String FILE_PATH = "src\\main\\java\\charper\\advent21\\day10\\DayTenInput.txt";
    private static final Map<Character, Character> BRACKET_MAP = new HashMap<>() {{
        put(')', '(');
        put(']', '[');
        put('}', '{');
        put('>', '<');
    }};
    private static final Map<Character, Integer> VALUES_MAP = new HashMap<>() {{
        put(')', 3);
        put(']', 57);
        put('}', 1197);
        put('>', 25137);
    }};
    private static final Set<Character> CLOSING_BRACKETS = BRACKET_MAP.keySet();
    private static final Set<Character> OPENING_BRACKETS = BRACKET_MAP.values().stream().collect(Collectors.toSet());


    public DayTen() {
        Path path = Paths.get(FILE_PATH);
        System.out.println(partOne(path));
        System.out.println(partTwo(path));
    }

    private int partTwo(Path path) {
        return 1;
    }

    private int partOne(Path path) {
        try (Stream<String> lines = Files.lines(path)) {
            List<List<Character>> corrupted = lines
                .map(this::convertToChars)
                .collect(Collectors.toList());
            int total = 0;
            for (List<Character> line: corrupted) {
                total += findCorruptedValue(line);
            }
            return total;
        } catch (IOException exception) {
            System.out.println("Oops");
            throw new RuntimeException("Should not be here!");
        }
    }

    private int findCorruptedValue(List<Character> corruptedLine) {
        Deque<Character> stack = new ArrayDeque<>();
        for (Character bracket: corruptedLine) {
            if (OPENING_BRACKETS.contains(bracket)) {
                stack.push(bracket);
            }
            else if (stack.peek() != BRACKET_MAP.get(bracket)) {
                return VALUES_MAP.get(bracket);
            }
            // must be a valid closure
            else {
                stack.pop();
            }
        }
        return 0;
    }

    private List<Character> convertToChars(String str) {
        return str.chars().mapToObj(c -> (char)c).collect(Collectors.toList());
    }
    
}
