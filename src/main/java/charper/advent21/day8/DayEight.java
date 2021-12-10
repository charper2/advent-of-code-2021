package charper.advent21.day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DayEight {
    private static final String FILE_PATH = "src\\main\\java\\charper\\advent21\\day8\\DayEightInput.txt";
    // segments
    //    0
    //  3   1
    //    2
    //  6   4
    //    5
    private static final Map<Integer, List<Integer>> SEGMENT_MAP = new HashMap<>() {{
        put(0, Arrays.asList(1, 2, 3, 4, 5, 6));
        put(1, Arrays.asList(1, 4));
        put(2, Arrays.asList(0, 1, 2, 5, 6));
        put(3, Arrays.asList(0, 1, 2, 4, 5));
        put(4, Arrays.asList(1, 2, 3, 4));
        put(5, Arrays.asList(0, 2, 3, 4, 5));
        put(6, Arrays.asList(2, 3, 4, 5, 6));
        put(7, Arrays.asList(0, 1, 4));
        put(8, Arrays.asList(0, 1, 2, 3, 4, 5, 6));
        put(9, Arrays.asList(0, 1, 2, 3, 4));
    }};
    // faster than doing a power of 10
    private static final int[] MULTIPLIERS = { 1000, 100, 10, 1};

    public DayEight() {
        List<Integer> uniqueSizes = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Path.of(FILE_PATH));
            String firstLine = lines.get(0);
            int firstPipeIndex = firstLine.indexOf("|");
            String[] vals = firstLine.substring(0, firstPipeIndex).trim().split(" ");
            uniqueSizes = findUnique(vals);
            System.out.println(partOne(uniqueSizes, lines));
            System.out.println(partTwo(lines));

        } catch (IOException exception) {
            System.out.println("Oops");
        }
    }

    private long partTwo(List<String> lines) {
        long total = 0L;

        for (String line: lines){
            Map<Integer, String> segmentCharMap = new HashMap<>();
            int pipeIndex = line.indexOf("|");
            String[] vals = line.substring(0, pipeIndex).trim().split(" ");
            String[] outputs = line.substring(pipeIndex + 1, line.length()).trim().split(" ");
    
            Map<Integer, List<String>> valMap = new HashMap<>();
            for(String val: vals) {
                int len = val.length();
                if (!valMap.containsKey(len)) {
                    valMap.put(len, Arrays.asList(val));
                }
                else {
                    List<String> current = new ArrayList<>(valMap.get(len));
                    current.add(val);
                    valMap.put(len, current);
                }
            }
    
            // map known lengths
            segmentCharMap.put(1, valMap.get(2).get(0));
            segmentCharMap.put(7, valMap.get(3).get(0));
            segmentCharMap.put(4, valMap.get(4).get(0));
            segmentCharMap.put(8, valMap.get(7).get(0));
    
            // deduce the rest
            Set<Character> fourAndSevenChars = combine(segmentCharMap.get(4), segmentCharMap.get(7));
            deduceSegment(segmentCharMap, 9, fourAndSevenChars, valMap.get(6));
    
            Set<Character> sevenChars = getCharsInString(segmentCharMap.get(7));
            deduceSegment(segmentCharMap, 0, sevenChars, valMap.get(6));
    
            segmentCharMap.put(6, valMap.get(6).get(0));
    
            Set<Character> oneChars = getCharsInString(segmentCharMap.get(1));
            deduceSegment(segmentCharMap, 3, oneChars, valMap.get(5));
    
            deduceInverseSegment(segmentCharMap, 5, segmentCharMap.get(9), valMap.get(5));
    
            segmentCharMap.put(2, valMap.get(5).get(0));
    
            Map<String, Integer> invertedMap = segmentCharMap.entrySet().stream().collect(
                Collectors.toMap(e -> sortChars(e.getValue()), Map.Entry::getKey));

            total += getOutputValue(invertedMap, outputs);
        }

        return total;
    }

    private String sortChars(String str) {
        char[] temp = str.toCharArray();
        Arrays.sort(temp);
        return new String(temp);
    }

    private int getOutputValue(Map<String, Integer> map, String[] outputs) {
        int outputVal = 0;
        for (int i = 0; i < outputs.length; i++) {
            outputVal += map.get(sortChars(outputs[i])) * MULTIPLIERS[i];
        }
        return outputVal;
    }

    private void deduceSegment(Map<Integer, String> segmentCharMap, int valToFind, Set<Character> charsToMatch, List<String> possibleMatches) {
        for (int i = 0; i < possibleMatches.size(); i++) {
            String fiveStr = possibleMatches.get(i);
            if (containsAll(charsToMatch, fiveStr)) {
                segmentCharMap.put(valToFind, fiveStr);
                possibleMatches.remove(i);
                break;
            }
        }
    }

    private void deduceInverseSegment(Map<Integer, String> segmentCharMap, int valToFind, String strToMatch, List<String> possibleMatches) {
        for (int i = 0; i < possibleMatches.size(); i++) {
            String fiveStr = possibleMatches.get(i);
            if (containsAll(getCharsInString(fiveStr), strToMatch)) {
                segmentCharMap.put(valToFind, fiveStr);
                possibleMatches.remove(i);
                break;
            }
        }
    }

    // return true is str contains at least all characters in chars. note it may contain extras
    private boolean containsAll(Set<Character> chars, String str) {
        Set<Character> strChars = getCharsInString(str);
        for (char c: chars) {
            if (!strChars.contains(c)) {
                return false;
            }
        }
        return true;
    }

    private Set<Character> getCharsInString(String str) {
        return str.chars().mapToObj(c -> (char)c).collect(Collectors.toSet());
    }

    private Set<Character> combine(String a, String b) {
        Set<Character> aChars = getCharsInString(a);
        Set<Character> bChars = getCharsInString(b);
        aChars.addAll(bChars);
        return aChars;
    }

    private int partOne(List<Integer> uniques, List<String> lines) {
        int total = 0;
        for (String line: lines) {
            int pipeIndex = line.indexOf("|");
            
            String[] outputs = line.substring(pipeIndex + 1, line.length()).trim().split(" ");
            for (String output: outputs) {
                if (uniques.contains(output.length())) {
                    total +=1;
                }
            }
        }
        return total;
    }

    private List<Integer> findUnique(String[] vals) {
        List<Integer> uniques = new ArrayList<>();
        Map<Integer, Integer> valMap = new HashMap<>();
        for(String val: vals) {
            int len = val.length();
            if (!valMap.containsKey(len)) {
                valMap.put(len, 1);
            }
            else {
                valMap.put(len, valMap.get(len) + 1);
            }
        }
        for (Map.Entry<Integer, Integer> entry: valMap.entrySet()) {
            if (entry.getValue() == 1) {
                uniques.add(entry.getKey());
            }
        }
        return uniques;
    }
    
}
