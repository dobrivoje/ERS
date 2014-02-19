/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dobrivoje.javafx.generators;

import java.awt.BorderLayout;
import java.util.List;
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

    private List<Integer> kljucevi;
    private List<Long> vrednosti;
    //
    private String serijaNaslov;
    private String lineChartTite;
    private String xOsaNaslov;
    private String yOsaNaslov;
    //
    private Chart chart;
    private final JFXPanel lineChartFxPanel;
    //
    private final NumberAxis xAxis = new NumberAxis();
    private final NumberAxis yAxis = new NumberAxis();
    private final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

    //<editor-fold defaultstate="collapsed" desc="...">
    public LineChartGenerator1() {
        this.lineChartFxPanel = new JFXPanel();
    }

    public void lineChartSetUpPanel(JPanel panelToEmbedFXObject) {
        panelToEmbedFXObject.add(lineChartFxPanel, BorderLayout.CENTER);
    }

    private void createScene() {
        chart = createLineChart();
        lineChartFxPanel.setScene(new Scene(chart));
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
        xAxis.setLabel(xOsaNaslov);
        yAxis.setLabel(yOsaNaslov);

        lineChart.setCreateSymbols(false);

        xAxis.setTickUnit(1);
        xAxis.setLowerBound(1);
        xAxis.setUpperBound(31);

        yAxis.setTickUnit(5);

        XYChart.Series serija1 = new XYChart.Series();
        serija1.setName(serijaNaslov);

        for (int i = 0; i < kljucevi.size(); i++) {
            serija1.getData().add(new XYChart.Data(kljucevi.get(i), vrednosti.get(i)));
        }

        lineChart.getData().add(serija1);

        return lineChart;
    }

    private LineChart createLineChart_Interval() {
        lineChart.setTitle(lineChartTite);
        xAxis.setLabel(xOsaNaslov);
        yAxis.setLabel(yOsaNaslov);

        xAxis.setLowerBound(1);
        xAxis.setUpperBound(31);
        xAxis.setTickUnit(1);

        XYChart.Series serija1 = new XYChart.Series();
        serija1.setName(serijaNaslov);

        for (int i = 0; i < kljucevi.size(); i++) {
            serija1.getData().add(new XYChart.Data(kljucevi.get(i), vrednosti.get(i)));
        }

        lineChart.getData().add(serija1);

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
