import java.util.TreeMap;

public class RomanNumeral {

    private Integer input;
    private static TreeMap<Integer, String> numbers = new TreeMap<Integer, String>();

    static {
        numbers.put(1000, "M");
        numbers.put(900, "CM");
        numbers.put(500, "D");
        numbers.put(400, "CD");
        numbers.put(100, "C");
        numbers.put(90, "XC");
        numbers.put(50, "L");
        numbers.put(40, "XL");
        numbers.put(10, "X");
        numbers.put(9, "IX");
        numbers.put(5, "V");
        numbers.put(4, "IV");
        numbers.put(1, "I");
        numbers.put(0, "");
    }

    public RomanNumeral(Integer input) {
        this.input = input;
    }

    public String getRomanNumeral() {
        return toRoman(input);
    }

    private String toRoman(Integer number) {
        int l = numbers.floorKey(number);
        if (number == l) {
            return numbers.get(number);
        }
        return numbers.get(l) + toRoman(number - l);
    }

}
