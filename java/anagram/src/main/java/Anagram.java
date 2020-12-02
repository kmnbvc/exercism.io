import java.util.List;
import java.util.stream.Collectors;

public class Anagram {

    private String word;
    private List<Character> chars;

    public Anagram(String word) {
        this.word = word;
        this.chars = charsList(word);
    }

    public List<String> match(List<String> strings) {
        return strings.stream().filter(str -> !str.equalsIgnoreCase(word))
                .filter(str -> charsList(str).equals(chars))
                .collect(Collectors.toList());
    }

    private static List<Character> charsList(String s) {
        return s.chars().map(Character::toLowerCase).sorted().mapToObj(c -> (char) c).collect(Collectors.toList());
    }
}
