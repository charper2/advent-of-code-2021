package charper.advent21.day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
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
    private static final Map<Character, Integer> PART_2_VALUES_MAP = new HashMap<>() {{
        put('(', 1);
        put('[', 2);
        put('{', 3);
        put('<', 4);
    }};
    private static final Set<Character> OPENING_BRACKETS = BRACKET_MAP.values().stream().collect(Collectors.toSet());

    private int partOneTotal = 0;
    private List<Long> partTwoTotals = new ArrayList<>();

    public DayTen() {
        Path path = Paths.get(FILE_PATH);
        bothParts(path);
    }

    private void bothParts(Path path) {
        try (Stream<String> lines = Files.lines(path)) {
            List<List<Character>> corrupted = lines
                .map(this::convertToChars)
                .collect(Collectors.toList());
            for (List<Character> line: corrupted) {
                findCorruptedValue(line);
            }
            System.out.println(partOneTotal);
            Collections.sort(partTwoTotals);
            int middle = (int)Math.floor(partTwoTotals.size() / 2.0);
            System.out.println(partTwoTotals.get(middle));
        } catch (IOException exception) {
            System.out.println("Oops");
            throw new RuntimeException("Should not be here!");
        }
    }

    private void findCorruptedValue(List<Character> corruptedLine) {
        Deque<Character> stack = new ArrayDeque<>();
        for (Character bracket: corruptedLine) {
            if (OPENING_BRACKETS.contains(bracket)) {
                stack.push(bracket);
            }
            else if (stack.peek() != BRACKET_MAP.get(bracket)) {
                partOneTotal += VALUES_MAP.get(bracket);
                return;
            }
            // must be a valid closure
            else {
                stack.pop();
            }
        }
        // line is not corrupted
        long partTwoTotal = 0;
        while(!stack.isEmpty()) {
            partTwoTotal = partTwoTotal * 5 + PART_2_VALUES_MAP.get(stack.pop());
        }
        partTwoTotals.add(partTwoTotal);
    }

    private List<Character> convertToChars(String str) {
        return str.chars().mapToObj(c -> (char)c).collect(Collectors.toList());
    }
    
}
