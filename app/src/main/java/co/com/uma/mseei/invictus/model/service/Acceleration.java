package co.com.uma.mseei.invictus.model.service;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.time.LocalDateTime.now;
import static java.time.LocalDateTime.ofInstant;
import static java.time.ZoneId.systemDefault;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Acceleration is a model class which represents business logic about acceleration.
 * @author Sandra Marcela López Torres
 * @version 0.1, 2023/07/02
 */
public class Acceleration {

    private float xAxis;
    private float yAxis;
    private float zAxis;
    private float magnitude;
    private long timestamp;
    private LocalDateTime date;

    public Acceleration() {
        this.xAxis = 0;
        this.yAxis = 0;
        this.zAxis = 0;
        this.magnitude = 0;
        this.timestamp = 0;
        this.date = now();
    }

    public float getXAxis() {
        return xAxis;
    }

    public void setxAxis(float xAxis) {
        this.xAxis = xAxis;
        calculateMagnitude();
    }

    public float getyAxis() {
        return yAxis;
    }

    public void setYAxis(float yAxis) {
        this.yAxis = yAxis;
        calculateMagnitude();
    }

    public float getzAxis() {
        return zAxis;
    }

    public void setZAxis(float zAxis) {
        this.zAxis = zAxis;
        calculateMagnitude();
    }

    public float getMagnitude() {
        return magnitude;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        calculateDate();
    }

    public String getDate(DateTimeFormatter formatter) {
        return date.format(formatter);
    }

    private void calculateMagnitude(){
        this.magnitude = (float) sqrt(pow(xAxis, 2) + pow(yAxis, 2) + pow(zAxis, 2));
    }

    private void calculateDate(){
        Instant instant = Instant.ofEpochMilli(timestamp);
        this.date = ofInstant(instant, systemDefault());
    }
}
