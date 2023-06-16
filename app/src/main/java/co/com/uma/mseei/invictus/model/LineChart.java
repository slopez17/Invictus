package co.com.uma.mseei.invictus.model;

import static android.graphics.Color.GRAY;
import static android.graphics.Color.parseColor;
import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static co.com.uma.mseei.invictus.model.database.Limits.WEIGHT;
import static co.com.uma.mseei.invictus.model.database.Limits.getPeriodInDaysBetween;
import static co.com.uma.mseei.invictus.util.ResourceOperations.getColorById;
import static lecho.lib.hellocharts.gesture.ContainerScrollType.HORIZONTAL;
import static lecho.lib.hellocharts.model.ValueShape.CIRCLE;

import android.app.Activity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import co.com.uma.mseei.invictus.model.database.Limits;
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
 * Data model for a line Chart. Allows you to assign color, data, limits and view to show.
 */
public class LineChart  {

    private final Activity activity;
    private final AppPreferences appPreferences;
    private int color;
    private List<Weight> dataList;
    private final LineChartView lineChart;
    private LineChartData lineChartData;
    private Limits limits;

    public LineChart(Activity activity, LineChartView lineChartView) {
        this.activity = activity;
        this.lineChart = lineChartView;
        this.appPreferences = new AppPreferences(activity);
    }

    /**
     * Define a line color for line chart
     *
     * @param color   Color id to search for in colors.xml
     *
     */
    public void setColor(int color) {
        this.color = parseColor(getColorById(activity, color));
    }

    /**
     * Define a data to display in line chart
     *
     * @param dataList   List with data
     *
     */
    public void setDataList(List<Weight> dataList) {
        this.dataList = dataList;
    }

    /**
     * Define limits in line chart
     * @param limits    X and Y axis limits (min, max)

     */
    public void setLimits(Limits limits) {
        this.limits = limits;
    }

    /**
     * Configures main attributes for line chart<br>
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
     * Define line properties for line chart: <br>
     *
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
     * Create an array with coordinates XY for every point value in line chart
     */
    private List<PointValue> getPointValues() {
        List<PointValue> pointValues = new ArrayList<>();
        String startDate = limits.getMinX();
        float xValue;
        float yValue;
        for (int i=0; i<dataList.size(); i++){
            xValue = getPeriodInDaysBetween(startDate, dataList.get(i).getDate());
            yValue = dataList.get(i).getValue(appPreferences.isUnitSystemImperial());
            pointValues.add(new PointValue(xValue, yValue));
        }

        return pointValues;
    }

    /**
     * Define X axis properties for line chart: <br>
     *
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
     * Create an array with labels for X axis
     */
    private List<AxisValue> getAxisXValuesFrom() {
        List<AxisValue> axisXValues = new ArrayList<>();
        long period = limits.getPeriodInDays();
        LocalDate date = parse(limits.getMinX(), ISO_LOCAL_DATE);
        for (int i = 0; i <= period; i++) {
            axisXValues.add(new AxisValue(i).setLabel(date.toString()));
            date = date.plusDays(1);
        }
        return axisXValues;
    }

    /**
     * Define Y axis properties for line chart: <br>
     *
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
     * Define interaction properties for line chart: <br>
     *
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
        float maxY = limits.getMaxY(WEIGHT, isUnitSystemImperial);
        float top =  maxY * 1.1f;
        float right = limits.getPeriodInDays() + 1f;
        float minY =  limits.getMinY(WEIGHT, isUnitSystemImperial);
        float bottom =  minY * 0.9f;
        Viewport viewport = new Viewport(left, top, right, bottom);
        lineChart.setMaxZoom(viewport.right * 0.35f);
        lineChart.setMaximumViewport(viewport);
        lineChart.setCurrentViewport(viewport);
    }

}
