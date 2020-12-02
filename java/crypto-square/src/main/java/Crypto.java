import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Crypto {
    private final String input;

    public Crypto(String input) {
        this.input = input;
    }

    public String getNormalizedPlaintext() {
        return input.toLowerCase().replaceAll("\\W", "");
    }

    public List<String> getPlaintextSegments() {
        String text = getNormalizedPlaintext();
        int rowLen = getSquareSize();
        String[] result = text.replaceAll("(.{" + rowLen + "})", "$0 ").split(" ");
        return Arrays.asList(result);
    }

    public int getSquareSize() {
        int length = getNormalizedPlaintext().length();
        return (int) Math.ceil(Math.sqrt(length));
    }

    public String getCipherText() {
        List<String> segments = getPlaintextSegments();
        return transpose(segments).collect(Collectors.joining());
    }

    public String getNormalizedCipherText() {
        List<String> segments = getPlaintextSegments();
        return transpose(segments).collect(Collectors.joining(" "));
    }

    private Stream<String> transpose(List<String> segments) {
        int rowLen = getSquareSize();
        return IntStream.range(0, rowLen)
                .mapToObj(col -> segments.stream()
                        .map(seg -> col < seg.length() ? seg.charAt(col) : "")
                        .map(String::valueOf).collect(Collectors.joining()));
    }

}
