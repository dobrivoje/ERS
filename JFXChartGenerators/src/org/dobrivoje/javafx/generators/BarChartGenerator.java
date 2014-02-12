/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dobrivoje.javafx.generators;

import java.awt.BorderLayout;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javax.swing.JPanel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import org.dobrivoje.tablemodels.SampleTableModel;
import org.dobrivoje.tablemodels.StockTraderUtilities;

/**
 *
 * @author root
 */
public class BarChartGenerator {

    private Chart chart;
    private final JFXPanel barChartFxPanel;
    //
    private final SampleTableModel tableModel;

    public SampleTableModel getTableModel() {
        return tableModel;
    }

    public BarChartGenerator() {
        this.tableModel = StockTraderUtilities.getSampleTableModel();
        this.barChartFxPanel = new JFXPanel();
    }

    public void barChartSetUpPanel(JPanel panelToEmbedFXObject) {
        panelToEmbedFXObject.add(barChartFxPanel, BorderLayout.CENTER);
    }

    //<editor-fold defaultstate="collapsed" desc="BarChart Kreiranje sa modelom tabele - SampleTableModel">
    private BarChart createBarChart() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setCategories(FXCollections.<String>observableArrayList(tableModel.getColumnNames()));
        xAxis.setLabel("Year");

        double tickUnit = tableModel.getTickUnit();

        NumberAxis yAxis = new NumberAxis();
        yAxis.setTickUnit(tickUnit);
        yAxis.setLabel("Units Sold");

        final BarChart chartLocal = new BarChart(xAxis, yAxis, tableModel.getBarChartData());
        tableModel.addTableModelListener(new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    final int row = e.getFirstRow();
                    final int column = e.getColumn();
                    final Object value = ((SampleTableModel) e.getSource()).getValueAt(row, column);

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            XYChart.Series<String, Number> s = (XYChart.Series<String, Number>) chartLocal.getData().get(row);
                            BarChart.Data data = s.getData().get(column);
                            data.setYValue(value);
                        }
                    });
                }
            }
        });

        return chartLocal;
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
