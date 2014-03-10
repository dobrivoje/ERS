/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JFXChartGenerators;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;

/**
 *
 * @author root
 */
public class StackedBarChartGenerator extends AbstractChartGenerator {

    private static final String[] zemlje = new String[]{"Austria", "Brazil", "France", "Italy", "USA"};

    @Override
    protected StackedBarChart createCustomChart() {
        fxSeries = new ArrayList<>(3);

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        final StackedBarChart<String, Number> sbc = new StackedBarChart<>(xAxis, yAxis);

        xAxis.setLabel("ZEMLJA.");
        xAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(zemlje)));
        xAxis.setTickLabelRotation(90);
        yAxis.setLabel("VREDNOST");

        int god = 2003;

        for (XYChart.Series s : fxSeries) {
            s = new XYChart.Series<>();
            s.setName(Integer.toString(god++));

            for (String zemlja : zemlje) {
                s.getData().add(new XYChart.Data(zemlja, 25000 * Math.random()));
            }

            sbc.getData().add(s);
        }

        return sbc;
    }

}
