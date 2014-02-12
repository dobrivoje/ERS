/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dobrivoje.javafx.generators;

import java.awt.BorderLayout;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javax.swing.JPanel;

/**
 *
 * @author root
 */
public class BarChartGenerator2 {

    // Definicije kategorija - Ovo ćemo dinamički uzeti iz ERS baze.
    final static String austria = "Austria";
    final static String brazil = "Brazil";
    final static String france = "France";
    final static String italy = "Italy";
    final static String usa = "USA";
    //
    private Chart chart;
    private final JFXPanel barChartFxPanel;
    //

    public BarChartGenerator2() {
        this.barChartFxPanel = new JFXPanel();
    }

    public void barChartSetUpPanel(JPanel panelToEmbedFXObject) {
        panelToEmbedFXObject.add(barChartFxPanel, BorderLayout.CENTER);
    }

    //<editor-fold defaultstate="collapsed" desc="BarChart Creation">
    private BarChart createBarChart() {
        final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();
        final BarChart<Number, String> bc
                = new BarChart<>(xAxis, yAxis);

        bc.setTitle("Country Summary");
        xAxis.setLabel("Value");
        xAxis.setTickLabelRotation(90);
        yAxis.setLabel("Country");
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("2003");
        series1.getData().add(new XYChart.Data(25601.34, austria));
        series1.getData().add(new XYChart.Data(20148.82, brazil));
        series1.getData().add(new XYChart.Data(10000, france));
        series1.getData().add(new XYChart.Data(35407.15, italy));
        series1.getData().add(new XYChart.Data(12000, usa));
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("2004");
        series2.getData().add(new XYChart.Data(57401.85, austria));
        series2.getData().add(new XYChart.Data(41941.19, brazil));
        series2.getData().add(new XYChart.Data(45263.37, france));
        series2.getData().add(new XYChart.Data(117320.16, italy));
        series2.getData().add(new XYChart.Data(14845.27, usa));
        XYChart.Series series3 = new XYChart.Series();
        series3.setName("2005");
        series3.getData().add(new XYChart.Data(45000.65, austria));
        series3.getData().add(new XYChart.Data(44835.76, brazil));
        series3.getData().add(new XYChart.Data(18722.18, france));
        series3.getData().add(new XYChart.Data(17557.31, italy));
        series3.getData().add(new XYChart.Data(92633.68, usa));
        bc.getData().addAll(series1, series2, series3);

        return bc;
    }
    //</editor-fold>

    private void createScene() {
        chart = createBarChart();
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
