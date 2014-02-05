/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dobrivoje.utilities.datumi;

import Exceptions.PomesaniDatumiException;
import java.util.Date;

/**
 *
 * @author dobri
 */
public class SingleDatumSelektor extends DatumSelektor {

    private static SingleDatumSelektor instanceSDS;

    private SingleDatumSelektor() {
        super();
    }

    public static SingleDatumSelektor getDafault() {
        if (instanceSDS == null) {
            instanceSDS = new SingleDatumSelektor();
            instanceSDS.initialize();
        }

        return instanceSDS;
    }

    public static SingleDatumSelektor getDafaultForMonthAndYear(int year, int month) {
        if (instanceSDS == null) {
            instanceSDS = new SingleDatumSelektor();
            instanceSDS.initializeMonthAndYear(year, month);
        }

        return instanceSDS;
    }

    public synchronized void setDatum(Date datum) throws NullPointerException, PomesaniDatumiException {
        super.setDatumOD(datum);
        super.setDatumDO(datum);
    }

    public synchronized void setDatum(String datum) throws NullPointerException, PomesaniDatumiException {
        super.setDatumOD(datum);
        super.setDatumDO(datum);
    }

    public Date getDatum() {
        return getDatumOD() != null ? getDatumOD() : getDatumDO();
    }

    public String getYMDDatum() {
        return getYMDDatumOD() != null ? getYMDDatumOD() : getYMDDatumDO();
    }

    public int getGodina() {
        return getGodinaOD() > 0 ? getGodinaOD() : getDanDO();
    }

    public int getMesec() {
        return getMesecOD() > 0 ? getMesecOD() : getMesecDO();
    }

    public int getDan() {
        return getDanOD() > 0 ? getDanOD() : getDanDO();
    }
}
