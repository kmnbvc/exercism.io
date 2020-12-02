import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Strain {

    public static <T> List<T> keep(List<T> input, Predicate<T> condition) {
        return input.stream().filter(condition).collect(Collectors.toList());
    }

    public static <T> List<T> discard(List<T> input, Predicate<T> condition) {
        return input.stream().filter(condition.negate()).collect(Collectors.toList());
    }

}
