import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Etl {
    public Map<String, Integer> transform(Map<Integer, List<String>> old) {
        Map<String, Integer> result = new HashMap<>();
        old.forEach((score, letters) -> result.putAll(letters.stream()
                .collect(Collectors.toMap(String::toLowerCase, letter -> score))));
        return result;
    }
}
