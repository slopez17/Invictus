package co.com.uma.mseei.invictus.model;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.time.LocalDate.now;
import static co.com.uma.mseei.invictus.R.array.bmi_clasification_array;
import static co.com.uma.mseei.invictus.util.GeneralConstants.CLEAN_TEXT;
import static co.com.uma.mseei.invictus.util.ResourceOperations.getStringArrayById;

import android.app.Application;

import java.time.LocalDate;
import java.time.Period;

public class Profile {

    /** Gender constants **/
    public static final int NO_ANSWER = 0;
    public static final int FEMALE = 1;
    public static final int MALE = 2;

    /** Weight constants **/
    public static final float MIN_WEIGHT_KG = 0.f;
    public static final float MAX_WEIGHT_KG = 451.f;
    public static final float MIN_WEIGHT_LBS = 0.f;
    public static final float MAX_WEIGHT_LBS = 994.28f;

    /** Height constants **/
    public static final float MIN_HEIGHT_M = 0.29f;
    public static final float MAX_HEIGHT_M = 2.81f;
    public static final float MIN_HEIGHT_IN = 11.41f;
    public static final float MAX_HEIGHT_IN = 110.62f;

    /** Bmi constants **/
    public static final float NORMAL_WEIGHT = 18.5f;
    public static final float OVERWEIGHT_1 = 25.f;
    public static final float OVERWEIGHT_2 = 27.f;
    public static final float OBESITY_1 = 30.f;
    public static final float OBESITY_2 = 35.f;
    public static final float OBESITY_3 = 40.f;
    public static final float OBESITY_4 = 50.f;

    /**
     * Public Methods
     **/
    public static int calculateAge(LocalDate date) {
        return Period.between(date, now()).getYears();
    }

    public static float calculateBmi(float weight, float height) {
        return (float) (weight / Math.pow(height, 2));
    }

    public static String calculateBmiClassification(Application application, float bmi) {
        String[] classification = getStringArrayById(application, bmi_clasification_array);

        if (bmi < NORMAL_WEIGHT) {
            return classification[0];
        } else if (bmi >= NORMAL_WEIGHT && bmi < OVERWEIGHT_1) {
            return classification[1];
        } else if (bmi >= OVERWEIGHT_1 && bmi < OVERWEIGHT_2) {
            return classification[2];
        } else if (bmi >= OVERWEIGHT_2  && bmi < OBESITY_1) {
            return classification[3];
        } else if (bmi >= OBESITY_1 && bmi < OBESITY_2) {
            return classification[4];
        } else if (bmi >= OBESITY_2&& bmi < OBESITY_3) {
            return classification[5];
        } else if (bmi >= OBESITY_3 && bmi < OBESITY_4) {
            return classification[6];
        } else if (bmi >= OBESITY_4) {
            return classification[7];
        } else {
            return CLEAN_TEXT;
        }

    }

    public static float fixWeightToLimits(float weight) {
        weight = min(weight, MAX_WEIGHT_KG);
        weight = max(weight, MIN_WEIGHT_KG);
        return weight;
    }

    public static float fixHeightToLimits(float height) {
        height = min(height, MAX_HEIGHT_M);
        height = max(height, MIN_HEIGHT_M);
        return height;
    }
}
