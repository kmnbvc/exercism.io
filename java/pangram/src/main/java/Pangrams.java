import java.util.List;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;

public class Pangrams {

    private static List<Character> alphabetLetters = charsList("abcdefghijklmnopqrstuvwxyz", IntUnaryOperator.identity());

    public static boolean isPangram(String str) {
        return charsList(str, Character::toLowerCase).containsAll(alphabetLetters);
    }

    private static List<Character> charsList(String s, IntUnaryOperator mapFunc) {
        return s.chars().map(mapFunc).mapToObj(c -> (char) c).collect(Collectors.toList());
    }
}
