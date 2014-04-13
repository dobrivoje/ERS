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
        
        // Obavezno se mora očistiti, inače će ostati maks. broj kategorija
        // na X osi (npr. mesec je februar, i umesto 28 dana, pojavljuje se 31...
        categories.clear();
        
        // Pošto je kategorija String tipa, onda se sortiranje TreeMap-om radi nad stringovima
        // tako da redosled ide od 1,10,11,12,2,..9 tako da se mora obezbediti sortiranje na način ispod !
        for (Integer k : sTmp) {
            categories.add(k < 10 ? "0" + k.toString() : k.toString());
        }
    }
}
