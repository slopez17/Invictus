package co.com.uma.mseei.invictus.util;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

public class UnitsAndConversions {

    public static final String KG_UND = "kg";
    public static final String LBS_UND = "lbs";
    public static final float _1KG_LBS = 2.20462262185f;
    public static final String M_UND = "m";
    public static final String IN_UND = "in";
    public static final float _1IN_M = 0.0254f;
    public static final DecimalFormat TWO_DIGITS = new DecimalFormat("#.00");
    public static final DateTimeFormatter DD_MMM_YYYY = DateTimeFormatter.ofPattern("dd MMM yyyy");

    public static float kg2lbs(float kg){
        return kg * _1KG_LBS;
    }

    public static float lbs2kg(float lbs){
        return lbs * (1/_1KG_LBS);
    }

    public static float m2in(float m){
        return m * (1/_1IN_M);
    }

    public static float in2m(float in){
        return in * _1IN_M;
    }

    public static float getFloatFrom(String value) {
        if (value.isEmpty()) {
            return 0f;
        } else {
            return Float.parseFloat(value);
        }
    }
}
