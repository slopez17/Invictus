package co.com.uma.mseei.invictus.model.chart;

import static java.lang.Float.parseFloat;
import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

import static co.com.uma.mseei.invictus.util.UnitsAndConversions.TWO_DIGITS_HOUR;

import android.app.Activity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import co.com.uma.mseei.invictus.model.database.Sport;
import lecho.lib.hellocharts.model.AxisValue;
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

    /**
     * @return list with X axis labels.
     */
    protected List<AxisValue> getAxisXValuesFrom() {
        List<AxisValue> axisXValues = new ArrayList<>();
        long period;
        if(index == 0) {
            period = 24;
            for (int i = 0; i <= period; i++) {
                axisXValues.add(new AxisValue(i).setLabel(TWO_DIGITS_HOUR.format(i)));
            }
        } else {
            period = limits.getPeriodInDaysFromStartToEnd();
            LocalDate date = parse(limits.getMinX(), ISO_LOCAL_DATE);
            for (int i = 0; i <= period; i++) {
                axisXValues.add(new AxisValue(i).setLabel(date.toString()));
                date = date.plusDays(1);
            }
        }
        return axisXValues;
    }

    protected float getRight() {
        return index == 0 ? 24f : limits.getPeriodInDaysFromStartToEnd() + 1f;
    }
}
