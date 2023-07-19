package co.com.uma.mseei.invictus.model.chart.limit;

import static co.com.uma.mseei.invictus.util.MathOperations.kg2lbs;

/**
 * WeightLimit is a model class which extends from Limit and contains minimum and maximum tracking values, where X axis
 * represents time and Y axis represents weight.
 * @author Sandra Marcela López Torres
 * @version 0.1, 2023/07/02
 */
public class WeightLimit extends Limit{

    public WeightLimit(String minX, String maxX, float minY, float maxY) {
        super(minX, maxX, minY, maxY);
    }

    @Override
    public float getMinY(boolean isUnitSystemImperial) {
        return isUnitSystemImperial ? kg2lbs(minY) : minY;
    }

    @Override
    public float getMaxY(boolean isUnitSystemImperial) {
        return isUnitSystemImperial ? kg2lbs(maxY) : maxY;
    }

}
