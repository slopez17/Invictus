package co.com.uma.mseei.invictus.model.chart;

import static co.com.uma.mseei.invictus.util.UnitsAndConversions.kg2lbs;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import co.com.uma.mseei.invictus.model.database.Weight;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * LineChart is a model class which allows to define color, data, limits and other attributes in a line chart.
 * @author Sandra Marcela López Torres
 * @version 0.1, 2023/07/02
 */
public class SpeedLineChart extends LineChart {


    public SpeedLineChart(Activity activity, LineChartView lineChartView) {
        super(activity, lineChartView);
    }

    protected List<PointValue> getPointValues() {
        List<PointValue> pointValues = new ArrayList<>();
        float xValue;
        float yValue;
        for (Object data : dataList) {
            Weight weight = (Weight) data;
            xValue = limits.getPeriodInDaysFromStartTo(weight.getDate());
            yValue = weight.getWeight();
            if(isUnitSystemImperial) yValue = kg2lbs(yValue);
            pointValues.add(new PointValue(xValue, yValue));
        }
        return pointValues;
    }

}
