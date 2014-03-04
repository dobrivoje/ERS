/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JFXChartGenerators;

import java.awt.BorderLayout;
import java.util.Map;
import java.util.TreeMap;
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
public class LineChartGenerator4 {

    private Map<Integer, Integer>[] SERIJA;
    private String[] SERIJA_NAZIV;
    //
    private String LineChartTite;
    private String xOsaNaslov;
    private String yOsaNaslov;
    //
    private Chart chart;
    private final JFXPanel lineChartFxPanel;
    //
    private static XYChart.Series[] serije;

    //<editor-fold defaultstate="collapsed" desc="Init, getters/setters">
    public LineChartGenerator4() {
        this.lineChartFxPanel = new JFXPanel();
    }

    public void lineChartSetUpPanel(JPanel panelToEmbedFXObject) {
        panelToEmbedFXObject.add(lineChartFxPanel, BorderLayout.CENTER);
    }

    public void setSerije(Map<Integer, Integer>... Serije) {
        serije = new XYChart.Series[Serije.length];
        SERIJA = new TreeMap[Serije.length];

        int i = 0;
        for (Map<Integer, Integer> map : Serije) {
            SERIJA[i++] = new TreeMap<>(map);
        }
    }

    public void setSerijeNazivi(String... Nazivi) {
        SERIJA_NAZIV = new String[Nazivi.length];
        System.arraycopy(Nazivi, 0, SERIJA_NAZIV, 0, Nazivi.length);
    }

    public void setLineChartTite(String LineChartTite) {
        this.LineChartTite = LineChartTite;
    }

    public void setxOsaNaslov(String xOsaNaslov) {
        this.xOsaNaslov = xOsaNaslov;
    }

    public void setyOsaNaslov(String yOsaNaslov) {
        this.yOsaNaslov = yOsaNaslov;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="BarChart Creation">
    private LineChart createLineChart() {
        final NumberAxis xAxis = new NumberAxis(1, 31, 1);
        final NumberAxis yAxis = new NumberAxis();
        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        lineChart.setCreateSymbols(false);

        lineChart.setTitle(LineChartTite);
        xAxis.setLabel(xOsaNaslov);
        xAxis.setTickMarkVisible(false);
        xAxis.setTickLength(xAxis.getTickLength());
        xAxis.setTickLabelRotation(45);

        yAxis.setLabel(yOsaNaslov);
        yAxis.setTickUnit(5);
        yAxis.setTickLength(yAxis.getTickLength());
        yAxis.setTickMarkVisible(false);

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
