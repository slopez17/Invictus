package co.com.uma.mseei.invictus.model.chart;

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
        float xValue;
        float yValue;
        for (Object data : dataList) {
            Sport sport = (Sport) data;
            xValue = limits.getPeriodInDaysFromStartTo(sport.getStartDateTime().substring(1,11));
            yValue = sport.getCalories();
            pointValues.add(new PointValue(xValue, yValue));
        }

        return pointValues;
    }

}
