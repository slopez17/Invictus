package co.com.uma.mseei.invictus.model.chart;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import co.com.uma.mseei.invictus.model.database.Speed;
import lecho.lib.hellocharts.model.AxisValue;
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
        for (int i = 0; i < dataList.size(); i++) {
            Speed speed = (Speed) dataList.get(i);
            pointValues.add(new PointValue(i, speed.getSpeed()));
        }
        return pointValues;
    }

    /**
     * @return list with X axis labels.
     */
    protected List<AxisValue> getAxisXValuesFrom() {
        List<AxisValue> axisXValues = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            Speed speed = (Speed) dataList.get(i);
            axisXValues.add(new AxisValue(i).setLabel(speed.getElapsedTime()));
        }
        return axisXValues;
    }

    protected float getRight() {
        return dataList.size();
    }
}
