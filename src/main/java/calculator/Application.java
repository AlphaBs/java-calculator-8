package calculator;

import camp.nextstep.edu.missionutils.Console;
import java.text.StringCharacterIterator;

public class Application {
    public static void main(String[] args) {
        System.out.println("덧셈할 문자열을 입력해 주세요.");
        String input = Console.readLine();
        ExpressionParser parser = new ExpressionParser();
        StringCharacterIterator iterator = new StringCharacterIterator(input);
        char delimiter = parser.parseCustomDelimiter(iterator);
        System.out.println(delimiter);
    }
}
