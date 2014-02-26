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
public class LineChartGenerator3 {

    private static final LineChartGenerator3 instance = new LineChartGenerator3();
    private static final Map<Integer, Integer> rd = new HashMap<>();
    private static final String[] kategorije = new String[]{"RN", "FA", "ST.Faktura"};

    private static Chart chart;
    private static final CategoryAxis xAxis = new CategoryAxis();
    private static final NumberAxis yAxis = new NumberAxis();
    private static final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
    private final JFXPanel lineChartFxPanel;
    //
    private static final XYChart.Series serije[] = new XYChart.Series[kategorije.length];

    //<editor-fold defaultstate="collapsed" desc="init">
    private LineChartGenerator3() {
        this.lineChartFxPanel = new JFXPanel();
    }

    public static LineChartGenerator3 getDefault() {
        return instance;
    }

    public void lineChartSetUpPanel(JPanel panelToEmbedFXObject) {
        panelToEmbedFXObject.add(lineChartFxPanel, BorderLayout.CENTER);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="BarChart Creation">
    private LineChart createLineChart() {
        lineChart.setCreateSymbols(false);

        lineChart.setTitle("Dinamika Naloga");
        xAxis.setLabel("Dani u Mesecu");
        xAxis.setTickLabelRotation(45);
        yAxis.setLabel("Br. Naloga");

        for (int i = 0; i < serije.length; i++) {
            serije[i] = new XYChart.Series();
            serije[i].setName(kategorije[i]);

            for (int kk = 0; kk < 30; kk++) {
                serije[i].getData().add(new XYChart.Data(kk, Math.random() * 25000));
            }

            lineChart.getData().add(serije[i]);
        }

        return lineChart;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Scene Creator">
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
//</editor-fold>

}
