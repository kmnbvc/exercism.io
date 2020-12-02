import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Gigasecond {

    public static final Duration GIGASECOND = Duration.ofSeconds(1000000000L);
    private LocalDateTime birthday;

    public Gigasecond(LocalDate date) {
        this.birthday = date.atStartOfDay();
    }

    public Gigasecond(LocalDateTime dateTime) {
        this.birthday = dateTime;
    }

    public LocalDateTime getDate() {
        return birthday.plus(GIGASECOND);
    }
}
