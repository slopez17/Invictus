package co.com.uma.mseei.invictus.model.time;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.LocalTime.MAX;
import static java.time.LocalTime.MIN;
import static co.com.uma.mseei.invictus.util.GeneralConstants.HYPHEN_WITH_SPACE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Week implements Time {

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public Week() {
        setActualPeriod();
    }

    @Override
    public void setActualPeriod(String... FromTo) {
        LocalDate startDate = LocalDate.now();
        startDateTime = LocalDateTime.of(startDate.with(MONDAY), MIN);
        endDateTime = LocalDateTime.of(startDate.with(SUNDAY), MAX);
    }

    @Override
    public void setNextPeriod() {
        startDateTime = startDateTime.plusDays(7);
        endDateTime = endDateTime.plusDays(7);
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

