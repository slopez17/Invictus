package co.com.uma.mseei.invictus.model.service;

import static java.lang.System.currentTimeMillis;
import static java.time.ZoneId.systemDefault;
import static co.com.uma.mseei.invictus.model.SportType.WALK;
import static co.com.uma.mseei.invictus.util.UnitsAndConversions.YYYY_MM_DD__HH_MM_SS;
import static co.com.uma.mseei.invictus.util.UnitsAndConversions.floatToString;
import static co.com.uma.mseei.invictus.util.UnitsAndConversions.m2km;
import static co.com.uma.mseei.invictus.util.UnitsAndConversions.ms2h;
import static co.com.uma.mseei.invictus.util.UnitsAndConversions.timeToString;

import android.hardware.SensorEvent;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import co.com.uma.mseei.invictus.model.database.Sport;

public class AccelerometerServiceData {

    public final AccelerometerServiceParameters parameters;

    private static final int FALLS_ANTI_BOUNCE = 5000;
    private static final float STEP_FEMALE_LONGITUDE_M = 0.67f;
    private static final float STEP_MALE_LONGITUDE_M = 0.762f;
    private static final float STEP_NOANSWER_LONGITUDE_M = 0.716f;
    private static final float MET_ROPE_SKIPPING = 8f;
    private static final float MET_LIGTH_WALK = 2.5f;
    private static final float SPEED_LIGTH_WALK = 0.06f; // 3.6 km/h = 0.06 km/min
    private static final float SPEED_ROPE_SKIPPING = 120f; // 2 jumps/s = 120 jumps/min

    private int falls;
    private long lastFallTime;
    private int steps;
    private int stepsReference;
    private float calories;
    private float caloriesFactor; //kcal_per_min
    private float distance;
    private float distanceUnit;
    private float speed;
    private long sectionStartTime;
    private long sectionEndTime;
    private int sectionStepsReference;
    private final long startTime;
    private long endTime;
    private long elapsedTime;
    private final ZoneId zoneId;
    private final Acceleration acceleration;


    public AccelerometerServiceData(AccelerometerServiceParameters parameters) {
        this.parameters = parameters;
        this.acceleration = new Acceleration();

        long now = currentTimeMillis();
        this.lastFallTime = now - FALLS_ANTI_BOUNCE;
        this.startTime = now;
        this.endTime = now;
        this.sectionStartTime = now;
        this.sectionEndTime = now;
        this.zoneId = systemDefault();

        this.falls = 0;
        this.steps = 0;
        this.stepsReference = 0;
        this.sectionStepsReference = 0;
        this.elapsedTime = 0L;

        setDistanceUnit();
        setCaloriesFactor();
    }

    public String getStartTime(DateTimeFormatter formatter) {
        Instant instant = Instant.ofEpochSecond(startTime);
        LocalDateTime startDateTime = instant.atZone(zoneId).toLocalDateTime();
        return startDateTime.format(formatter);
    }

    public String getEndTime(DateTimeFormatter formatter) {
        Instant instant = Instant.ofEpochSecond(endTime);
        LocalDateTime endDateTime = instant.atZone(zoneId).toLocalDateTime();
        return endDateTime.format(formatter);
    }

    public void setTime() {
        long now = currentTimeMillis();
        this.endTime = now;
        this.sectionEndTime = now;
        setElapsedTime();
    }

    public String getElapsedTime() {
        return timeToString(elapsedTime);
    }

    private void setElapsedTime() {
        this.elapsedTime = endTime - startTime;
    }

    public int getFalls() {
        return falls;
    }

    public void setFalls() {
        if(acceleration.getTimestamp() - lastFallTime > FALLS_ANTI_BOUNCE){
            if(acceleration.getMagnitude() >= 0 && acceleration.getMagnitude() < 1){
                this.falls++;
                this.lastFallTime = acceleration.getTimestamp();
            }
        }
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        if(stepsReference == 0){
            this.stepsReference = steps;
        }
        this.steps = steps - stepsReference;
    }

    public float getCalories() {
        return calories;
    }

    public void setCalories() {
        this.calories = caloriesFactor * distance;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance() {
        this.distance = m2km(steps * distanceUnit);
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed() {
        this.speed = sectionEndTime == sectionStartTime ? 0f :
                m2km((steps - sectionStepsReference) * distanceUnit) / ms2h(sectionEndTime - sectionStartTime);
        this.sectionStartTime = endTime;
        this.sectionStepsReference = steps;
    }

    public Acceleration getAcceleration(){
        return acceleration;
    }

    public void setAcceleration(SensorEvent sensorEvent) {
        acceleration.setxAxis(sensorEvent.values[0]);
        acceleration.setYAxis(sensorEvent.values[1]);
        acceleration.setZAxis(sensorEvent.values[2]);
        acceleration.setTimestamp(endTime);
    }

    public String[] getScreenData() {
        return new String[] {
                Integer.toString(falls),
                Integer.toString(steps),
                floatToString(calories),
                floatToString(distance),
                floatToString(speed),
                timeToString(elapsedTime)
        };
    }

    public Sport getSport(){
        return new Sport(
                parameters.getSportId(),
                parameters.getSportType().toString(),
                getStartTime(YYYY_MM_DD__HH_MM_SS),
                getEndTime(YYYY_MM_DD__HH_MM_SS),
                getElapsedTime(),
                getFalls(),
                getSteps(),
                getDistance(),
                getCalories()
        );
    }

    private void setDistanceUnit() {
        if(parameters.getSportType() == WALK){
            switch (parameters.getGender()){
                case 1:
                    this.distanceUnit = STEP_FEMALE_LONGITUDE_M;
                    break;
                case 2:
                    this.distanceUnit = STEP_MALE_LONGITUDE_M;
                    break;
                default:
                    this.distanceUnit = STEP_NOANSWER_LONGITUDE_M;
                    break;
            }
        } else {
            this.distanceUnit = 0;
        }
    }

    private void setCaloriesFactor() {
        switch (parameters.getSportType()){
            case WALK:
                this.caloriesFactor =
                        (0.0175f * MET_LIGTH_WALK * parameters.getWeight()) / SPEED_LIGTH_WALK;
                break;
            case ROPE_SKIPPING:
                this.caloriesFactor =
                        (0.0175f * MET_ROPE_SKIPPING * parameters.getWeight()) / SPEED_ROPE_SKIPPING;
                break;
            default:
                this.caloriesFactor = 0;
                break;
        }
    }
}
