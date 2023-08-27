package co.com.uma.mseei.invictus.model.time;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.LocalDate.now;
import static java.time.LocalTime.MAX;
import static java.time.LocalTime.MIN;
import static co.com.uma.mseei.invictus.util.Constants.HYPHEN_WITH_SPACE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Week is a interface implementation class which represents time. It represents one week.
 * @author Sandra Marcela López Torres
 * @version 0.1, 2023/07/02
 */
public class Week implements Time {

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private final LocalDate now;

    public Week() {
        now = now();
        setCurrent();
    }

    @Override
    public void setCurrent(String... FromTo) {
        startDateTime = LocalDateTime.of(now.with(MONDAY), MIN);
        endDateTime = LocalDateTime.of(now.with(SUNDAY), MAX);
    }

    @Override
    public void setNext() {
        if (now.isAfter(endDateTime.toLocalDate())) {
            startDateTime = startDateTime.plusDays(7);
            endDateTime = endDateTime.plusDays(7);
        }
    }

    @Override
    public void setPrevious() {
        startDateTime = startDateTime.minusDays(7);
        endDateTime = endDateTime.minusDays(7);
    }

    @Override
    public String toString(DateTimeFormatter formatter) {
        return startDateTime.format(formatter) + HYPHEN_WITH_SPACE +  endDateTime.format(formatter);
    }

    @Override
    public String[] toStringArray(DateTimeFormatter formatter) {
        return new String[] {startDateTime.format(formatter), endDateTime.format(formatter)};
    }

}

