package calculator;

import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.List;

public class Calculator {
    private static final char DONE = StringCharacterIterator.DONE;

    public long calculate(String input) {
        if (input == null) {
            throw new IllegalArgumentException("입력은 null일 수 없습니다.");
        }
        List<Long> numbers = parse(input);
        long sum = 0L;
        for (long number : numbers) {
            sum += number;
        }
        return sum;
    }

    private List<Long> parse(String input) {
        StringCharacterIterator iterator = new StringCharacterIterator(input);
        ExpressionReader reader = new ExpressionReader(iterator);
        List<Character> delimiters = parseDelimiters(reader);
        return reader.readNumbers(delimiters);
    }

    private List<Character> parseDelimiters(ExpressionReader reader) {
        List<Character> delimiters = createDefaultDelimiters();
        char customDelimiter = reader.readCustomDelimiter();
        if (customDelimiter != DONE) {
            delimiters.add(customDelimiter);
        }
        return delimiters;
    }

    private List<Character> createDefaultDelimiters() {
        List<Character> delimiters = new ArrayList<>();
        delimiters.add(':');
        delimiters.add(',');
        return delimiters;
    }
}
