/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dobrivoje.utilities.datumi;

import Exceptions.PomesaniDatumiException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author root
 */
public class DatumSelektor {

    private static DatumSelektor instance;
    //
    private Date d_OD = null;
    private Date d_DO = null;
    //
    //
    private int godina_OD;
    private int mesec_OD;
    private int dan_OD;
    //
    private int godina_DO;
    private int mesec_DO;
    private int dan_DO;
    //
    private String datumOD;
    private String datumDO;
    //
    private final Calendar calendar = Calendar.getInstance();

    protected synchronized void initialize() {
        if (d_OD == null) {
            d_OD = new Date();
        }
        if (d_DO == null) {
            d_DO = new Date();
        }
    }

    private void initDateStringRepresentations() {
        try {
            datumOD = new SimpleDateFormat("yyyy-MM-dd").format(d_OD);
            datumDO = new SimpleDateFormat("yyyy-MM-dd").format(d_DO);
        } catch (NullPointerException e1) {
        }
    }

    protected synchronized void initializeSupplements() {
        calendar.setTime(d_OD);

        godina_OD = calendar.get(Calendar.YEAR);
        mesec_OD = calendar.get(Calendar.MONTH);
        dan_OD = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.setTime(d_DO);

        godina_DO = calendar.get(Calendar.YEAR);
        mesec_DO = calendar.get(Calendar.MONTH);
        dan_DO = calendar.get(Calendar.DAY_OF_MONTH);
    }

    protected synchronized void initializeMonthAndYear(int year, int month) {
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        d_OD = cal.getTime();

        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        d_DO = cal.getTime();

        calendar.setTime(d_OD);

        godina_OD = calendar.get(Calendar.YEAR);
        mesec_OD = calendar.get(Calendar.MONTH);
        dan_OD = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);

        calendar.setTime(d_DO);

        godina_DO = calendar.get(Calendar.YEAR);
        mesec_DO = calendar.get(Calendar.MONTH);
        dan_DO = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        initDateStringRepresentations();
    }

    protected synchronized void checkDates() throws PomesaniDatumiException {
        initialize();
        initializeSupplements();

        // ne može prosto d_OD.after(d_DO) jer se onda radi upoređenje U MILISEKUNDAMA
        // i dva ista datuma nikada ne mogu biti ista !!!

        String dod_str = new SimpleDateFormat("yyyy-MM-dd").format(d_OD);
        String ddo_str = new SimpleDateFormat("yyyy-MM-dd").format(d_DO);

        Date dod = null;
        Date ddo = null;

        try {
            dod = new SimpleDateFormat("yyyy-MM-dd").parse(dod_str);
            ddo = new SimpleDateFormat("yyyy-MM-dd").parse(ddo_str);
        } catch (ParseException ex) {
        }

        if (dod.after(ddo) || ddo.before(dod)) {
            throw new PomesaniDatumiException();
        }
    }

    protected DatumSelektor() {
    }

    public static DatumSelektor getDafault() {
        if (instance == null) {
            instance = new DatumSelektor();
            instance.initialize();
        }

        return instance;
    }

    public static DatumSelektor getDafaultForMonthAndYear(int year, int month) {
        if (instance == null) {
            instance = new DatumSelektor();
            instance.initializeMonthAndYear(year, month);
        }

        return instance;
    }

    public synchronized Date getDatumOD() {
        return d_OD;
    }

    public synchronized Date getDatumDO() {
        return d_DO;
    }

    public synchronized void setDatumOD(Date datumOD)
            throws NullPointerException, PomesaniDatumiException {

        if (datumOD != null) {
            this.d_OD = datumOD;
            checkDates();
        } else {
            throw new NullPointerException();
        }
    }

    public synchronized void setDatumDO(Date datumDO)
            throws NullPointerException, PomesaniDatumiException {
        if (datumDO != null) {
            this.d_DO = datumDO;
            checkDates();

        } else {
            throw new NullPointerException();
        }
    }

    public synchronized void setDatumOD(String datumOD)
            throws NullPointerException, PomesaniDatumiException {
        if (datumOD.length() == 0) {
            throw new NullPointerException();
        } else {
            try {
                this.d_OD = new SimpleDateFormat("yyyy-MM-dd").parse(datumOD);
            } catch (ParseException ex) {

                checkDates();
            }
        }
    }

    public synchronized void setDatumDO(String datumDO)
            throws PomesaniDatumiException {
        if (datumDO.length() == 0) {
            throw new NullPointerException();
        } else {
            try {
                this.d_DO = new SimpleDateFormat("yyyy-MM-dd").parse(datumDO);
            } catch (ParseException ex) {
            }
        }

        checkDates();
    }

    public String getYMDDatumOD() {
        try {
            return (new SimpleDateFormat("yyyy-MM-dd")).format(d_OD);
        } catch (NullPointerException e) {
            return "";
        }
    }

    public String getYMDDatumDO() {
        try {
            return (new SimpleDateFormat("yyyy-MM-dd")).format(d_DO);
        } catch (NullPointerException e) {
            return "";
        }
    }

    //<editor-fold defaultstate="collapsed" desc="getters/setters">
    public int getGodinaOD() {
        return godina_OD;
    }

    public int getGodinaDO() {
        return godina_DO;
    }

    public int getMesecOD() {
        // mesec počinje od nule, pa ga treba povećati za jedan !
        return mesec_OD + 1;
    }

    public int getMesecDO() {
        // mesec počinje od nule, pa ga treba povećati za jedan !
        return mesec_DO + 1;
    }

    public int getDanOD() {
        return dan_OD;
    }

    public int getDanDO() {
        return dan_DO;
    }

    public String getDatumStringOD() {
        return datumOD;
    }

    public String getDatumStringDO() {
        return datumDO;
    }
    //</editor-fold>
}
