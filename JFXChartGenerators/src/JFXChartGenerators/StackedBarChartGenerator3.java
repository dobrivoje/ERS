/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JFXChartGenerators;

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
public class StackedBarChartGenerator3 {

    final static String austria = "Austria";
    final static String brazil = "Brazil";
    final static String france = "France";
    final static String italy = "Italy";
    final static String usa = "USA";
    //
    private Chart chart;
    private final JFXPanel barChartFxPanel;
    //

    public StackedBarChartGenerator3() {
        this.barChartFxPanel = new JFXPanel();
    }

    public void barChartSetUpPanel(JPanel panelToEmbedFXObject) {
        panelToEmbedFXObject.add(barChartFxPanel, BorderLayout.CENTER);
    }

    //<editor-fold defaultstate="collapsed" desc="BarChart Creation">
    private StackedBarChart createStackedBarChart() {
        final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();

        final StackedBarChart<Number, String> sbc = new StackedBarChart<>(xAxis, yAxis);

        final XYChart.Series<Number, String> series1 = new XYChart.Series<>();
        final XYChart.Series<Number, String> series2 = new XYChart.Series<>();
        final XYChart.Series<Number, String> series3 = new XYChart.Series<>();

        yAxis.setLabel("ZEMLJA.");
        yAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(austria, brazil, france, italy, usa)));

        xAxis.setLabel("VREDNOST");
        series1.setName("2003");
        series1.getData().add(new XYChart.Data<Number, String>(25601.34, austria));
        series1.getData().add(new XYChart.Data<Number, String>(20148.82, brazil));
        series1.getData().add(new XYChart.Data<Number, String>(10000, france));
        series1.getData().add(new XYChart.Data<Number, String>(35407.15, italy));
        series1.getData().add(new XYChart.Data<Number, String>(12000, usa));
        series2.setName("2004");
        series2.getData().add(new XYChart.Data<Number, String>(57401.85, austria));
        series2.getData().add(new XYChart.Data<Number, String>(41941.19, brazil));
        series2.getData().add(new XYChart.Data<Number, String>(45263.37, france));
        series2.getData().add(new XYChart.Data<Number, String>(117320.16, italy));
        series2.getData().add(new XYChart.Data<Number, String>(14845.27, usa));
        series3.setName("2005");
        series3.getData().add(new XYChart.Data<Number, String>(45000.65, austria));
        series3.getData().add(new XYChart.Data<Number, String>(44835.76, brazil));
        series3.getData().add(new XYChart.Data<Number, String>(18722.18, france));
        series3.getData().add(new XYChart.Data<Number, String>(17557.31, italy));
        series3.getData().add(new XYChart.Data<Number, String>(92633.68, usa));
        sbc.getData().addAll(series1, series2, series3);

        return sbc;
    }
    //</editor-fold>

    private void createScene() {
        chart = createStackedBarChart();
        barChartFxPanel.setScene(new Scene(chart));
    }

    public void createFXObject() {
        Platform.setImplicitExit(false);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                createScene();
            }
        });
    }
}
