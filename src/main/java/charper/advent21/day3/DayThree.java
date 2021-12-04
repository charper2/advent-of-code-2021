package charper.advent21.day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DayThree {

    public DayThree() {
        Path path = Paths.get("src\\main\\java\\charper\\advent21\\day3\\DayThreeInput.txt");
        try {
            List<String> lines = Files.readAllLines(path);
            System.out.println(partOne(getCounts(lines), lines.size()));
            System.out.println(partTwo(lines));
        } 
        catch (IOException e) {
            System.out.println("Oops Exception");
        }
    }

    private List<Integer> getCounts(List<String> lines) {
        List<Integer> counts = new ArrayList<>();
        for (String line : lines) {
            int index = 0;
            for(char c: line.toCharArray()) {
                if (counts.size() <= index) {
                    counts.add(0);
                }
                if (c == '1') {
                    counts.set(index, counts.get(index) + 1);
                }
                index++;
            }
        }
        return counts;
    }

    private int partTwo(List<String> lines) {
        String oxyRate = getRate(lines, 0, "1", "0").get(0);
        String scrubRate = getRate(lines, 0, "0", "1").get(0);
        return Integer.valueOf(oxyRate, 2) * Integer.valueOf(scrubRate, 2);
    }

    private List<String> getRate(List<String> lines, int index, String most, String least) {
        String matchBits = getBinary(getCounts(lines), lines.size(), most, least);
        List<String> valid = new ArrayList<>();
        for (String line : lines) {
            if (line.charAt(index) == matchBits.charAt(index)) {
                valid.add(line);
            }
        }
        if (valid.size() == 1) {
            return valid;
        }
        return getRate(valid, index + 1, most, least);
    }

    private int partOne(List<Integer> counts, int totalLines) {
        int gamma = Integer.valueOf(getBinary(counts, totalLines, "1", "0"), 2);
        int epsilon = Integer.valueOf(getBinary(counts, totalLines, "0", "1"), 2);  // there is probs a nice way to do this mathematically, complement?
        return gamma * epsilon;
    }

    private String getBinary(List<Integer> counts, int totalLines, String most, String least) {
        String bin = "";
        double mostCommonTarget = Math.ceil(totalLines / 2.0);
        for (int count : counts) {
            if (count >= mostCommonTarget) {
                bin += most;
            }
            else {
                bin += least;
            }
        }
        return bin;
    }

}
