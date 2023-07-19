package co.com.uma.mseei.invictus.model.chart;

import static android.graphics.Color.GRAY;
import static android.graphics.Color.parseColor;
import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static co.com.uma.mseei.invictus.util.ResourceOperations.getColorById;
import static lecho.lib.hellocharts.gesture.ContainerScrollType.HORIZONTAL;
import static lecho.lib.hellocharts.model.ValueShape.CIRCLE;

import android.app.Activity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import co.com.uma.mseei.invictus.model.AppPreferences;
import co.com.uma.mseei.invictus.model.chart.limit.Limit;
import co.com.uma.mseei.invictus.model.database.Weight;
import lecho.lib.hellocharts.formatter.SimpleAxisValueFormatter;
import lecho.lib.hellocharts.formatter.SimpleLineChartValueFormatter;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * LineChart is a model class which allows to define color, data, limits and other attributes in a line chart.
 * @author Sandra Marcela López Torres
 * @version 0.1, 2023/07/02
 */
public class LineChart  {

    private final Activity activity;
    private final AppPreferences appPreferences;
    private int color;
    private List<Weight> dataList;
    private final LineChartView lineChart;
    private LineChartData lineChartData;
    private Limit limits;

    public LineChart(Activity activity, LineChartView lineChartView) {
        this.activity = activity;
        this.lineChart = lineChartView;
        this.appPreferences = new AppPreferences(activity.getApplication());
    }

    /**
     * This method is used to choose line color from colors.xml.
     * @param color   Integer parameter which identify the chosen color
     */
    public void setColor(int color) {
        this.color = parseColor(getColorById(activity, color));
    }

    /**
     * This method is used to set chart data.
     * @param dataList   List parameter with data
     */
    public void setDataList(List<Weight> dataList) {
        this.dataList = dataList;
    }

    /**
     * This method sets chart limits
     * @param limits    Limit parameter with minimum and maximum values for X and Y axis
     */
    public void setLimits(Limit limits) {
        this.limits = limits;
    }

    /**
     * This method sets line chart main attributes: <br>
     * - Lines<br>
     * - Axis<br>
     * - Interaction<br>
     */
    public void setChart() {
        setLine();
        setXAxis();
        setYAxis();
        setInteraction();
    }

    /**
     * This method sets line attribute: <br>
     *  - Point values<br>
     *  - Line color<br>
     *  - Shape for points values<br>
     *  - Labels for points values<br>
     *  - Labels decimal format<br>
     *  - Labels Text size<br>
     */
    private void setLine() {
        Line line = new Line(getPointValues());
        line.setColor(color);
        line.setShape(CIRCLE);
        line.setHasLabelsOnlyForSelected(true);
        line.setFormatter(new SimpleLineChartValueFormatter(1));

        List<Line> lines = new ArrayList<>();
        lines.add(line);

        lineChartData = new LineChartData();
        lineChartData.setValueLabelTextSize(24);
        lineChartData.setLines(lines);
    }

    /**
     * This method creates XY coordinates for each point value.
     * @return Point values list
     */
    private List<PointValue> getPointValues() {
        List<PointValue> pointValues = new ArrayList<>();
        float xValue;
        float yValue;
        for (int i=0; i<dataList.size(); i++){
            xValue = limits.getPeriodInDaysFromStartTo(dataList.get(i).getDate());
            yValue = dataList.get(i).getValue(appPreferences.isUnitSystemImperial());
            pointValues.add(new PointValue(xValue, yValue));
        }

        return pointValues;
    }

    /**
     * This method sets X axis attribute: <br>
     *  - Labels value<br>
     *  - Labels orientation<br>
     *  - Labels text color<br>
     *  - Labels text size<br>
     *  - Labels quantity<br>
     *  - X axis division lines<br>
     *  - X axis location
     */
    private void setXAxis() {
        Axis axisX = new Axis();
        axisX.setValues(getAxisXValuesFrom());
        axisX.setHasTiltedLabels(true);
        axisX.setTextColor(GRAY);
        axisX.setTextSize(10);
        axisX.setMaxLabelChars(8);
        axisX.setHasLines(true);
        lineChartData.setAxisXBottom(axisX);
    }

    /**
     * This method creates X axis labels
     * @return X axis label list
     */
    private List<AxisValue> getAxisXValuesFrom() {
        List<AxisValue> axisXValues = new ArrayList<>();
        long period = limits.getPeriodInDaysFromStartToEnd();
        LocalDate date = parse(limits.getMinX(), ISO_LOCAL_DATE);
        for (int i = 0; i <= period; i++) {
            axisXValues.add(new AxisValue(i).setLabel(date.toString()));
            date = date.plusDays(1);
        }
        return axisXValues;
    }

    /**
     * This method sets Y axis attribute: <br>
     *  - Labels text size<br>
     *  - Labels decimal format<br>
     *  - Y axis location
     */
    private void setYAxis() {
        Axis axisY = new Axis();
        axisY.setTextSize(10);
        axisY.setFormatter(new SimpleAxisValueFormatter(1));
        lineChartData.setAxisYLeft(axisY);
    }

    /**
     * This method sets interaction attribute: <br>
     *  - Zoom<br>
     *  - Scroll<br>
     *  - Line<br>
     *  - Viewport<br>
     */
    private void setInteraction() {
        lineChart.setZoomType(ZoomType.HORIZONTAL);
        lineChart.setContainerScrollEnabled(true, HORIZONTAL);
        lineChart.setLineChartData(lineChartData);
        lineChart.setViewportCalculationEnabled(false);
        boolean isUnitSystemImperial = appPreferences.isUnitSystemImperial();
        float left = 0f;
        float maxY = limits.getMaxY(isUnitSystemImperial);
        float top =  maxY * 1.1f;
        float right = limits.getPeriodInDaysFromStartToEnd() + 1f;
        float minY =  limits.getMinY(isUnitSystemImperial);
        float bottom =  minY * 0.9f;
        Viewport viewport = new Viewport(left, top, right, bottom);
        lineChart.setMaxZoom(viewport.right * 0.35f);
        lineChart.setMaximumViewport(viewport);
        lineChart.setCurrentViewport(viewport);
    }

}
