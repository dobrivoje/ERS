/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JFXChartGenerators.Lines;
import JFXChartGenerators.AbstractBASEChartGenerator;

import java.util.Map;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 *
 * @author root
 */
public class LineCategoryChartGenerator extends AbstractBASEChartGenerator<String, Integer> {

    @Override
    protected LineChart createCustomChart() {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);

        lineChart.setCreateSymbols(false);
        lineChart.setTitle(ChartTite);

        xAxis.setLabel(xAxisTitle);
        xAxis.setTickMarkVisible(false);
        xAxis.setTickLength(xAxis.getTickLength());

        yAxis.setLabel(yAxisTitle);
        yAxis.setTickMarkVisible(false);
        yAxis.setMinorTickCount(0);
        yAxis.setTickLength(yAxis.getTickLength());
        yAxis.setTickUnit(5);

        int i = 0;
        XYChart.Series sTmp;

        for (Map<String, Integer> s : FXSeriesMaps) {
            sTmp = new XYChart.Series<>();
            sTmp.setName(FXSeriesMapTitles.get(i++));

            for (Map.Entry<String, Integer> e : s.entrySet()) {
                sTmp.getData().add(new XYChart.Data(e.getKey(), e.getValue()));
            }

            lineChart.getData().add(sTmp);
        }

        return lineChart;
    }
}
