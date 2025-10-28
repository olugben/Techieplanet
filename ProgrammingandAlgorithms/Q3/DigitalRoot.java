package Q3;
public class DigitalRoot {

    // Recursive function to find sum of digits in a string
    public static int sumOfDigits(String digits) {
        if (digits.length() == 0) {
            return 0;
        }
        int firstDigit = digits.charAt(0) - '0';
        return firstDigit + sumOfDigits(digits.substring(1));
    }

    // Recursive function to compute the digital root
    public static int digitalRoot(String digits) {
        int sum = sumOfDigits(digits);
        if (sum < 10) {
            return sum; // Base case single-digit reached
        }
        // Convert sum back to string and call recursively
        return digitalRoot(String.valueOf(sum));
    }

    public static void main(String[] args) {
        String input = "1234445";
        int result = digitalRoot(input);
        System.out.println("Digital Root: " + result);
    }
}
