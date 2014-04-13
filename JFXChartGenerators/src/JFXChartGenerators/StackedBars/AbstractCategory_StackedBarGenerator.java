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
 * Kategorija može biti na i na X i na Y osi !!!
 * FXSeriesMaps je UVEK u formi <String, Integer>
 */
public abstract class AbstractCategory_StackedBarGenerator
        extends AbstractBASEChartGenerator<String, Integer> {

    protected Set<String> categories;

    @Override
    public void setUpSeries(List<Map<String, Integer>> FXSeries) {
        super.setUpSeries(FXSeries);
        this.categories = FXSeriesMaps.get(0).keySet();
    }
}
