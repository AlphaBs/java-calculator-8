package calculator;

import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.List;

public class ExpressionParser {
    public char parseCustomDelimiter(StringCharacterIterator iterator) {
        // 커스텀 구분자로 시작하지 않을 때
        int initial = iterator.getIndex();
        if (!(iterator.current() == '/' && iterator.next() == '/')) {
            iterator.setIndex(initial);
            return StringCharacterIterator.DONE;
        }

        char delimiter = iterator.next();
        if (Character.isDigit(delimiter)) {
            throw new IllegalArgumentException("커스텀 구분자로 숫자를 입력할 수 없습니다.");
        }

        if (!(iterator.next() == '\\' && iterator.next() == 'n')) {
            throw new IllegalArgumentException("커스텀 구분자가 올바르게 끝나지 않습니다.");
        }
        iterator.next();
        return delimiter;
    }

    public List<Long> parseNumbers(StringCharacterIterator iterator, List<Character> delimiters) {
        List<Long> numbers = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();

        // 빈 문자열 허용
        if (iterator.current() == StringCharacterIterator.DONE) {
            return numbers;
        }

        while (iterator.current() != StringCharacterIterator.DONE) {
            if (Character.isDigit(iterator.current())) {
                buffer.append(iterator.current());
            }
            else if (delimiters.contains(iterator.current())) {
                numbers.add(parseNumber(buffer.toString()));
                buffer.delete(0, buffer.length());
            }
            else {
                throw new IllegalArgumentException("잘못된 구분자");
            }

            iterator.next();
        }

        // 버퍼가 비어있다면 구분자로 문자열이 끝난 경우
        if (buffer.isEmpty()) {
            throw new IllegalArgumentException("구분자로 끝난 문자열");
        }

        numbers.add(parseNumber(buffer.toString()));
        return numbers;
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
