import java.util.AbstractMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Binary {

    private static final Predicate<String> INVALID_PATTERN = Pattern.compile("[^10]+").asPredicate();
    private final String input;

    public Binary(String input) {
        this.input = INVALID_PATTERN.test(input) ? "" : input;
    }

    public int getDecimal() {
        int length = input.length();
        return IntStream.range(0, length).boxed()
                .map(pos -> pair(pos, input.charAt(length - pos - 1)))
                .filter(entry -> entry.getValue().equals('1'))
                .collect(Collectors.summingInt(entry -> (int) Math.pow(2, entry.getKey())));
    }

    private <K, V> Map.Entry<K, V> pair(K key, V value) {
        return new AbstractMap.SimpleEntry<>(key, value);
    }
}
