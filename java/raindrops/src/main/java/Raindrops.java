import java.util.*;

public class Raindrops {

    private static final Map<Integer, String> CONVERSIONS = new TreeMap<>();

    static {
        CONVERSIONS.put(3, "Pling");
        CONVERSIONS.put(5, "Plang");
        CONVERSIONS.put(7, "Plong");
    }

    public static String convert(int number) {
        return CONVERSIONS.keySet().stream()
                .filter(x -> number % x == 0)
                .map(CONVERSIONS::get)
                .reduce(String::concat)
                .orElse(String.valueOf(number));
    }

}
