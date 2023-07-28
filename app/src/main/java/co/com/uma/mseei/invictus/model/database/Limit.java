package co.com.uma.mseei.invictus.model.database;

import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDate;

/**
 * Limit is a model abstract class which contains minimum and maximum tracking values to delimit a
 * chart. X axis represents time and Y axis represents a magnitude.
 * @author Sandra Marcela López Torres
 * @version 0.1, 2023/07/02
 */
public abstract class Limit {

    protected String minX;
    protected String maxX;
    protected float minY;
    protected float maxY;

    public Limit(String minX, String maxX, float minY, float maxY) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    public String getMinX() {
        return minX;
    }

    public void setMinX(String minX) {
        this.minX = minX;
    }

    public String getMaxX() {
        return maxX;
    }

    public void setMaxX(String maxX) {
        this.maxX = maxX;
    }

    public abstract float getMinY();

    public void setMinY(float minY) {
        this.minY = minY;
    }

    public abstract float getMaxY();

    public void setMaxY(float maxY) {
        this.maxY = maxY;
    }

    /**
     * @return period in days, from start (minX) to end (maxY).
     */
    public long getPeriodInDaysFromStartToEnd() {
        return getPeriodInDaysBetween(minX, maxX);
    }

    /**
     * @param end A string date with 'yyyy-MM-dd' format.
     * @return period in days, from start (minX) to end (specificated date).
     */
    public long getPeriodInDaysFromStartTo(String end) {
        return getPeriodInDaysBetween(minX, end);
    }

    private long getPeriodInDaysBetween(String start, String end) {
        LocalDate startDateTime = parse(start, ISO_LOCAL_DATE);
        LocalDate endDateTime = parse(end, ISO_LOCAL_DATE);
        return DAYS.between(startDateTime, endDateTime);
    }
}
