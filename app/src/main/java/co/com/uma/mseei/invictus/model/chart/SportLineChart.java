package co.com.uma.mseei.invictus.model.chart;

import static java.lang.Float.parseFloat;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import co.com.uma.mseei.invictus.model.database.Sport;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * LineChart is a model class which allows to define color, data, limits and other attributes in a line chart.
 * @author Sandra Marcela López Torres
 * @version 0.1, 2023/07/02
 */
public class SportLineChart extends LineChart {

    public SportLineChart(Activity activity, LineChartView lineChartView) {
        super(activity, lineChartView);
    }

    protected List<PointValue> getPointValues() {
        List<PointValue> pointValues = new ArrayList<>();
        float xPreviousValue = -1;
        float yTotalValue = 0;
        float xValue;
        float yValue;

        for (Object data : dataList) {
            Sport sport = (Sport) data;
            xValue = index == 0 ? parseFloat(sport.getStartDateTime().substring(11, 13)) :
                    limits.getPeriodInDaysFromStartTo(sport.getStartDateTime().substring(0, 10));
            yValue = sport.getCalories();
            if (xPreviousValue == xValue) {
                yTotalValue = yTotalValue + yValue;
                limits.setMaxY(yTotalValue);
            } else if (xPreviousValue < 0) {
                xPreviousValue = xValue;
                yTotalValue = yValue;
            } else {
                pointValues.add(new PointValue(xPreviousValue, yTotalValue));
                xPreviousValue = xValue;
                yTotalValue = yValue;
            }
        }
        pointValues.add(new PointValue(xPreviousValue, yTotalValue));

        return pointValues;
    }

}
