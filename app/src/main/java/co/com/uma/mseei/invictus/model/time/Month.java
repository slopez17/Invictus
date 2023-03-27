package co.com.uma.mseei.invictus.model.time;

import static java.time.LocalTime.MAX;
import static java.time.LocalTime.MIN;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static co.com.uma.mseei.invictus.util.GeneralConstants.HYPHEN_WITH_SPACE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Month implements Time {

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public Month() {
        setActualPeriod();
    }

    @Override
    public void setActualPeriod(String... FromTo) {
        LocalDate startDate = LocalDate.now().with(DAY_OF_MONTH,1);
        startDateTime = LocalDateTime.of(startDate, MIN);
        endDateTime = LocalDateTime.of(startDate.plusMonths(1).minusDays(1), MAX);
    }

    @Override
    public void setNextPeriod() {
        startDateTime = endDateTime.plusNanos(1);
        endDateTime = startDateTime.plusMonths(1).minusNanos(1);
    }

    @Override
    public void setPreviousPeriod() {
        endDateTime = startDateTime.minusNanos(1);
        startDateTime = startDateTime.minusMonths(1);
    }

    @Override
    public String periodToString(DateTimeFormatter formatter) {
        return startDateTime.format(formatter) + HYPHEN_WITH_SPACE + endDateTime.format(formatter);
    }

    @Override
    public String[] periodToStringArray(DateTimeFormatter formatter) {
        return new String[] {startDateTime.format(formatter), endDateTime.format(formatter)};
    }
}