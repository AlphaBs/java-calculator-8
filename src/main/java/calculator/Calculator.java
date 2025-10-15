package calculator;

import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.List;

public class Calculator {
    private final ExpressionParser parser = new ExpressionParser();

    public long calculate(String input) {
        List<Long> numbers = parse(input);
        Long sum = 0L;
        for (Long number : numbers) {
            sum += number;
        }
        return sum;
    }

    private List<Long> parse(String input) {
        StringCharacterIterator iterator = new StringCharacterIterator(input);
        List<Character> delimiters = parseDelimiters(iterator);
        return parser.parseNumbers(iterator, delimiters);
    }

    private List<Character> parseDelimiters(StringCharacterIterator iterator) {
        List<Character> delimiters = createDefaultDelimiters();
        char customDelimiter = parser.parseCustomDelimiter(iterator);
        if (customDelimiter != StringCharacterIterator.DONE) {
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
