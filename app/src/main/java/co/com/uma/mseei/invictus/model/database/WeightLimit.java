package co.com.uma.mseei.invictus.model.database;

import static co.com.uma.mseei.invictus.util.UnitsAndConversions.kg2lbs;

import android.app.Activity;

import androidx.room.Ignore;

import co.com.uma.mseei.invictus.model.AppPreferences;

/**
 * WeightLimit is a model abstract class which contains minimum and maximum tracking values to delimit a
 * chart. X axis represents time and Y axis represents a weight.
 * @author Sandra Marcela López Torres
 * @version 0.1, 2023/07/02
 */
public class WeightLimit extends Limit{

    @Ignore
    private boolean isUnitSystemImperial;

    public WeightLimit(String minX, String maxX, float minY, float maxY) {
        super(minX, maxX, minY, maxY);
    }

    @Override
    public float getMinY() {
        return isUnitSystemImperial ? kg2lbs(minY) : minY;
    }

    @Override
    public float getMaxY() {
        return isUnitSystemImperial? kg2lbs(maxY) : maxY;
    }

    public void setUnitSystem(Activity activity) {
        AppPreferences appPreferences = new AppPreferences(activity.getApplication());
        isUnitSystemImperial = appPreferences.isUnitSystemImperial();
    }

}
