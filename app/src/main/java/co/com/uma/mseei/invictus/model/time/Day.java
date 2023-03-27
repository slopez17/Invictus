package co.com.uma.mseei.invictus.model.time;

import static java.time.LocalTime.MAX;
import static java.time.LocalTime.MIN;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Day implements Time {

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public Day() {
        setActualPeriod();
    }

    @Override
    public void setActualPeriod(String... FromTo) {
        LocalDate startDate = LocalDate.now();
        startDateTime = LocalDateTime.of(startDate, MIN);
        endDateTime = LocalDateTime.of(startDate, MAX);
    }

    @Override
    public void setNextPeriod() {
        startDateTime = startDateTime.plusDays(1);
        endDateTime = endDateTime.plusDays(1);
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

