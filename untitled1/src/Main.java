// Явно адаптированный код под метод public static String calc(String input)
// Ограничения на использование чисел ввода в диапозоне от 1 до 10 или от I до X
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Map<Integer, String> ROMAN_NUMERALS = new HashMap<>();
    private static final int[] VALUES = {100, 90, 50, 40, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
    private static final String[] SYMBOLS = {"C", "XC", "L", "XL", "X", "IX", "VIII", "VII", "VI", "V", "IV", "III", "II", "I"};

    static {
        for (int i = 0; i < VALUES.length; i++) {
            ROMAN_NUMERALS.put(VALUES[i], SYMBOLS[i]);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение (например, 2 + 3):");
        String input = scanner.nextLine();

        String result = calc(input);
        System.out.println("Результат: " + result);
    }

    public static String calc(String input) {
        try {
            String[] parts = input.split("\\s+");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Неверный формат выражения");
            }

            String firstOperand = parts[0];
            char operator = parts[1].charAt(0);
            String secondOperand = parts[2];

            if ((!isArabicNumber(firstOperand) && !isRomanNumber(firstOperand)) || (!isArabicNumber(secondOperand) && !isRomanNumber(secondOperand))) {
                throw new IllegalArgumentException("Числа должны быть в диапазоне от 1 до 10 или быть римскими числами от I до X");
            }

            int result;
            if (isArabicNumber(firstOperand) && isArabicNumber(secondOperand)) {
                int num1 = Integer.parseInt(firstOperand);
                int num2 = Integer.parseInt(secondOperand);
                result = calculate(num1, num2, operator);
            } else {
                int num1 = romanToArabic(firstOperand);
                int num2 = romanToArabic(secondOperand);
                result = calculate(num1, num2, operator);
            }

            if (isArabicNumber(firstOperand)) {
                return String.valueOf(result);
            } else {
                if (result <= 0) {
                    throw new IllegalArgumentException("Результат работы калькулятора с римскими числами может быть только положительным");
                }
                return arabicToRoman(result);
            }
        } catch (NumberFormatException e) {
            return "Неверный формат чисел";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    static boolean isArabicNumber(String input) {
        try {
            int num = Integer.parseInt(input);
            return num >= 1 && num <= 10;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    static boolean isRomanNumber(String input) {
        return ROMAN_NUMERALS.containsValue(input.toUpperCase());
    }

    static int romanToArabic(String roman) {
        int result = 0;
        for (int i = 0; i < roman.length(); i++) {
            if (i > 0 && charToValue(roman.charAt(i)) > charToValue(roman.charAt(i - 1))) {
                result += charToValue(roman.charAt(i)) - 2 * charToValue(roman.charAt(i - 1));
            } else {
                result += charToValue(roman.charAt(i));
            }
        }
        return result;
    }

    static int charToValue(char c) {
        switch (c) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            default:
                return -1;
        }
    }

    static String arabicToRoman(int number) {
        if (number < 1) {
            throw new IllegalArgumentException("Результат работы калькулятора с римскими числами может быть только положительным");
        }
        StringBuilder roman = new StringBuilder();
        int i = 0;
        while (number > 0) {
            while (number >= VALUES[i]) {
                roman.append(SYMBOLS[i]);
                number -= VALUES[i];
            }
            i++;
        }
        return roman.toString();
    }

    static int calculate(int num1, int num2, char operator) {
        int result;
        switch (operator) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                if (num2 == 0) {
                    throw new IllegalArgumentException("Ошибка: деление на ноль");
                }
                result = num1 / num2;
                break;
            default:
                throw new IllegalArgumentException("Неверный оператор");
        }
        return result;
    }
}
