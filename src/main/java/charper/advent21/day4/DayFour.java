package charper.advent21.day4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DayFour {
    private static final String FILE_PATH = "src\\main\\java\\charper\\advent21\\day4\\DayFourInput.txt";
    private static final String MATCHED = "-";
    private static final int CARD_SIZE = 5;

    public DayFour() {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(FILE_PATH)))) {
            List<String> ballsDrawn = Stream.of(reader.readLine().split(",")).collect(Collectors.toList());
            String line = reader.readLine();  // read line here to get rid of first empty line.
            int bingoCard = 0;
            List<List<String>> allCards = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.length() == 0) {
                    bingoCard++;
                }
                if (line.length() > 0) {
                    List<String> numbers = Stream.of(line.split("\\s+")).collect(Collectors.toList());
                    allCards.add(numbers);
                }
            }
            System.out.println(playBingo(ballsDrawn, allCards));
        }
        catch (IOException e) {
            System.out.println("Oops Exception");
        }
    }

    private Integer playBingo(List<String> balls, List<List<String>> cards) {
        for (String ball: balls) {
            Integer play = searchAndMark(cards, ball);
            if (play != null) {
                return play;
            }
        }
        return null;
    }

    // return score or null
    private Integer searchAndMark(List<List<String>> cards, String ball) {
        for (int i = 0; i < cards.size(); i++) {
            for (int j = 0; j < cards.get(0).size(); j++) {
                if (cards.get(i).get(j).equals(ball)) {
                    cards.get(i).set(j, MATCHED);
                    if (checkForBingo(cards, j, i)) {
                        return calculateScore(cards, ball, j, i);
                    }
                }
            }
        }
        return null;
    }

    private int calculateScore(List<List<String>> cards, String ball, int xMatch, int yMatch) {
        int startY = yMatch - (yMatch % CARD_SIZE);
        int totalNotMatched = 0;
        for (int y = startY; y < startY + CARD_SIZE; y++) {
            for (String val : cards.get(y)) {
                if (!val.equals(MATCHED)) {
                    totalNotMatched += Integer.valueOf(val);
                }
            }
        }
        return totalNotMatched * Integer.valueOf(ball);
    }

    // assumes that the substitution has been made
    private boolean checkForBingo(List<List<String>> cards, int xMatch, int yMatch) {
        // get horizontal
        List<String> hIntersect = cards.get(yMatch);
        // get vertical
        int startY = yMatch - (yMatch % CARD_SIZE);
        List<String> vIntersect = new ArrayList<>();
        for (int i = 0; i < CARD_SIZE; i++) {
            vIntersect.add(cards.get(startY + i).get(xMatch));
        }
        // check if either is bingo
        if (checkIntersectForAllMatch(hIntersect) || checkIntersectForAllMatch(vIntersect)) {
            return true;
        }
        return false;
    }

    private boolean checkIntersectForAllMatch(List<String> intersect) {
        for (String val: intersect) {
            if (!val.equals(MATCHED)) {
                return false;
            }
        }
        return true;
    }
    
}
