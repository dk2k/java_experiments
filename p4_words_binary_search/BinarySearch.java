package ru.outofrange.dealex.p4words;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BinarySearch {

    static Map<Character, String> map = new HashMap<>();
    static {
        map.put('0', "");
        map.put('1', "");
        map.put('2', "abc");
        map.put('3', "def");
        map.put('4', "ghi");
        map.put('5', "jkl");
        map.put('6', "mno");
        map.put('7', "pqrs");
        map.put('8', "tuv");
        map.put('9', "wxyz");
    }

    static Map<Character, Integer> reversedMap = new HashMap<>();
    static {
        reversedMap.put('a', 2);
        reversedMap.put('b', 2);
        reversedMap.put('c', 2);
        reversedMap.put('d', 3);
        reversedMap.put('e', 3);
        reversedMap.put('f', 3);
        reversedMap.put('g', 4);
        reversedMap.put('h', 4);
        reversedMap.put('i', 4);
        reversedMap.put('j', 5);
        reversedMap.put('k', 5);
        reversedMap.put('l', 5);
        reversedMap.put('m', 6);
        reversedMap.put('n', 6);
        reversedMap.put('o', 6);
        reversedMap.put('p', 7);
        reversedMap.put('q', 7);
        reversedMap.put('r', 7);
        reversedMap.put('s', 7);
        reversedMap.put('t', 8);
        reversedMap.put('u', 8);
        reversedMap.put('v', 8);
        reversedMap.put('w', 9);
        reversedMap.put('x', 9);
        reversedMap.put('y', 9);
        reversedMap.put('z', 9);
    }

    public static void main(String[] args) throws IOException {

        // Leave these two lines alone for opening the input file
        FileReader f = new FileReader("D:\\soft\\free\\P4WORDS.TXT");
        Scanner sc = new Scanner(f);

        // List allows not to care about the actual number of the words in the file
        List<NumberWrapper> words = new LinkedList<>();

        // This loop runs through the words in the file
        while (sc.hasNext()) {
            String s = sc.nextLine();

            //System.out.println(s);
            NumberWrapper numberWrapper = new NumberWrapper(s.toCharArray());
            numberWrapper.number = evaluateNumber(numberWrapper.chars);

            words.add(numberWrapper);
            System.out.println(numberWrapper);

        }
        System.out.println("Total words " + words.size());

        Collections.sort(words);

        int sampleNumber = 9666464;

        int position = Collections.<NumberWrapper>binarySearch(words, new NumberWrapper(sampleNumber));
        System.out.println("Found position " + position);

        System.out.println("======================");

        // Moving back from the found position till we find the same number - needed
        // for the case when there are several words with the same number
        if (position >= 0) {
            System.out.println("Found:");
            while (words.get(position).number == sampleNumber) {
                System.out.println(words.get(position));
                position--;
            }
        } else {
            System.out.println("nothing found");
        }

        return;
    }


    public static int evaluateNumber(char[] chars) {
        int result = 0;
        for (char s : chars) {
            int digit = 0;

            digit = reversedMap.get(Character.toLowerCase(s));
            // Horner's formula
            result = result*10 + digit;
        }

        return result;
    }

    static class NumberWrapper implements Comparable<NumberWrapper>{
        char[] chars;
        Integer number;

        public NumberWrapper(Integer number) {
            this.number = number;
        }

        public NumberWrapper(char[] chars) {
            this.chars = chars;
        }

        public char[] getChars() {
            return chars;
        }

        public void setChars(char[] chars) {
            this.chars = chars;
        }

        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }

        @Override
        public String toString() {
            return "NumberWrapper{" +
                    "chars=" + Arrays.toString(chars) +
                    ", number=" + number +
                    '}';
        }

        @Override
        public int compareTo(NumberWrapper o) {
            return this.number.compareTo(o.number);
        }
    }
}
