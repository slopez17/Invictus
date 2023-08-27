package co.com.uma.mseei.invictus.model.profile;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.time.LocalDate.now;
import static co.com.uma.mseei.invictus.R.array.bmi_clasification_array;
import static co.com.uma.mseei.invictus.util.Resource.getStringFromArrayById;

import android.app.Application;

import java.time.LocalDate;
import java.time.Period;

/**
 * Profile is a model class which represents business logic about user profile.
 * @author Sandra Marcela López Torres
 * @version 0.1, 2023/07/02
 */
public class Profile {

    /**
     * Constant which specifies minimum weight allowed
     */
    private static final float MIN_WEIGHT_KG = 0.f;
    /**
     * Constant which specifies maximum weight allowed
     */
    private static final float MAX_WEIGHT_KG = 451.f;
    /**
     * Constant which specifies minimum height allowed
     */
    private static final float MIN_HEIGHT_M = 0.29f;
    /**
     * Constant which specifies maximum height allowed
     */
    private static final float MAX_HEIGHT_M = 2.81f;
    /**
     * Constant which specifies normal weight upper limit in BMI scale
     */
    private static final float NORMAL_WEIGHT = 18.5f;
    /**
     * Constant which specifies overweight 1 upper limit in BMI scale
     */
    private static final float OVERWEIGHT_1 = 25.f;
    /**
     * Constant which specifies overweight 2 upper limit in BMI scale
     */
    private static final float OVERWEIGHT_2 = 27.f;
    /**
     * Constant which specifies obesity 1 upper limit in BMI scale
     */
    private static final float OBESITY_1 = 30.f;
    /**
     * Constant which specifies obesity 2 upper limit in BMI scale
     */
    private static final float OBESITY_2 = 35.f;
    /**
     * Constant which specifies obesity 3 upper limit in BMI scale
     */
    private static final float OBESITY_3 = 40.f;
    /**
     * Constant which specifies obesity 4 upper limit in BMI scale
     */
    private static final float OBESITY_4 = 50.f;

    public static int calculateAge(LocalDate date) {
        return Period.between(date, now()).getYears();
    }

    public static float calculateBmi(float weight, float height) {
        return (float) (weight / Math.pow(height, 2));
    }

    public static String defineBmiClassification(Application application, float bmi) {
        int position = 0;
        if (bmi >= NORMAL_WEIGHT && bmi < OVERWEIGHT_1) {
            position = 1;
        } else if (bmi >= OVERWEIGHT_1 && bmi < OVERWEIGHT_2) {
            position = 2;
        } else if (bmi >= OVERWEIGHT_2  && bmi < OBESITY_1) {
            position = 3;
        } else if (bmi >= OBESITY_1 && bmi < OBESITY_2) {
            position = 4;
        } else if (bmi >= OBESITY_2&& bmi < OBESITY_3) {
            position = 5;
        } else if (bmi >= OBESITY_3 && bmi < OBESITY_4) {
            position = 6;
        } else if (bmi >= OBESITY_4) {
            position = 7;
        }
        return getStringFromArrayById(application, bmi_clasification_array, position);
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
