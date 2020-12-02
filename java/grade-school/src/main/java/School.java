import java.util.*;

public class School {
    private Map<Integer, List<String>> db = new HashMap<>();

    public Map<Integer, List<String>> db() {
        return db;
    }

    public void add(String name, Integer grade) {
        List<String> byGrade = grade(grade);
        byGrade.add(name);
        db.putIfAbsent(grade, byGrade);
    }

    public List<String> grade(Integer grade) {
        return db.getOrDefault(grade, new ArrayList<>());
    }

    public Map<Integer, List<String>> sort() {
        db.forEach((grade, names) -> Collections.sort(names));
        return db;
    }
}
