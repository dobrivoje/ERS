/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JFXChartGenerators.Lines;

import java.util.Map;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 *
 * @author root
 */
public class LineChartGenerator extends AbstractMonthLineChartGenerator {

    @Override
    protected LineChart createCustomChart() {
        // Obavezno generiši onoliko podeljaka na X osi 
        // koliko ih ima KAKSIMALNO u seriji,a to je ovde 31.
        // Ako treba dinaički da se menja pogledaj ispod kako 
        // da postaviš !
        final NumberAxis xAxis = new NumberAxis(1, 31, 1);
        final NumberAxis yAxis = new NumberAxis();

        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        lineChart.setCreateSymbols(false);
        lineChart.setTitle(ChartTite);

        xAxis.setLabel(xAxisTitle);
        xAxis.setTickMarkVisible(false);
        xAxis.setMinorTickCount(0);
        xAxis.setTickLength(xAxis.getTickLength());

        yAxis.setLabel(yAxisTitle);
        yAxis.setTickMarkVisible(false);
        yAxis.setMinorTickCount(0);
        yAxis.setTickLength(yAxis.getTickLength());
        yAxis.setTickUnit(5);

        int i = 0;
        XYChart.Series sTmp;

        for (Map<Integer, Integer> s : FXSeriesMaps) {
            sTmp = new XYChart.Series<>();
            sTmp.setName(FXSeriesMapTitles.get(i++));

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
}
