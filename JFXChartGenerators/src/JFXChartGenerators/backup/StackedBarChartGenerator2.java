/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JFXChartGenerators.backup;

import java.awt.BorderLayout;
import java.util.Arrays;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javax.swing.JPanel;

/**
 *
 * @author root
 */
public class StackedBarChartGenerator2 {

    private static final String[] zemlje = new String[]{"Austria", "Brazil", "France", "Italy", "USA"};
    //
    private Chart chart;
    private final JFXPanel barChartFxPanel;
    //
    private static final XYChart.Series serije[] = new XYChart.Series[3];

    public StackedBarChartGenerator2() {
        this.barChartFxPanel = new JFXPanel();
    }

    public void barChartSetUpPanel(JPanel panelToEmbedFXObject) {
        panelToEmbedFXObject.add(barChartFxPanel, BorderLayout.CENTER);
    }

    //<editor-fold defaultstate="collapsed" desc="BarChart Creation">
    private StackedBarChart createStackedBarChart() {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        final StackedBarChart<String, Number> sbc = new StackedBarChart<>(xAxis, yAxis);

        xAxis.setLabel("ZEMLJA.");
        xAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(zemlje)));
        xAxis.setTickLabelRotation(90);
        yAxis.setLabel("VREDNOST");

        int god = 2003;

        for (int i = 0; i < serije.length; i++) {
            serije[i] = new XYChart.Series();
            serije[i].setName(Integer.toString(god++));

            for (String zemlja : zemlje) {
                serije[i].getData().add(new XYChart.Data(zemlja, 25000 * Math.random()));
            }

            sbc.getData().add(serije[i]);
        }

        return sbc;
    }
    //</editor-fold>

    private void createScene() {
        try {
            chart = createStackedBarChart();
            barChartFxPanel.setScene(new Scene(chart));
        } catch (Exception e) {
        }
    }

    public synchronized void createFXObject() {
        Platform.setImplicitExit(false);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                createScene();
            }
        });
    }
}
