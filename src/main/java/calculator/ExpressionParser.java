package calculator;

import java.text.StringCharacterIterator;

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
}
