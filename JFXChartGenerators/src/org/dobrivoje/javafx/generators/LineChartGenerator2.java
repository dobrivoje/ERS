/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dobrivoje.javafx.generators;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javax.swing.JPanel;

/**
 *
 * @author root
 */
public class LineChartGenerator2 {

    private static final LineChartGenerator2 instance = new LineChartGenerator2();
    private static final String[] zemlje = new String[]{"Austria", "brazil", "France", "Italy", "USA"};
    //
    private Chart chart;
    private static final JFXPanel lineChartFxPanel = new JFXPanel();
    //
    private static final XYChart.Series serije[] = new XYChart.Series[3];

    private LineChartGenerator2() {
    }

    public static LineChartGenerator2 getDefault() {
        return instance;
    }

    public void lineChartSetUpPanel(JPanel panelToEmbedFXObject) {
        panelToEmbedFXObject.add(lineChartFxPanel, BorderLayout.CENTER);
    }

    //<editor-fold defaultstate="collapsed" desc="BarChart Creation">
    private synchronized LineChart createLineChart() {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setCreateSymbols(false);

        lineChart.setTitle("Izveštaj za Države");
        xAxis.setLabel("Države");
        xAxis.setTickLabelRotation(90);
        yAxis.setLabel("Dug");

        int god = 2003;

        for (int i = 0; i < serije.length; i++) {
            serije[i] = new XYChart.Series();
            serije[i].setName(Integer.toString(god++));

            for (String zemlja : zemlje) {
                serije[i].getData().add(new XYChart.Data(zemlja, Math.random() * 2000));
            }

            lineChart.getData().add(serije[i]);
        }

        return lineChart;
    }
    //</editor-fold>

    private void createScene() {
        try {
            chart = createLineChart();
            lineChartFxPanel.setScene(new Scene(chart));
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
