/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JFXChartGenerators;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.Chart;
import javafx.scene.chart.XYChart;
import javax.swing.JPanel;

/**
 *
 * @author root
 */
public abstract class AbstractChartGenerator {

    protected List<Map<Integer, Integer>> FXSeriesMaps;
    protected List<String> FXSeriesMapTitles;

    protected String ChartTite;
    protected String xAxisTitle;
    protected String yAxisTitle;
    
    protected Chart chart;
    protected final JFXPanel chartFxPanel;

    protected List<XYChart.Series> fxSeries;

    //<editor-fold defaultstate="collapsed" desc="Init, getters/setters">
    public AbstractChartGenerator() {
        this.chartFxPanel = new JFXPanel();
    }

    public void lineChartSetUpPanel(JPanel panelToEmbedFXObject) {
        panelToEmbedFXObject.add(this.chartFxPanel, BorderLayout.CENTER);
    }

    public void setUpSeries(Map<Integer, Integer>... FXSeries) {
        this.fxSeries = new ArrayList<>(FXSeries.length);
        this.FXSeriesMaps = new ArrayList<>(FXSeries.length);

        for (Map<Integer, Integer> map : FXSeries) {
            this.FXSeriesMaps.add(new TreeMap<>(map));
        }
    }

    public void setUpSeries(List<Map<Integer, Integer>> FXSeries) {
        this.fxSeries = new ArrayList<>(FXSeries.size());
        this.FXSeriesMaps = FXSeries;
    }

    public void setSeriesTitles(String... Titles) {
        this.FXSeriesMapTitles = new ArrayList<>(Arrays.asList(Titles));
    }

    public void setSeriesTitles(List<String> Titles) {
        this.FXSeriesMapTitles = Titles;
    }

    public void setChartTitle(String chartTite) {
        this.ChartTite = chartTite;
    }

    public void setXAxisTitle(String xAxisTitle) {
        this.xAxisTitle = xAxisTitle;
    }

    public void setYAxisTitle(String yAxisTitle) {
        this.yAxisTitle = yAxisTitle;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Abstract BarChart Creation">
    protected abstract Chart createCustomChart();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Scene Creator">
    private synchronized void createScene() {
        try {
            chart = createCustomChart();
            chartFxPanel.setScene(new Scene(chart));
        } catch (Exception e) {
        }
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
}
