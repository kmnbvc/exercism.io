import java.util.stream.Collectors;

public class Atbash {

    public static String decode(String input) {
        return _encode(input);
    }

    public static String encode(String input) {
        return _encode(input).replaceAll("(.{5})", "$1 ").trim();
    }

    private static String _encode(String input) {
        return input.toLowerCase().chars()
                .filter(Character::isLetterOrDigit)
                .map(x -> Character.isLetter(x) ? (-x + 219) : x)
                .mapToObj(x -> String.valueOf((char) x))
                .collect(Collectors.joining());
    }

}
