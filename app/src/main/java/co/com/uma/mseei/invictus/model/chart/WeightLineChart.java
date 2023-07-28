package co.com.uma.mseei.invictus.model.chart;

import static co.com.uma.mseei.invictus.util.UnitsAndConversions.kg2lbs;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * LineChart is a model class which allows to define color, data, limits and other attributes in a line chart.
 * @author Sandra Marcela López Torres
 * @version 0.1, 2023/07/02
 */
public class WeightLineChart extends LineChart {

    public WeightLineChart(Activity activity, LineChartView lineChartView) {
        super(activity, lineChartView);
    }

    protected List<PointValue> getPointValues() {
        List<PointValue> pointValues = new ArrayList<>();
        float xValue;
        float yValue;
        for (int i=0; i<dataList.size(); i++){
            xValue = limits.getPeriodInDaysFromStartTo(dataList.get(i).getDate());
            yValue = dataList.get(i).getWeight();
            if(isUnitSystemImperial) yValue = kg2lbs(yValue);
            pointValues.add(new PointValue(xValue, yValue));
        }

        return pointValues;
    }

}
