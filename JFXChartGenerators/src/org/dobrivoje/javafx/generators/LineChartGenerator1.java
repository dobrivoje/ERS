/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dobrivoje.javafx.generators;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.List;
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

    private static List<Integer> kljucevi;
    private static List<Long> vrednosti;
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

        for (int i = 0; i < kljucevi.size(); i++) {
            serija.getData().add(new XYChart.Data(kljucevi.get(i), vrednosti.get(i)));
        }
        
        kljucevi.clear();
        vrednosti.clear();

        lineChart.getData().add(serija);
        
        return lineChart;
    }

    //<editor-fold defaultstate="collapsed" desc="getters/setters">
    public List<Long> getVrednosti() {
        return vrednosti;
    }

    public void setVrednosti(List<Long> vrednosti) {
        this.vrednosti = vrednosti;
    }

    public String getLineChartTite() {
        return lineChartTite;
    }

    public void setLineChartTite(String lineChartTite) {
        this.lineChartTite = lineChartTite;
    }

    public String getxOsaNaslov() {
        return xOsaNaslov;
    }

    public void setxOsaNaslov(String xOsaNaslov) {
        this.xOsaNaslov = xOsaNaslov;
    }

    public String getyOsaNaslov() {
        return yOsaNaslov;
    }

    public void setyOsaNaslov(String yOsaNaslov) {
        this.yOsaNaslov = yOsaNaslov;
    }

    public List getRadniNalozi() {
        return vrednosti;
    }

    public void setRadniNalozi(List radniNalozi) {
        this.vrednosti = radniNalozi;
    }

    public String getSerijaNaslov() {
        return serijaNaslov;
    }

    public void setSerijaNaslov(String serijaNaslov) {
        this.serijaNaslov = serijaNaslov;
    }

    public List<Integer> getKljucevi() {
        return kljucevi;
    }

    public void setKljucevi(List<Integer> kljucevi) {
        this.kljucevi = kljucevi;
    }
    //</editor-fold>

}
