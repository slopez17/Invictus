package co.com.uma.mseei.invictus.model.historical.time;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.LocalDate.now;
import static java.time.LocalTime.MAX;
import static java.time.LocalTime.MIN;
import static co.com.uma.mseei.invictus.util.GeneralConstants.HYPHEN_WITH_SPACE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Week implements Time {

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private final LocalDate now;

    public Week() {
        now = now();
        setActualPeriod();
    }

    @Override
    public void setActualPeriod(String... FromTo) {
        startDateTime = LocalDateTime.of(now.with(MONDAY), MIN);
        endDateTime = LocalDateTime.of(now.with(SUNDAY), MAX);
    }

    @Override
    public void setNextPeriod() {
        if (now.isAfter(endDateTime.toLocalDate())) {
            startDateTime = startDateTime.plusDays(7);
            endDateTime = endDateTime.plusDays(7);
        }
    }

    @Override
    public void setPreviousPeriod() {
        startDateTime = startDateTime.minusDays(7);
        endDateTime = endDateTime.minusDays(7);
    }

    @Override
    public String periodToString(DateTimeFormatter formatter) {
        return startDateTime.format(formatter) + HYPHEN_WITH_SPACE +  endDateTime.format(formatter);
    }

    @Override
    public String[] periodToStringArray(DateTimeFormatter formatter) {
        return new String[] {startDateTime.format(formatter), endDateTime.format(formatter)};
    }

}

