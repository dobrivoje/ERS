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
import java.util.Calendar;
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
public abstract class AbstractBASEChartGenerator<T1, T2> {

    private static final Calendar c = Calendar.getInstance();
    private static int lastDayOfMonth;

    protected Scene scene;
    protected CSSStyles.Style CSSStyle;

    protected List<Map<T1, T2>> FXSeriesMaps;
    // FXSeriesMapsMaxXAxis : Ako imamo više serija, na X osi se može dogoditi da se prikaže
    // manje podataka u npr. drugoj nego u prvoj seriji ! Zato moramo obezbediti da se prikaže
    // maksimum !
    protected int FXSeriesMapsMaxXAxis;
    protected List<String> FXSeriesMapTitles;

    protected String ChartTite;
    protected String xAxisTitle;
    protected String yAxisTitle;

    protected Chart chart;
    protected final JFXPanel chartFxPanel;

    protected List<XYChart.Series> fxSeries;

    protected int getFXSeriesMapsMaxXAxis() {
        for (Map<T1, T2> s : FXSeriesMaps) {
            // Ako ima više serija, moramo uzeti max vrednost na X osi, da bi se prikazale sve vrednosti !
            FXSeriesMapsMaxXAxis = Math.max(FXSeriesMapsMaxXAxis, s.entrySet().size());
        }
        return FXSeriesMapsMaxXAxis;
    }

    public static int getLastDayOfMonth(int year, int month) {
        c.set(year, month - 1, 1);
        lastDayOfMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);

        return lastDayOfMonth;
    }

    //<editor-fold defaultstate="collapsed" desc="Init, getters/setters">
    public AbstractBASEChartGenerator() {
        this.chartFxPanel = new JFXPanel();
        this.CSSStyle = CSSStyles.Style.DEFAULT_LINE;
    }

    public void setCSSStyle(CSSStyles.Style CSSStyle) {
        this.CSSStyle = CSSStyle;
    }

    public void setUpChartPanel(JPanel panelToEmbedFXObject) {
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
