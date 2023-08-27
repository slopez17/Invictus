package co.com.uma.mseei.invictus.model.time;

import static java.time.LocalDate.now;
import static java.time.LocalTime.MAX;
import static java.time.LocalTime.MIN;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static co.com.uma.mseei.invictus.util.Constants.HYPHEN_WITH_SPACE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Month is a interface implementation class which represents time. It represents one month.
 * @author Sandra Marcela López Torres
 * @version 0.1, 2023/07/02
 */
public class Month implements Time {

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private final LocalDate now;

    public Month() {
        now = now();
        setCurrent();
    }

    @Override
    public void setCurrent(String... FromTo) {
        LocalDate startDate = now.with(DAY_OF_MONTH,1);
        startDateTime = LocalDateTime.of(startDate, MIN);
        endDateTime = LocalDateTime.of(startDate.plusMonths(1).minusDays(1), MAX);
    }

    @Override
    public void setNext() {
        if (now.isAfter(endDateTime.toLocalDate())) {
            startDateTime = endDateTime.plusNanos(1);
            endDateTime = startDateTime.plusMonths(1).minusNanos(1);
        }
    }

    @Override
    public void setPrevious() {
        endDateTime = startDateTime.minusNanos(1);
        startDateTime = startDateTime.minusMonths(1);
    }

    @Override
    public String toString(DateTimeFormatter formatter) {
        return startDateTime.format(formatter) + HYPHEN_WITH_SPACE + endDateTime.format(formatter);
    }

    @Override
    public String[] toStringArray(DateTimeFormatter formatter) {
        return new String[] {startDateTime.format(formatter), endDateTime.format(formatter)};
    }
}
