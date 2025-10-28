package Q3;

public class SumOfDigits {

    // Recursive function to find the sum of digits in a string
    public static int sumOfDigits(String digits) {
        // Base case: if string is empty, return 0
        if (digits.length() == 0) {
            return 0;
        }

        // Take the first character, convert to integer
        int firstDigit = digits.charAt(0) - '0';

        // Recursive call for the remaining string
        return firstDigit + sumOfDigits(digits.substring(1));
    }

    public static void main(String[] args) {
        String input = "1234445123444512344451234445123444512344451234445";
        int sum = sumOfDigits(input);
        System.out.println(sum);
    }
}
