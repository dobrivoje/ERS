/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JFXChartGenerators.Lines;

import JFXChartGenerators.AbstractBASEChartGenerator;
import java.util.Map;

/**
 *
 * @author root
 */
public abstract class AbstractMonthLineGenerator
        extends AbstractBASEChartGenerator<Integer, Integer> {

    private int Year;
    private int Month;

    //<editor-fold defaultstate="collapsed" desc="getters/setters">
    public int getYear() {
        return Year;
    }

    public void setYear(int Year) {
        this.Year = Year;
    }

    public int getMonth() {
        return Month;
    }

    public void setMonth(int Month) {
        this.Month = Month;
    }
    //</editor-fold>

    protected synchronized void setYearAndMonth(int Year, int Month) {
        this.Year = Year;
        this.Month = Month;
    }

    @Override
    protected int getFXSeriesMapsMaxXAxis() {
        for (Map<Integer, Integer> s : FXSeriesMaps) {
            FXSeriesMapsMaxXAxis = Math.max(FXSeriesMapsMaxXAxis, s.entrySet().size());
        }

        return FXSeriesMapsMaxXAxis;
    }
}
