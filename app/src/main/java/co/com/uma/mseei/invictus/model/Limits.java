package co.com.uma.mseei.invictus.model;

import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.temporal.ChronoUnit.DAYS;
import static co.com.uma.mseei.invictus.util.MathOperations.kg2lbs;

import java.time.LocalDate;

public class Limits {
    public static final int WEIGHT = 0;
    private String minX;
    private String maxX;
    private float minY;
    private float maxY;

    public Limits(String minX, String maxX, float minY, float maxY) {
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

    public float getMinY() {
        return minY;
    }

    public float getMinY(int undType, boolean isUnitSystemImperial) {
        switch (undType){
            default:
            case WEIGHT:
                return isUnitSystemImperial ? kg2lbs(minY) : minY;
        }

    }

    public void setMinY(float minY) {
        this.minY = minY;
    }

    public float getMaxY() {
        return maxY;
    }

    public float getMaxY(int undType, boolean isUnitSystemImperial) {
        switch (undType){
            default:
            case WEIGHT:
                return isUnitSystemImperial ? kg2lbs(maxY) : maxY;
        }

    }

    public void setMaxY(float maxY) {
        this.maxY = maxY;
    }

    public long getPeriodInDays() {
        return getPeriodInDaysBetween(minX, maxX);
    }

    public static long getPeriodInDaysBetween(String start, String end) {
        LocalDate startDateTime = parse(start, ISO_LOCAL_DATE);
        LocalDate endDateTime = parse(end, ISO_LOCAL_DATE);
        return DAYS.between(startDateTime, endDateTime);
    }
}
