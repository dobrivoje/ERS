/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JFXChartGenerators.StackedBars;

import JFXChartGenerators.AbstractBASEChartGenerator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author root
 */
public abstract class AbstractStackedBarGenerator
        extends AbstractBASEChartGenerator<Integer, Integer> {

    protected Set<String> categories = new TreeSet<>();

    @Override
    public void setUpSeries(List<Map<Integer, Integer>> FXSeries) {
        super.setUpSeries(FXSeries);

        Set<Integer> sTmp = new TreeSet<>(FXSeriesMaps.get(0).keySet());

        for (Integer k : sTmp) {
            categories.add(k < 10 ? "0" + k.toString() : k.toString());
        }
    }
}
