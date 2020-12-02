import java.util.stream.Stream;
import static java.util.stream.Collectors.joining;

public class Acronym {
    public static String generate(String phrase) {
        return Stream.of(phrase.split("\\W"))
                .flatMap(Acronym::splitInconsistentWords)
                .filter(s -> !s.isEmpty())
                .map(s -> s.substring(0, 1).toUpperCase())
                .collect(joining());
    }

    private static Stream<String> splitInconsistentWords(String str) {
        String[] result = str.equals(str.toUpperCase()) ? new String[]{str} : str.split("(?=[A-Z])");
        return Stream.of(result);
    }

}
