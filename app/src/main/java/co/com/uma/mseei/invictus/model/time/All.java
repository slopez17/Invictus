package co.com.uma.mseei.invictus.model.time;

import static java.time.LocalDate.parse;
import static java.time.LocalTime.MIN;
import static co.com.uma.mseei.invictus.util.GeneralConstants.CLEAN;
import static co.com.uma.mseei.invictus.util.GeneralConstants.HYPHEN_WITH_SPACE;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class All implements Time {

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public All() {

    }

    @Override
    public void setActualPeriod(String... dates) {
        if (dates.length == 2){
            this.startDateTime = LocalDateTime.of(parse(dates[0]), MIN);
            this.endDateTime = LocalDateTime.of(parse(dates[1]), MIN);
        }
    }

    @Override
    public void setNextPeriod() {

    }

    @Override
    public void setPreviousPeriod() {

    }

    @Override
    public String periodToString(DateTimeFormatter formatter) {
        if(startDateTime==null || endDateTime==null){
            return CLEAN;
        }
        return startDateTime.format(formatter) + HYPHEN_WITH_SPACE + endDateTime.format(formatter);
    }

    @Override
    public String[] periodToStringArray(DateTimeFormatter formatter) {
        if(startDateTime==null || endDateTime==null) {
            String today = LocalDateTime.now().format(formatter);
            return new String[] {today, today};
        }
        return new String[] {startDateTime.format(formatter), endDateTime.format(formatter)};
    }

}
