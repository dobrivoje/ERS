/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JFXChartGenerators.StackedBars;

import JFXChartGenerators.AbstractBASEChartGenerator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author root
 */
public abstract class AbstractStackedBarChartGenerator
        extends AbstractBASEChartGenerator<String, Integer> {

    protected Set<String> categories;

    @Override
    public void setUpSeries(List<Map<String, Integer>> FXSeries) {
        super.setUpSeries(FXSeries);
        this.categories = FXSeriesMaps.get(0).keySet();
    }
}
