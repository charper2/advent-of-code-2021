package charper.advent21.day4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
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
            // System.out.println(playBingo(ballsDrawn, allCards));
            System.out.println(playLoserBingo(ballsDrawn, allCards));
        }
        catch (IOException e) {
            System.out.println("Oops Exception");
        }
    }

    private Integer playLoserBingo(List<String> balls, List<List<String>> cards) {
        for (String ball: balls) {
            Set<Integer> toRemove = cardsWithBingo(cards, ball);
            if (cards.size() == CARD_SIZE && toRemove.size() > 0) {
                return calculateScore(cards, ball, 0);
            }
            // delete highest y first so we don't have to shift future y values
            for (int y : toRemove) {
                for (int i = CARD_SIZE - 1; i >= 0; i--) {
                    cards.remove(y + i);
                }
            }
            
        }
        return null;
    }

    private Set<Integer> cardsWithBingo(List<List<String>> cards, String ball) {
        Set<Integer> toRemove = new TreeSet<>(Collections.reverseOrder());
        for (int y = 0; y < cards.size(); y++) {
            for (int x = 0; x < cards.get(0).size(); x++) {
                if (cards.get(y).get(x).equals(ball)) {
                    cards.get(y).set(x, MATCHED);
                    if (checkForBingo(cards, x, y)) {
                        // delete card if bingo
                        int startY = y - (y % CARD_SIZE);
                        toRemove.add(startY);
                    }
                }
            }
        }
        return toRemove;
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
        for (int y = 0; y < cards.size(); y++) {
            for (int x = 0; x < cards.get(0).size(); x++) {
                if (cards.get(y).get(x).equals(ball)) {
                    cards.get(y).set(x, MATCHED);
                    if (checkForBingo(cards, x, y)) {
                        return calculateScore(cards, ball, y);
                    }
                }
            }
        }
        return null;
    }

    private int calculateScore(List<List<String>> cards, String ball, int yMatch) {
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
