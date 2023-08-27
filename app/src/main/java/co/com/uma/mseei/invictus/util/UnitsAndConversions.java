package co.com.uma.mseei.invictus.util;

import java.text.DecimalFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class UnitsAndConversions {

    public static final String KG_UND = "kg";
    public static final String LBS_UND = "lbs";
    public static final float _1KG_LBS = 2.20462262185f;
    public static final String M_UND = "m";
    public static final String KM_UND = "km";
    public static final String IN_UND = "in";
    public static final float _1IN_M = 0.0254f;
    public static final float _1M_KM = 0.001f;
    public static final int _1H_MS= 3600000;
    public static final int _1MIN_MS= 60000;
    public static final int _1S_MS= 1000;

    public static final DateTimeFormatter YYYY_MM_DD__HH_MM_SS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DD_MMM_YYYY = DateTimeFormatter.ofPattern("dd MMM yyyy");
    public static final DateTimeFormatter HH_MM_SS = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final DecimalFormat TWO_DIGITS = new DecimalFormat("#.00");

    public static float kg2lbs(float kg){
        return kg * _1KG_LBS;
    }

    public static float lbs2kg(float lbs){
        return lbs / _1KG_LBS;
    }

    public static float m2in(float m){
        return m / _1IN_M;
    }

    public static float in2m(float in){
        return in * _1IN_M;
    }

    public static float m2km(float m){
        return m * _1M_KM;
    }

    public static float ms2h(long ms){
        return (float) ms / (float) _1H_MS;
    }
    public static long s2ms(long s){
        return s * _1S_MS;
    }
    public static long min2ms(long min){
        return min * _1MIN_MS;
    }
    public static float stringToFloat(String value) {
        if (value.isEmpty()) {
            return 0f;
        } else {
            return Float.parseFloat(value);
        }
    }

    public static String floatToString(float value) {
        return TWO_DIGITS.format(value);
    }

    public static String timeToString(long time) {
        int hour = (int) (time / _1H_MS);
        int minute = (int) (time - hour * _1H_MS) / _1MIN_MS;
        int second = (int) (time - hour * _1H_MS - minute * _1MIN_MS) / _1S_MS;
        LocalTime localTime = LocalTime.of(hour,minute,second);
        return  localTime.format(HH_MM_SS);
    }
}
