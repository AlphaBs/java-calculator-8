package calculator;

import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.List;

public class ExpressionReader {
    private static final char DONE = StringCharacterIterator.DONE;
    private final StringCharacterIterator iterator;

    public ExpressionReader(StringCharacterIterator iterator) {
        this.iterator = iterator;
    }

    public char readCustomDelimiter() {
        // 커스텀 구분자로 시작하지 않을 때
        int initial = this.iterator.getIndex();
        char first = this.iterator.current();
        char second = this.iterator.next();
        if (!(first == '/' && second == '/')) {
            this.iterator.setIndex(initial);
            return DONE;
        }

        char delimiter = this.iterator.next();
        if (Character.isDigit(delimiter)) {
            throw new IllegalArgumentException("커스텀 구분자로 숫자를 입력할 수 없습니다.");
        }

        char backslash = this.iterator.next();
        char nChar = this.iterator.next();
        if (!(backslash == '\\' && nChar == 'n')) {
            throw new IllegalArgumentException("커스텀 구분자가 올바르게 끝나지 않습니다.");
        }
        this.iterator.next();
        return delimiter;
    }

    public List<Long> readNumbers(List<Character> delimiters) {
        List<Long> numbers = new ArrayList<>();
        if (this.iterator.current() == DONE) { // 빈 문자열 허용
            return numbers;
        }

        StringBuilder buffer = new StringBuilder();
        while (this.iterator.current() != DONE) {
            readNextToken(delimiters, buffer, numbers);
        }

        // 버퍼가 비어있다면 구분자로 문자열이 끝난 경우
        if (buffer.isEmpty()) {
            throw new IllegalArgumentException("구분자로 끝난 문자열");
        }

        Long last = parseNumber(buffer.toString());
        numbers.add(last);
        return numbers;
    }

    private void readNextToken(
        List<Character> delimiters,
        StringBuilder buffer,
        List<Long> numbers
    ) {
        char current = this.iterator.current();

        if (Character.isDigit(current)) {
            buffer.append(current);
            this.iterator.next();
            return;
        }

        if (delimiters.contains(current)) {
            Long parsed = parseNumber(buffer.toString());
            numbers.add(parsed);
            buffer.setLength(0);
            this.iterator.next();
            return;
        }

        throw new IllegalArgumentException("잘못된 구분자");
    }

    private Long parseNumber(String input) {
        if (input.isEmpty()) {
            throw new IllegalArgumentException("숫자가 올 자리에 빈 문자열이 있었습니다.");
        }

        try {
            return Long.parseUnsignedLong(input);
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
