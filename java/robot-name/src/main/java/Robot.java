import java.io.Serializable;
import java.security.SecureRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Robot {

    private static final SecureRandom RANDOM = new SecureRandom();

    private String name = generateName();

    public String getName() {
        return name;
    }

    public void reset() {
        name = generateName();
    }

    private String generateName() {
        Stream<String> chars = RANDOM.ints(2, 'A', 'Z' + 1).mapToObj(x -> String.valueOf((char) x));
        Stream<String> numbers = RANDOM.ints(3, 0, 10).mapToObj(String::valueOf);
        return Stream.concat(chars, numbers).collect(Collectors.joining());
    }

}
