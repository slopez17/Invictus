package co.com.uma.mseei.invictus.model.chart;

import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static co.com.uma.mseei.invictus.util.UnitsAndConversions.TWO_DIGITS_HOUR;
import static co.com.uma.mseei.invictus.util.UnitsAndConversions.kg2lbs;

import android.app.Activity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import co.com.uma.mseei.invictus.model.database.Weight;
import lecho.lib.hellocharts.model.AxisValue;
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
        for (Object data : dataList) {
            Weight weight = (Weight) data;
            xValue = limits.getPeriodInDaysFromStartTo(weight.getDate());
            yValue = weight.getWeight();
            if(isUnitSystemImperial) yValue = kg2lbs(yValue);
            pointValues.add(new PointValue(xValue, yValue));
        }
        return pointValues;
    }

    /**
     * @return list with X axis labels.
     */
    protected List<AxisValue> getAxisXValuesFrom() {
        List<AxisValue> axisXValues = new ArrayList<>();
        long period = limits.getPeriodInDaysFromStartToEnd();
        LocalDate date = parse(limits.getMinX(), ISO_LOCAL_DATE);
        for (int i = 0; i <= period; i++) {
            axisXValues.add(new AxisValue(i).setLabel(date.toString()));
            date = date.plusDays(1);
        }
        return axisXValues;
    }

    protected float getRight() {
        return limits.getPeriodInDaysFromStartToEnd() + 1f;
    }
}
