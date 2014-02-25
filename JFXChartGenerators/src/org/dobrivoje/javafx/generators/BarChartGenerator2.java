/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dobrivoje.javafx.generators;

import java.awt.BorderLayout;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javax.swing.JPanel;

/**
 *
 * @author root
 */
public class BarChartGenerator2 {

    // Definicije kategorija - Ovo ćemo dinamički uzeti iz ERS baze.
    private static final String[] zemlje = new String[]{"Austria", "brazil", "France", "Italy", "USA"};
    //
    private Chart chart;
    private final JFXPanel barChartFxPanel;
    //
    private static final XYChart.Series serije[] = new XYChart.Series[3];

    public BarChartGenerator2() {
        this.barChartFxPanel = new JFXPanel();
    }

    public void barChartSetUpPanel(JPanel panelToEmbedFXObject) {
        panelToEmbedFXObject.add(barChartFxPanel, BorderLayout.CENTER);
    }

    //<editor-fold defaultstate="collapsed" desc="BarChart Creation">
    private BarChart createBarChart() {
        final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();
        final BarChart<Number, String> bc = new BarChart<>(xAxis, yAxis);

        bc.setTitle("Country Summary");
        xAxis.setLabel("Value");
        xAxis.setTickLabelRotation(90);
        yAxis.setLabel("Country");

        int god = 2003;

        for (int i = 0; i < serije.length; i++) {
            serije[i] = new XYChart.Series();
            serije[i].setName(Integer.toString(god++));
            
            for (String zemlja : zemlje) {
                serije[i].getData().add(new XYChart.Data(Math.random() * 25000, zemlja));
            }
            
            bc.getData().add(serije[i]);
        }

        return bc;
    }
    //</editor-fold>

    private void createScene() {
        chart = createBarChart();
        barChartFxPanel.setScene(new Scene(chart));
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
}
