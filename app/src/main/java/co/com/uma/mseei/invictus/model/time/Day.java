package co.com.uma.mseei.invictus.model.time;

import static java.time.LocalDate.now;
import static java.time.LocalTime.MAX;
import static java.time.LocalTime.MIN;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Day is a interface implementation class which represents time. It represents one day.
 * @author Sandra Marcela López Torres
 * @version 0.1, 2023/07/02
 */
public class Day implements Time {

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private final LocalDate now;

    public Day() {
        now = now();
        setCurrent();
    }

    @Override
    public void setCurrent(String... FromTo) {
        startDateTime = LocalDateTime.of(now, MIN);
        endDateTime = LocalDateTime.of(now, MAX);
    }

    @Override
    public void setNext() {
        if (now.isAfter(startDateTime.toLocalDate())) {
            startDateTime = startDateTime.plusDays(1);
            endDateTime = endDateTime.plusDays(1);
        }
    }

    @Override
    public void setPrevious() {
        startDateTime = startDateTime.minusDays(1);
        endDateTime = endDateTime.minusDays(1);
    }

    @Override
    public String toString(DateTimeFormatter formatter) {
        return startDateTime.format(formatter);
    }

    @Override
    public String[] toStringArray(DateTimeFormatter formatter) {
        return new String[] {startDateTime.format(formatter), endDateTime.format(formatter)};
    }
}

