package co.com.uma.mseei.invictus.model.historical.time;

import java.time.format.DateTimeFormatter;

public interface Time {

    void setActualPeriod(String... FromTo);
    void setNextPeriod();
    void setPreviousPeriod();
    String periodToString(DateTimeFormatter formatter);
    String[] periodToStringArray(DateTimeFormatter formatter);
}
