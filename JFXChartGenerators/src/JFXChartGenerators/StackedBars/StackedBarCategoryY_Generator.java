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
public class StackedBarCategoryY_Generator extends AbstractCategory_StackedBarGenerator {

    @Override
    protected StackedBarChart createCustomChart() {
        final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();

        final StackedBarChart<Number, String> stackedBarChart = new StackedBarChart<>(xAxis, yAxis);

        stackedBarChart.setTitle(ChartTite);

        xAxis.setLabel(xAxisTitle);
        xAxis.setTickMarkVisible(true);
        xAxis.setMinorTickCount(0);
        xAxis.setTickLabelRotation(45);

        yAxis.setLabel(yAxisTitle);
        yAxis.setTickMarkVisible(false);
        yAxis.setCategories(FXCollections.<String>observableArrayList(categories));

        int i = 0;
        XYChart.Series sTmp;

        for (Map<String, Integer> s : FXSeriesMaps) {
            sTmp = new XYChart.Series<>();
            sTmp.setName(FXSeriesMapTitles.get(i++));

            for (Map.Entry<String, Integer> e : s.entrySet()) {
                sTmp.getData().add(new XYChart.Data(e.getValue(), e.getKey()));
            }

            stackedBarChart.getData().add(sTmp);
        }

        return stackedBarChart;
    }
}
