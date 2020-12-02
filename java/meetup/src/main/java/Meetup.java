import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;
import java.util.stream.IntStream;

public class Meetup {
    private final LocalDate date;

    public Meetup(int month, int year) {
        date = LocalDate.of(year, month, 1);
    }

    public LocalDate day(DayOfWeek weekDay, MeetupSchedule schedule) {
        return getPossibleDaysNumbers(schedule).mapToObj(date::withDayOfMonth)
                .filter(d -> d.getMonth().equals(date.getMonth()))
                .filter(d -> d.getDayOfWeek() == weekDay)
                .sorted(Comparator.reverseOrder())
                .findFirst().get();
    }

    private IntStream getPossibleDaysNumbers(MeetupSchedule schedule) {
        if (schedule == MeetupSchedule.TEENTH) {
            return IntStream.rangeClosed(13, 19);
        } else {
            int maxDayNumber = Math.min((7 * (schedule.ordinal() + 1)), date.lengthOfMonth());
            return IntStream.rangeClosed(1, maxDayNumber);
        }
    }

}
