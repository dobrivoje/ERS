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
public class LineChartGenerator3 {

    private static final LineChartGenerator3 instance = new LineChartGenerator3();
    //
    private Map<Integer, Integer>[] SERIJA;
    private String[] SERIJA_NAZIV;

    private static Chart chart;
    private final JFXPanel lineChartFxPanel;
    //
    private static XYChart.Series[] serije;

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

    public void setSerije(Map<Integer, Integer>... Serije) {
        serije = new XYChart.Series[Serije.length];
        
        SERIJA = new HashMap[Serije.length];
        System.arraycopy(Serije, 0, SERIJA, 0, Serije.length);
    }

    public void setSerijeNazivi(String... Nazivi) {
        SERIJA_NAZIV = new String[Nazivi.length];
        System.arraycopy(Nazivi, 0, SERIJA_NAZIV, 0, Nazivi.length);
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="BarChart Creation">
    private LineChart createLineChart() {
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        lineChart.setCreateSymbols(false);

        lineChart.setTitle("Dinamika Naloga");
        xAxis.setLabel("Dani u Mesecu");
        xAxis.setTickLabelRotation(45);
        yAxis.setLabel("Br. Naloga");

        for (int i = 0; i < SERIJA.length; i++) {
            serije[i] = new XYChart.Series<>();
            serije[i].setName(SERIJA_NAZIV[i]);

            for (Map.Entry<Integer, Integer> e : SERIJA[i].entrySet()) {
                serije[i].getData().add(new XYChart.Data(e.getKey(), e.getValue()));
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
