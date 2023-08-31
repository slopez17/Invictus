package co.com.uma.mseei.invictus.model.chart;

import static android.graphics.Color.GRAY;
import static android.graphics.Color.parseColor;
import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static co.com.uma.mseei.invictus.util.Resource.getColorById;
import static lecho.lib.hellocharts.gesture.ContainerScrollType.HORIZONTAL;
import static lecho.lib.hellocharts.model.ValueShape.CIRCLE;

import android.app.Activity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import co.com.uma.mseei.invictus.model.AppPreferences;
import co.com.uma.mseei.invictus.model.database.Limit;
import co.com.uma.mseei.invictus.model.database.Sport;
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
public abstract class LineChart  {

    private final Activity activity;
    protected final boolean isUnitSystemImperial;
    private final LineChartView lineChartView;
    private LineChartData lineChartData;
    protected List<Object> dataList;
    protected Limit limits;
    private int color;

    public LineChart(Activity activity, LineChartView lineChartView) {
        this.activity = activity;
        this.lineChartView = lineChartView;
        this.isUnitSystemImperial = new AppPreferences(activity.getApplication()).isUnitSystemImperial();
    }

    /**
     * Customizes the line color of the line chart, using a color from colors.xml.
     * @param color   Integer which identifies the chosen color.
     */
    public void setColor(int color) {
        this.color = parseColor(getColorById(activity, color));
    }

    /**
     * Sets up the data for its visual representation in the line chart.
     * @param dataList   List with data.
     */
    public void setDataList(List<Object> dataList) {
        this.dataList = dataList;
    }

    /**
     * Configures line chart limits.
     * @param limits    Argument with minimum and maximum values for X and Y axis.
     */
    public void setLimits(Limit limits) {
        this.limits = limits;
    }

    /**
     * Defines line chart main attributes: <br>
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
     * @return List of point values with XY coordinates.
     */
    protected abstract List<PointValue> getPointValues();

    /**
     * Adjusts line attributes: <br>
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
     * Adjusts X axis attributes: <br>
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
     * @return list with X axis labels.
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
     * Adjusts Y axis attributes: <br>
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
     * Adjusts interaction attributes: <br>
     *  - Zoom<br>
     *  - Scroll<br>
     *  - Line<br>
     *  - Viewport<br>
     */
    private void setInteraction() {
        lineChartView.setZoomType(ZoomType.HORIZONTAL);
        lineChartView.setContainerScrollEnabled(true, HORIZONTAL);
        lineChartView.setLineChartData(lineChartData);
        lineChartView.setViewportCalculationEnabled(false);
        float left = 0f;
        float maxY = limits.getMaxY();
        float top =  maxY * 1.1f;
        float right = limits.getPeriodInDaysFromStartToEnd() + 1f;
        float minY =  limits.getMinY();
        float bottom =  minY * 0.9f;
        Viewport viewport = new Viewport(left, top, right, bottom);
        lineChartView.setMaxZoom(viewport.right * 0.35f);
        lineChartView.setMaximumViewport(viewport);
        lineChartView.setCurrentViewport(viewport);
    }


}
