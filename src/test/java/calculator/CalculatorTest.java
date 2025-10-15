package calculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorTest {
    private final Calculator calc = new Calculator();

    @Test
    void empty_input_return_zero() {
        assertEquals(0L, calc.calculate(""));
    }

    @Test
    void single_number() {
        assertEquals(5L, calc.calculate("5"));
    }

    @Test
    void default_delimiter_and_two_numbers() {
        assertEquals(4L, calc.calculate("1:3"));
        assertEquals(4L, calc.calculate("1,3"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1:2:7",
            "2:2:6",
            "3,3,4",
            "2,3:5",
            "2:3,5",
            "1,2,3,4",
            "1,2,3:4",
            "1:2:3,4",
            "1:2,3:4",
            "1,2:3,4",
            "1,1,1,1,1,1,1,1,1,1"
    })
    void default_delimiter_and_multiple_numbers(String input) {
        assertEquals(10L, calc.calculate(input));
    }

    @Test
    void error_negative_single_number() {
        assertThrows(IllegalArgumentException.class, () -> calc.calculate("-10"));
    }

    @Test
    void error_negative_numbers() {
        assertThrows(IllegalArgumentException.class, () -> calc.calculate("10:-1"));
    }

    @Test
    void error_not_number() {
        assertThrows(IllegalArgumentException.class, () -> calc.calculate("101:d3"));
        assertThrows(IllegalArgumentException.class, () -> calc.calculate("101:d3:5"));
        assertThrows(IllegalArgumentException.class, () -> calc.calculate("101:3d:5"));
    }

    @Test
    void error_unexpected_delimiter() {
        assertThrows(IllegalArgumentException.class, () -> calc.calculate("101;32"));
    }

    @Test
    void error_end_of_delimiter() {
        assertThrows(IllegalArgumentException.class, () -> calc.calculate("101:3,"));
    }

    @Test
    void error_no_number_between_delimiter() {
        assertThrows(IllegalArgumentException.class, () -> calc.calculate("101:3,:3"));
    }

    @Test
    void use_custom_delimiter() {
        assertEquals(10L, calc.calculate("//;\\n1;2,3:3;1"));
    }

    @Test
    void custom_delimiter_no_expression() {
        assertEquals(0L, calc.calculate("//;\\n"));
    }

    @Test
    void minus_sign_custom_delimiter() {
        assertEquals(10L, calc.calculate("//-\\n1:2-3-3-1"));
    }

    @Test
    void error_when_custom_delimiter_is_number() {
        assertThrows(IllegalArgumentException.class, () -> calc.calculate("//3\\n1,2"));
    }

    @Test
    void allow_only_one_custom_delimiter() {
        assertThrows(IllegalArgumentException.class, () -> calc.calculate("//a\\n//b\\n1,2"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "//\\n3",
            "//ab\\n3",
            "//a\n3",
            "//a\\/3",
            "/\\n3"
    })
    void wrong_custom_delimiter_format(String input) {
        assertThrows(IllegalArgumentException.class, () -> calc.calculate(input));
    }
}
