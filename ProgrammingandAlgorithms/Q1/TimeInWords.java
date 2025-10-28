public class TimeInWords {

    public static String toWords(int hour, int minute) {
        if (hour < 1 || hour > 12 || minute < 0 || minute > 59) {
            return "Invalid time input";
        }

        String[] words = {
            "", "one", "two", "three", "four", "five", "six", "seven",
            "eight", "nine", "ten", "eleven", "twelve", "thirteen",
            "fourteen", "quarter", "sixteen", "seventeen", "eighteen",
            "nineteen", "twenty", "twenty one", "twenty two", "twenty three",
            "twenty four", "twenty five", "twenty six", "twenty seven",
            "twenty eight", "twenty nine", "half"
        };

        String result;
        if (minute == 0) {
            result = words[hour] + " o'clock";
        } else if (minute == 15) {
            result = "quarter past " + words[hour];
        } else if (minute == 30) {
            result = "half past " + words[hour];
        } else if (minute == 45) {
            result = "quarter to " + words[nextHour(hour)];
        } else if (minute < 30) {
            String minuteWord = minute == 1 ? "minute" : "minutes";
            result = words[minute] + " " + minuteWord + " past " + words[hour];
        } else {
            int remaining = 60 - minute;
            String minuteWord = remaining == 1 ? "minute" : "minutes";
            result = words[remaining] + " " + minuteWord + " to " + words[nextHour(hour)];
        }

        return result.substring(0, 1).toUpperCase() + result.substring(1);
    }

    private static int nextHour(int current) {
        return current == 12 ? 1 : current + 1;
    }
    public static void main(String[] args) {
        // Quick manual test runs
        System.out.println(toWords(5, 0));   // Five o'clock
        System.out.println(toWords(5, 1));   // One minute past five
        System.out.println(toWords(5, 28));  // Twenty eight minutes past five
        System.out.println(toWords(5, 45));  // Quarter to six
        System.out.println(toWords(12, 59)); // One minute to one
        System.out.println(toWords(13, 0));  // Invalid time input
    }
}
