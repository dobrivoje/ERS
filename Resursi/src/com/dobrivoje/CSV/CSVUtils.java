/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dobrivoje.CSV;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

/**
 *
 * @author root
 */
public class CSVUtils {

    private static CSVUtils instance;
    //
    private static String CSV_LokacijaFajla;
    //
    private static char CSV_Separator = ';';
    private static final char CSV_Param = '\'';
    private static int CSV_PreskakanjeLinija = 1;
    //
    private static final CsvToBean CVSToBean = new CsvToBean();
    private static CSVReader CVSReader;
    private static final ColumnPositionMappingStrategy cpms = new ColumnPositionMappingStrategy();
    //
    private static List<IColumnMapping> list;

    protected CSVUtils() throws FileNotFoundException {
        init();
    }

    private void init() throws FileNotFoundException {
        CVSReader = new CSVReader(
                new FileReader(CSV_LokacijaFajla),
                CSV_Separator,
                CSV_Param,
                CSV_PreskakanjeLinija);
    }

    public static CSVUtils getDafault() throws FileNotFoundException {
        return instance = (instance == null ? new CSVUtils() : instance);
    }

    //<editor-fold defaultstate="collapsed" desc="Setters">
    public static void setCSV_LokacijaFajla(String CSV_LokacijaFajla) {
        CSVUtils.CSV_LokacijaFajla = CSV_LokacijaFajla;
    }

    public static void setCSV_Separator(char CSV_Separator) {
        CSVUtils.CSV_Separator = CSV_Separator;
    }

    public static void setCSV_PreskakanjeLinija(int CSV_PreskakanjeLinija) {
        CSVUtils.CSV_PreskakanjeLinija = CSV_PreskakanjeLinija;
    }
//</editor-fold>

    public static void setUpBean(IColumnMapping bean) {
        cpms.setType(bean.getClass());
        cpms.setColumnMapping(bean.getColumnNames());

        list = CVSToBean.parse(cpms, CVSReader);
    }

    public static List<IColumnMapping> getBean() {
        return list;
    }
}
