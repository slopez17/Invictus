package co.com.uma.mseei.invictus.model.database;

/**
 * SportLimit is a model abstract class which contains minimum and maximum tracking values to delimit a
 * chart. X axis represents time and Y axis represents a consumed calories.
 * @author Sandra Marcela López Torres
 * @version 0.1, 2023/07/02
 */
public class SportLimit extends Limit{

    public SportLimit(String minX, String maxX, float minY, float maxY) {
        super(minX, maxX, minY, maxY);
    }
    @Override
    public float getMinY() {
        return minY;
    }
    @Override
    public float getMaxY() {
        return maxY;
    }

}
