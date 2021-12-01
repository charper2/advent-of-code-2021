package charper.advent21;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Utils {
    
    private Utils() {}

    private static Integer[] copy(Integer[] input) {
        Integer[] copy = new Integer[input.length];
        for (int i = 0; i < input.length; i++) {
            copy[i] = input[i];
        }
        return copy;
    }

    public static void getPermutations(Integer[] input, int n, List<Integer[]> perms) {
        if (n == 1) {
            perms.add(copy(input));
            return;
        }
        for (int i = 0; i < n-1 + 1; i++) {
            Integer[] temp = input;
            getPermutations(temp, n-1, perms);
            if (n % 2 == 0) {
                Integer save = temp[i];
                temp[i] = temp[n-1];
                temp[n-1] = save;
            }
            else {
                Integer save = temp[0];
                temp[0] = temp[n-1];
                temp[n-1] = save;
            }
        }
    }

    public static Integer getNthDigit(int n, long number) {
        return (int) number / pow10(n) % 10;
    }

    private static Integer pow10(int n) {
        int val = 1;
        if (n == 0) {
            return 1;
        }
        while (n > 0) {
            val *= 10;
            n--;
        }
        return val;
    }
    
    public static List<Integer> getIntegers() {
        List<Integer> integers = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()) {
            integers.add(scanner.nextInt());
        }
        scanner.close();
        return integers;
    }

    public static List<Long> getLongs(String filePath) {
        List<Long> numbers = new ArrayList<>();
        try {
            File input = new File(filePath);
            Scanner scanner = new Scanner(input);
            while(scanner.hasNext()) {
                numbers.add(scanner.nextLong());
            }
            scanner.close();
        } catch (FileNotFoundException exception) {
            System.out.println("oops no file here...");
        }
        return numbers;
    }

    public static List<Integer> getIntegerList(String filePath) {
        List<Integer> numbers = new ArrayList<>();
        try {
            File input = new File(filePath);
            Scanner scanner = new Scanner(input);
            while(scanner.hasNext()) {
                numbers.add(scanner.nextInt());
            }
            scanner.close();
        } catch (FileNotFoundException exception) {
            System.out.println("oops no file here...");
        }
        return numbers;
    }

    public static List<String> getLinesFromFile(String filePath) {
        List<String> lines = new ArrayList<>();
        try {
            File input = new File(filePath);
            Scanner scanner = new Scanner(input);

            while(scanner.hasNext()) {
                String instruction = scanner.nextLine();
                lines.add(instruction);
            }
            scanner.close();
        } catch (FileNotFoundException exception) {
            System.out.println("oops no file here...");
        }
        return lines;
    }

    public static List<List<Character>> getCharList(String filePath) {
        List<List<Character>> lines = new ArrayList<>();
        try {
            File input = new File(filePath);
            Scanner scanner = new Scanner(input);

            while(scanner.hasNext()) {
                String str = scanner.nextLine();
                str = str.replace(" ", "");
                List<Character> chars = str.chars().mapToObj(e -> (char)e).collect(Collectors.toList());
                lines.add(chars);
            }
            scanner.close();
        } catch (FileNotFoundException exception) {
            System.out.println("oops no file here...");
        }
        return lines;
    }

    public static Map<Integer, char[]> getCharMap(String filePath) {
        Map<Integer, char[]> charMap = new HashMap<>();
        try {
            File input = new File(filePath);
            Scanner scanner = new Scanner(input);

            Integer row = 0;
            while(scanner.hasNext()) {
                String instruction = scanner.nextLine();
                char chars[] = instruction.toCharArray();
                charMap.put(row, chars);
                row++;
            }
            scanner.close();
        } catch (FileNotFoundException exception) {
            System.out.println("oops no file here...");
        }
        return charMap;
    }
}