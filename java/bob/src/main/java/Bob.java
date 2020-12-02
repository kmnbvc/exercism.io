public class Bob {

    public String hey(String s) {
        if (s.trim().isEmpty()) {
            return "Fine. Be that way!";
        } else if (s.chars().anyMatch(Character::isAlphabetic) && s.equals(s.toUpperCase())) {
            return "Whoa, chill out!";
        } else if (s.endsWith("?")) {
            return "Sure.";
        } else {
            return "Whatever.";
        }
    }

}
