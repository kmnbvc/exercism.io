import java.util.ArrayList;
import java.util.List;

public class PrimeFactors {

    public static List<Long> getForNumber(long number) {
        List<Long> result = new ArrayList<>();
        long prime = 2;
        while (prime <= number) {
            if (number % prime == 0) {
                result.add(prime);
                number = number / prime;
            } else {
                prime++;
            }
        }

        return result;
    }

}
