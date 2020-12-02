public class PhoneNumber {

    public static final String PRETTY_PATTERN = "(%s) %s-%s";
    private String number;

    public PhoneNumber(String number) {
        number = number.replaceAll("\\D", "");
        if (number.length() == 11 && number.startsWith("1")) {
            this.number = number.substring(1);
        } else if (number.length() == 10) {
            this.number = number;
        } else {
            this.number = "0000000000";
        }
    }

    public String getNumber() {
        return number;
    }

    public String getAreaCode() {
        return number.substring(0, 3);
    }

    public String pretty() {
        String part1 = number.substring(3, 6);
        String part2 = number.substring(6);
        return String.format(PRETTY_PATTERN, getAreaCode(), part1, part2);
    }
}
