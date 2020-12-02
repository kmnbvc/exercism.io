import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordCount {
    public Map<String, Integer> phrase(String str) {
        return Stream.of(str.split("\\W+"))
                .collect(Collectors.groupingBy(String::toLowerCase, Collectors.summingInt(x -> 1)));
    }


}
