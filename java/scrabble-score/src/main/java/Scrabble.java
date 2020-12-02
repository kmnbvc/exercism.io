import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Scrabble {

    private static final Map<String, Integer> SCORES_BY_LETTERS;
    private String input;

    static {
        Map<List<String>, Integer> scores = new HashMap<>();
        scores.put(Arrays.asList("A", "E", "I", "O", "U", "L", "N", "R", "S", "T"), 1);
        scores.put(Arrays.asList("D", "G"), 2);
        scores.put(Arrays.asList("B", "C", "M", "P"), 3);
        scores.put(Arrays.asList("F", "H", "V", "W", "Y"), 4);
        scores.put(Arrays.asList("K"), 5);
        scores.put(Arrays.asList("J", "X"), 8);
        scores.put(Arrays.asList("Q", "Z"), 10);

        SCORES_BY_LETTERS = scores.entrySet().stream().flatMap(Scrabble::flat)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    public Scrabble(String input) {
        this.input = input != null ? input : "";
    }

    public int getScore() {
        return input.chars()
                .mapToObj(c -> String.valueOf((char) c))
                .map(String::toUpperCase)
                .filter(SCORES_BY_LETTERS::containsKey)
                .map(SCORES_BY_LETTERS::get)
                .reduce(0, Integer::sum);
    }

    private static <K, V> Stream<Map.Entry<K, V>> flat(Map.Entry<List<K>, V> entry) {
        return entry.getKey().stream().map(key -> new AbstractMap.SimpleEntry<>(key, entry.getValue()));
    }

}
