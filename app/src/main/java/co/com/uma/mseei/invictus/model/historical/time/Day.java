package co.com.uma.mseei.invictus.model.historical.time;

import static java.time.LocalDate.now;
import static java.time.LocalTime.MAX;
import static java.time.LocalTime.MIN;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Day implements Time {

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private final LocalDate now;

    public Day() {
        now = now();
        setActualPeriod();
    }

    @Override
    public void setActualPeriod(String... FromTo) {
        startDateTime = LocalDateTime.of(now, MIN);
        endDateTime = LocalDateTime.of(now, MAX);
    }

    @Override
    public void setNextPeriod() {
        if (now.isAfter(startDateTime.toLocalDate())) {
            startDateTime = startDateTime.plusDays(1);
            endDateTime = endDateTime.plusDays(1);
        }
    }

    @Override
    public void setPreviousPeriod() {
        startDateTime = startDateTime.minusDays(1);
        endDateTime = endDateTime.minusDays(1);
    }

    @Override
    public String periodToString(DateTimeFormatter formatter) {
        return startDateTime.format(formatter);
    }

    @Override
    public String[] periodToStringArray(DateTimeFormatter formatter) {
        return new String[] {startDateTime.format(formatter), endDateTime.format(formatter)};
    }
}

