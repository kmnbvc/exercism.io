import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DNA {

    private static List<Character> nucleotides = Arrays.asList('A', 'C', 'G', 'T');

    private String dna;

    public DNA(String dna) {
        this.dna = dna;
    }

    public int count(char character) {
        if (!nucleotides.contains(character))
            throw new IllegalArgumentException();

        return (int) dna.chars().filter(ch -> ch == character).count();
    }

    public Map<Character, Integer> nucleotideCounts() {
        return nucleotides.stream().collect(Collectors.toMap(Function.identity(), this::count));
    }

}
