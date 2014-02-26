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
import javafx.scene.chart.Chart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javax.swing.JPanel;

/**
 *
 * @author root
 */
public class LineChartGenerator1 {

    private static final LineChartGenerator1 instance = new LineChartGenerator1();

    private static Map<Integer, Integer> rd = new HashMap<>();
    private static final Map<Integer, Long> map = new HashMap<>();
    //
    private static String serijaNaslov;
    private static String lineChartTite;
    private static String xOsaNaslov;
    private static String yOsaNaslov;
    //
    private static Chart chart;
    private static Scene scene;
    private static final XYChart.Series serija = new XYChart.Series();
    private static final JFXPanel lineChartFxPanel = new JFXPanel();
    //
    private final NumberAxis xAxis;
    private final NumberAxis yAxis;
    private final LineChart<Number, Number> lineChart;

    //<editor-fold defaultstate="collapsed" desc="...">
    private LineChartGenerator1() {
        xAxis = new NumberAxis();
        yAxis = new NumberAxis();
        lineChart = new LineChart<>(xAxis, yAxis);
    }

    public static LineChartGenerator1 getDefault() {
        return instance;
    }

    public void lineChartSetUpPanel(JPanel panelToEmbedFXObject) {
        panelToEmbedFXObject.add(lineChartFxPanel, BorderLayout.CENTER);
    }

    private void createScene() {
        chart = createLineChart();
        scene = (scene == null ? new Scene(chart) : scene);

        scene.setRoot(chart);
        lineChartFxPanel.setScene(scene);
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
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="BarChart Creation">
    private LineChart createLineChart() {
        lineChart.setTitle(lineChartTite);
        lineChart.setCreateSymbols(false);

        xAxis.setLabel(xOsaNaslov);
        yAxis.setLabel(yOsaNaslov);

        xAxis.setTickUnit(1);
        xAxis.setLowerBound(1);
        xAxis.setUpperBound(31);

        yAxis.setTickUnit(5);

        serija.setName(serijaNaslov);

        for (Map.Entry<Integer, Integer> entry : rd.entrySet()) {
            serija.getData().add(new XYChart.Data(entry.getKey(), entry.getKey()));
        }

        lineChart.getData().add(serija);

        return lineChart;
    }

    //<editor-fold defaultstate="collapsed" desc="getters/setters">
    
    public void setRd(Map<Integer, Integer> rd) {
        LineChartGenerator1.rd = rd;
    }
    
    public String getLineChartTite() {
        return lineChartTite;
    }

    public void setLineChartTite(String lineChartTite) {
        LineChartGenerator1.lineChartTite = lineChartTite;
    }

    public String getxOsaNaslov() {
        return xOsaNaslov;
    }

    public void setxOsaNaslov(String xOsaNaslov) {
        LineChartGenerator1.xOsaNaslov = xOsaNaslov;
    }

    public String getyOsaNaslov() {
        return yOsaNaslov;
    }

    public void setyOsaNaslov(String yOsaNaslov) {
        LineChartGenerator1.yOsaNaslov = yOsaNaslov;
    }

    public String getSerijaNaslov() {
        return serijaNaslov;
    }

    public void setSerijaNaslov(String serijaNaslov) {
        LineChartGenerator1.serijaNaslov = serijaNaslov;
    }
    //</editor-fold>

}
