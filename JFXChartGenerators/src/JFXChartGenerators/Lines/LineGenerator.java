/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JFXChartGenerators.Lines;

import java.util.Map;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 *
 * @author root
 */
public class LineGenerator extends AbstractMonthLineGenerator {

    @Override
    protected LineChart createCustomChart() {
        final NumberAxis xAxis = new NumberAxis(1, getFXSeriesMaps_MaxXAxis(), 1);
        final NumberAxis yAxis = new NumberAxis();

        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        lineChart.setCreateSymbols(false);
        lineChart.setTitle(ChartTite);

        xAxis.setLabel(xAxisTitle);
        xAxis.setTickMarkVisible(false);
        xAxis.setMinorTickCount(0);

        yAxis.setLabel(yAxisTitle);
        yAxis.setTickMarkVisible(false);
        yAxis.setMinorTickCount(0);
        yAxis.setTickUnit(5);

        int i = 0;
        XYChart.Series sTmp;

        for (Map<Integer, Integer> s : FXSeriesMaps) {

            sTmp = new XYChart.Series<>();
            sTmp.setName(FXSeriesMapTitles.get(i++));

            for (Map.Entry<Integer, Integer> e : s.entrySet()) {
                sTmp.getData().add(new XYChart.Data(e.getKey(), e.getValue()));
            }

            lineChart.getData().add(sTmp);
        }

        return lineChart;
    }

    private void paint(int serija, String color) {
        for (Node n : chart.lookupAll(".default-color" + Integer.toString(serija) + ".chart-series-line")) {
            n.setStyle("-fx-stroke: #" + color + ";");
        }
        
        for (Node n : chart.lookupAll(".default-color" + Integer.toString(serija) + ".chart-line-symbol")) {
            n.setStyle("-fx-background-color: #" + color + ", white;");
        }
    }

    @Override
    protected void createScene() {
        super.createScene();
        paint(0, "11FF33");
        paint(1, "FF33AA");
    }
}
