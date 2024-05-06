
// Я решил подробно расписать комментарии с пояснениями к работе моего кода
// Для комфортной проверки со стороны педагогического состава Kata
// А также, с целью продемонстрировать ход моего мышления

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Main {
    // Создаю словарь для хранения эквивалентов арабским числам в виде римских
    private static final Map<Integer, String> ROMAN_NUMERALS = new HashMap<>();
    private static final int[] VALUES = {100, 90, 50, 40, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
    private static final String[] SYMBOLS = {"C", "XC", "L", "XL", "X", "IX", "VIII", "VII", "VI", "V", "IV", "III", "II", "I"};
    // Заполнение словаря римскими числами и их арабскими значениями
    static {
        for (int i = 0; i < VALUES.length; i++) {
            ROMAN_NUMERALS.put(VALUES[i], SYMBOLS[i]);
        }
    }
    // Глобальный запрос значений через консоль с вводом
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение (например, 2 + 3):");
        String input = scanner.nextLine();

        try {
            // Инциализация элементров строки в количестве трёх, операнды и оператор
            String[] parts = input.split("\\s+");
            // Проверка формата
            if (parts.length != 3) {
                throw new IllegalArgumentException("Неверный формат выражения");
            }
            // Назначения элементов из строки
            String firstOperand = parts[0];
            char operator = parts[1].charAt(0);
            String secondOperand = parts[2];
            // Проверка условия на условия соответствия элементов одному типу, либо Рим, либо Араб
            if ((isArabicNumber(firstOperand) && isArabicNumber(secondOperand)) || (isRomanNumber(firstOperand) && isRomanNumber(secondOperand))) {
                int result;
                // Выполнение вычислений в зависимости от типа операнда
                if (isArabicNumber(firstOperand) && isArabicNumber(secondOperand)) {
                    int num1 = Integer.parseInt(firstOperand);
                    int num2 = Integer.parseInt(secondOperand);
                    result = calculate(num1, num2, operator);
                } else {
                    int num1 = romanToArabic(firstOperand);
                    int num2 = romanToArabic(secondOperand);
                    result = calculate(num1, num2, operator);
                }
                // Глобальный вывод результата, в консоли
                if (isArabicNumber(firstOperand)) {
                    System.out.println("Результат: " + result);
                } else {
                    // Проверка на результат с римскими
                    if (result <= 0) {
                        throw new IllegalArgumentException("Результат работы калькулятора с римскими числами может быть только положительным");
                    }
                    System.out.println("Результат: " + arabicToRoman(result));
                }
            } else {
                throw new IllegalArgumentException("Калькулятор умеет работать только с арабскими или римскими цифрами одновременно");
            }

        } catch (NumberFormatException e) {
            System.out.println("Неверный формат чисел");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // Метод проверки арабского числа
    static boolean isArabicNumber(String input) {
        try {
            int num = Integer.parseInt(input);
            return num >= 1 && num <= 999999999;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Метод проверки римского числа
    static boolean isRomanNumber(String input) {
        return ROMAN_NUMERALS.containsValue(input);
    }
    // Метод преобразования из римского в арабские для операции
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

    // Метод ответа в числовом десятичном виде от Римского
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

    // Метод для преобразования арабского числа в римское
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

    // Метод выполнения арифметических операций с подстановкой значений
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
                // Проверка деления на ноль
                if (num2 == 0) {
                    throw new IllegalArgumentException("Ошибка: деление на ноль");
                }
                result = num1 / num2;
                break;
            default:
                throw new IllegalArgumentException("Неверный оператор");
        }
        // Возвращение результата вычислений
        return result;
    }
}
