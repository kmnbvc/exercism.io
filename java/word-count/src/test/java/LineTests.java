import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runners.JUnit4;

public class LineTests {
    @Test
    public void test1() {
        assertEquals("YES", new Line().Tickets(new int[] {25, 25, 50}));
    }
    @Test
    public void test2() {
        assertEquals("NO", new Line().Tickets(new int []{25, 100}));
    }
}