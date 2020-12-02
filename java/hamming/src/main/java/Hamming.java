public class Hamming {

    public static Integer compute(String s1, String s2) {
        if (s1.length() != s2.length())
            throw new IllegalArgumentException("Different lengths");

        int result = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i))
                result++;
        }
        return result;
    }

}
