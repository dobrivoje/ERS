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
    private static final Map<Integer, Integer> rd = new HashMap<>();

    private static final String[] zemlje = new String[]{"Austria", "brazil", "France", "Italy", "USA"};
    //
    private Chart chart;
    private static final CategoryAxis xAxis = new CategoryAxis();
    private static final NumberAxis yAxis = new NumberAxis();
    private static final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
    private final JFXPanel lineChartFxPanel;
    //
    private static final XYChart.Series serije[] = new XYChart.Series[3];

    private LineChartGenerator2() {
        this.lineChartFxPanel = new JFXPanel();
    }

    public static LineChartGenerator2 getDefault() {
        return instance;
    }

    public void lineChartSetUpPanel(JPanel panelToEmbedFXObject) {
        panelToEmbedFXObject.add(lineChartFxPanel, BorderLayout.CENTER);
    }

    //<editor-fold defaultstate="collapsed" desc="BarChart Creation">
    private LineChart createLineChart() {
        lineChart.setCreateSymbols(false);

        lineChart.setTitle("Izveštaj za Zemlje - " + zemlje.toString());
        xAxis.setLabel("Zemlja");
        xAxis.setTickLabelRotation(90);
        yAxis.setLabel("Vrednosti");

        int god = 2003;

        for (int i = 0; i < serije.length; i++) {
            serije[i] = new XYChart.Series();
            serije[i].setName(Integer.toString(god++));

            for (String zemlja : zemlje) {
                serije[i].getData().add(new XYChart.Data(zemlja, Math.random() * 25000));
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

    public JFXPanel getLineChartFxPanel() {
        return lineChartFxPanel;
    }
}
