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
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javax.swing.JPanel;

/**
 *
 * @author root
 */
public class LineChartGenerator {

    private List<Map<Integer, Integer>> FXSerije;
    //private String[] FXSerije_Naziv;
    private List<String> FXSerije_Naziv;

    private String LineChartTite;
    private String xOsaNaslov;
    private String yOsaNaslov;
    //
    private Chart chart;
    private final JFXPanel lineChartFxPanel;

    private List<XYChart.Series> fxSerija;

    //<editor-fold defaultstate="collapsed" desc="Init, getters/setters">
    public LineChartGenerator() {
        this.lineChartFxPanel = new JFXPanel();
    }

    public void lineChartSetUpPanel(JPanel panelToEmbedFXObject) {
        panelToEmbedFXObject.add(this.lineChartFxPanel, BorderLayout.CENTER);
    }

    public void setSerije(Map<Integer, Integer>... FXSerije) {
        this.fxSerija = new ArrayList<>(FXSerije.length);
        this.FXSerije = new ArrayList<>(FXSerije.length);

        for (Map<Integer, Integer> map : FXSerije) {
            this.FXSerije.add(new TreeMap<>(map));
        }
    }

    public void setSerije(List<Map<Integer, Integer>> FXSerije) {
        this.fxSerija = new ArrayList<>(FXSerije.size());
        this.FXSerije = FXSerije;
    }

    public void setSerijeNazivi(String... Nazivi) {
        this.FXSerije_Naziv = new ArrayList<>(Arrays.asList(Nazivi));
    }

    public void setSerijeNazivi(List<String> Nazivi) {
        this.FXSerije_Naziv = Nazivi;
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
        // Obavezno generiši onoliko podeljaka na X osi 
        // koliko ih ima KAKSIMALNO u seriji,a to je ovde 31.
        // Ako treba dinaički da se menja pogledaj ispod kako 
        // da postaviš !
        final NumberAxis xAxis = new NumberAxis(1, 31, 1);
        final NumberAxis yAxis = new NumberAxis();

        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        lineChart.setCreateSymbols(false);
        lineChart.setTitle(LineChartTite);

        xAxis.setLabel(xOsaNaslov);
        xAxis.setTickMarkVisible(false);
        xAxis.setMinorTickCount(0);
        xAxis.setTickLength(xAxis.getTickLength());

        yAxis.setLabel(yOsaNaslov);
        yAxis.setTickMarkVisible(false);
        yAxis.setMinorTickCount(0);
        yAxis.setTickLength(yAxis.getTickLength());
        yAxis.setTickUnit(5);
        
        int i = 0;
        XYChart.Series sTmp;

        for (Map<Integer, Integer> s : FXSerije) {
            sTmp = new XYChart.Series<>();
            sTmp.setName(FXSerije_Naziv.get(i++));

            // OVO JE TRIK KOJI DINAMIČKI ODREĐUJE DUŽINU X-OSE !!!
            // JEEEEEEEEEEEEEEEEEEE !!!
            xAxis.setUpperBound(s.entrySet().size());

            for (Map.Entry<Integer, Integer> e : s.entrySet()) {
                sTmp.getData().add(new XYChart.Data(e.getKey(), e.getValue()));
            }

            lineChart.getData().add(sTmp);
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
