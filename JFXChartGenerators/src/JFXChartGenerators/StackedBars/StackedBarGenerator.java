/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JFXChartGenerators.StackedBars;

import java.util.Map;
import javafx.collections.FXCollections;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;

/**
 *
 * @author root
 */
public class StackedBarGenerator extends AbstractStackedBarGenerator {

    @Override
    protected StackedBarChart createCustomChart() {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        final StackedBarChart<String, Number> stackedBarChart = new StackedBarChart<>(xAxis, yAxis);

        stackedBarChart.setTitle(ChartTite);

        xAxis.setLabel(xAxisTitle);
        xAxis.setTickMarkVisible(false);
        xAxis.setTickLength(xAxis.getTickLength());

        xAxis.setCategories(FXCollections.<String>observableArrayList(categories));

        yAxis.setLabel(yAxisTitle);
        yAxis.setTickMarkVisible(false);
        yAxis.setMinorTickCount(0);
        yAxis.setTickLength(yAxis.getTickLength());
        yAxis.setTickUnit(5);

        int i = 0;
        XYChart.Series sTmp;

        for (Map<Integer, Integer> s : FXSeriesMaps) {
            sTmp = new XYChart.Series<>();
            sTmp.setName(FXSeriesMapTitles.get(i++));

            for (Map.Entry<Integer, Integer> e : s.entrySet()) {
                sTmp.getData().add(new XYChart.Data(e.getKey() < 10 ? "0" + e.getKey().toString() : e.getKey().toString(), e.getValue()));
            }

            stackedBarChart.getData().add(sTmp);
        }

        return stackedBarChart;
    }
}
