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
public class StackedBarCategoryX_Generator extends AbstractCategory_StackedBarGenerator {

    @Override
    protected StackedBarChart createCustomChart() {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        final StackedBarChart<String, Number> stackedBarChart = new StackedBarChart<>(xAxis, yAxis);

        stackedBarChart.setTitle(ChartTite);

        xAxis.setLabel(xAxisTitle);
        xAxis.setTickMarkVisible(false);
        xAxis.setCategories(FXCollections.<String>observableArrayList(categories));

        yAxis.setLabel(yAxisTitle);
        yAxis.setTickMarkVisible(true);
        yAxis.setMinorTickCount(6);

        int i = 0;
        XYChart.Series sTmp;

        for (Map<String, Integer> s : FXSeriesMaps) {
            sTmp = new XYChart.Series<>();
            sTmp.setName(FXSeriesMapTitles.get(i++));

            for (Map.Entry<String, Integer> e : s.entrySet()) {
                sTmp.getData().add(new XYChart.Data(e.getKey(), e.getValue()));
            }

            stackedBarChart.getData().add(sTmp);
        }

        return stackedBarChart;
    }
}
