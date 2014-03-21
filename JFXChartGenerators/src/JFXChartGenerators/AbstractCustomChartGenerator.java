/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JFXChartGenerators;

import JFXChartGenerators.CssStyles.CSSStyles;
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
 * @param <T1> -> x Axis
 * @param <T2> -> y Axis
 */
public abstract class AbstractCustomChartGenerator<T1, T2> {

    protected Scene scene;
    protected CSSStyles.Style CSSStyle;

    protected List<Map<T1, T2>> FXSeriesMaps;
    protected List<String> FXSeriesMapTitles;

    protected String ChartTite;
    protected String xAxisTitle;
    protected String yAxisTitle;

    protected Chart chart;
    protected final JFXPanel chartFxPanel;

    protected List<XYChart.Series> fxSeries;

    //<editor-fold defaultstate="collapsed" desc="Init, getters/setters">
    public AbstractCustomChartGenerator() {
        this.chartFxPanel = new JFXPanel();
        this.CSSStyle = CSSStyles.Style.DEFAULT;
    }

    public void setCSSStyle(CSSStyles.Style CSSStyle) {
        this.CSSStyle = CSSStyle;
    }

    public void lineChartSetUpPanel(JPanel panelToEmbedFXObject) {
        panelToEmbedFXObject.add(this.chartFxPanel, BorderLayout.CENTER);
    }

    public void setUpSeries(Map<T1, T2>... FXSeries) {
        this.fxSeries = new ArrayList<>(FXSeries.length);
        this.FXSeriesMaps = new ArrayList<>(FXSeries.length);

        for (Map<T1, T2> map : FXSeries) {
            this.FXSeriesMaps.add(new TreeMap<>(map));
        }
    }

    public void setUpSeries(List<Map<T1, T2>> FXSeries) {
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
    protected void createScene() {
        /*try {
         chart = createCustomChart();
         scene = new Scene(chart);
         chartFxPanel.setScene(scene);
         } catch (Exception e) {
         }*/

        try {
            chart = createCustomChart();
            scene = new Scene(chart);

            // Do jaja !!! Dinamičko učitavanje CSS fajla smeštenog u drugom paketu-
            // Ukoliko se promeni lokacija CSSStyles.java ili CSS.* MORA SE REBUILD-ovati 
            // i JFXChartGenerators
            // i OBAVEZNO korisnik(ci) JFXChartGenerators-a tj. ovde, Servis_Radionica !!!
            scene.getStylesheets().add(
                    CSSStyles.class
                    .getResource(CSSStyles.getCSSStyle(this.CSSStyle))
                    .toExternalForm()
            );

            chartFxPanel.setScene(scene);
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
