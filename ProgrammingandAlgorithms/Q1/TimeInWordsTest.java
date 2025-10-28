import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TimeInWordsEdgeCaseTest {

    @Test
    void testMinuteZero_exactHour() {
        assertEquals("Five o'clock", TimeInWords.toWords(5, 0),
                "Should return 'Five o'clock' for exact hour");
    }

    @Test
    void testQuarterPast() {
        assertEquals("Quarter past five", TimeInWords.toWords(5, 15),
                "Should return 'Quarter past five' for 5:15");
    }

    @Test
    void testHalfPast() {
        assertEquals("Half past five", TimeInWords.toWords(5, 30),
                "Should return 'Half past five' for 5:30");
    }

    @Test
    void testQuarterTo() {
        assertEquals("Quarter to six", TimeInWords.toWords(5, 45),
                "Should return 'Quarter to six' for 5:45");
    }

    @Test
    void testOneMinuteToNextHour() {
        assertEquals("One minute to one", TimeInWords.toWords(12, 59),
                "Should return 'One minute to one' for 12:59");
    }

    @Test
    void testZeroHour_invalid() {
        assertEquals("Invalid time input", TimeInWords.toWords(0, 15),
                "Hour = 0 should be invalid (valid range is 1–12)");
    }

    @Test
    void testSixtyMinutes_invalid() {
        assertEquals("Invalid time input", TimeInWords.toWords(5, 60),
                "Minute = 60 should be invalid (valid range is 0–59)");
    }

    @Test
    void testBoundaryHourTwelveExact() {
        assertEquals("Twelve o'clock", TimeInWords.toWords(12, 0),
                "Should correctly handle upper hour boundary at 12");
    }
}
