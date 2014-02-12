/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dobrivoje.javafx.generators;

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

    final static String austria = "Austria";
    final static String brazil = "Brazil";
    final static String france = "France";
    final static String italy = "Italy";
    final static String usa = "USA";
    //
    private Chart chart;
    private final JFXPanel barChartFxPanel;
    //

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
        
        final XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        final XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        final XYChart.Series<String, Number> series3 = new XYChart.Series<>();

        xAxis.setLabel("ZEMLJA.");
        xAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(austria, brazil, france, italy, usa)));
        xAxis.setTickLabelRotation(90);
        
        yAxis.setLabel("VREDNOST");
        series1.setName("2003");
        series1.getData().add(new XYChart.Data<String, Number>(austria, 25601.34));
        series1.getData().add(new XYChart.Data<String, Number>(brazil, 20148.82));
        series1.getData().add(new XYChart.Data<String, Number>(france, 10000));
        series1.getData().add(new XYChart.Data<String, Number>(italy, 35407.15));
        series1.getData().add(new XYChart.Data<String, Number>(usa, 12000));
        series2.setName("2004");
        series2.getData().add(new XYChart.Data<String, Number>(austria, 57401.85));
        series2.getData().add(new XYChart.Data<String, Number>(brazil, 41941.19));
        series2.getData().add(new XYChart.Data<String, Number>(france, 45263.37));
        series2.getData().add(new XYChart.Data<String, Number>(italy, 117320.16));
        series2.getData().add(new XYChart.Data<String, Number>(usa, 14845.27));
        series3.setName("2005");
        series3.getData().add(new XYChart.Data<String, Number>(austria, 45000.65));
        series3.getData().add(new XYChart.Data<String, Number>(brazil, 44835.76));
        series3.getData().add(new XYChart.Data<String, Number>(france, 18722.18));
        series3.getData().add(new XYChart.Data<String, Number>(italy, 17557.31));
        series3.getData().add(new XYChart.Data<String, Number>(usa, 92633.68));
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
