/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package izvestaji.resursi.generatori;

import ERS.queries.ERSQuery;
import Exceptions.ReportGeneratorException;
import com.dobrivoje.utilities.warnings.Display;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author dobri
 */
public class ReportGenerator {

    public static final String JASPER_EFIKASNOST_SERVISA = "izvestaji/servis/EfikasnostServisa3.jasper";
    public static final String JASPER_EFIKASNOST_SERVISA_ELMEH = "izvestaji/servis/EfikasnostServisa_LimMeh.jasper";
    public static final String JASPER_MAJSTORI_CLOCKING = "izvestaji/servis/RadnikClocking.jasper";
    public static final String JASPER_MAJSTORI_ANALIZAVREMENA = "izvestaji/servis/AnalizaVremena3.jasper";
    public static final String JASPER_SAVETNICI = "izvestaji/jasperreports/prodatisati/savetnici/Svi_Savetnici_ProdatiSati_Za_Period.jasper";
    public static final String JASPER_FIRMA_POSLOVANJE = "izvestaji/jasperreports/poslovanje/report1.jasper";
    public static final String JASPER_FIRMA_TOPKLIJENTI = "izvestaji/jasperreports/promet/TopKlijenti_Najveci_Promet.jasper";
    public static final String JASPER_ORGJED_EFIKASNOST = "izvestaji/jasperreports/orgjed/orgjed_efektivnost.jasper";
    //
    public static final String JASPER_BARKOD_RADNICI = "izvestaji/servis/barkodovi/BarKodoviRadnika.jasper";
    public static final String JASPER_BARKOD_STATUSI = "izvestaji/servis/barkodovi/BarKodoviStatusi.jasper";
    //
    private final Map<String, Object> jHashMap;
    private final List<String> listParamNames;
    private final List<Object> listParamObjects;
    private InputStream is;
    //
    private static EntityManager entityManager = null;

    public ReportGenerator() throws Exception {
        jHashMap = new HashMap<>();
        listParamNames = new ArrayList<>();
        listParamObjects = new ArrayList<>();

        if (entityManager == null) {
            entityManager = ERSQuery.getEm();
        }
    }

    //<editor-fold defaultstate="collapsed" desc="setup parameters">
    public void setUpJParamNames(String... jKeys) {
        listParamNames.addAll(Arrays.asList(jKeys));
    }

    public void setUpJParamObjects(Object... jValues) {
        listParamObjects.addAll(Arrays.asList(jValues));
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="init">
    public void init() throws ReportGeneratorException {
        if (listParamNames.size() != listParamObjects.size() || listParamNames == null || listParamObjects == null) {
            throw new ReportGeneratorException();
        } else {
            for (int i = 0; i < listParamNames.size(); i++) {
                jHashMap.put(listParamNames.get(i), listParamObjects.get(i));
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="generateReport">
    public void generateReport() {

        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }

        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport(is, jHashMap, entityManager.unwrap(java.sql.Connection.class));
            JasperViewer.setDefaultLookAndFeelDecorated(true);

            JasperViewer.viewReport(jasperPrint, false);
            entityManager.getTransaction().commit();
        } catch (NullPointerException | JRException e1) {
            Display.obavestenjeBaloncic("Greška.", e1.getMessage(), Display.TIP_OBAVESTENJA.GRESKA);

            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        } catch (IllegalStateException e3) {
            entityManager.getTransaction().rollback();
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Definicije Izveštaja.">
    public void generisanjeIzvestaja_SavetniciFinansijskiIzvestaj(String DatumOD, String DatumDO)
            throws Exception {

        is = this.getClass().getClassLoader().getResourceAsStream(JASPER_SAVETNICI);

        setUpJParamNames("DatumOD", "DatumDO");
        setUpJParamObjects(DatumOD, DatumDO);
        init();
        generateReport();
    }

    public void generisanjeIzvestaja_Firma_TopKlijenti(int GodinaOD, int GodinaDO, int BrojKlijenata)
            throws Exception {

        is = this.getClass().getClassLoader().getResourceAsStream(JASPER_FIRMA_TOPKLIJENTI);

        setUpJParamNames("GodinaOD", "GodinaDO", "BrojKlijenata");
        setUpJParamObjects(GodinaOD, GodinaDO, BrojKlijenata);
        init();
        generateReport();
    }

    public void generisanjeIzvestaja_Firma_Poslovanje(int GodinaOD, int GodinaDO)
            throws Exception {

        is = this.getClass().getClassLoader().getResourceAsStream(JASPER_FIRMA_POSLOVANJE);
        
        setUpJParamNames("GodinaOD", "GodinaDO");
        setUpJParamObjects(GodinaOD, GodinaDO);
        init();
        generateReport();
    }
    
    public void generisanjeIzvestaja_ClockingZaPeriod(long IDRadnik, String DatumOD, String DatumDO)
            throws Exception {

        is = this.getClass().getClassLoader().getResourceAsStream(JASPER_MAJSTORI_CLOCKING);

        setUpJParamNames("IDRadnik", "DatumOD", "DatumDO");
        setUpJParamObjects(IDRadnik, DatumOD, DatumDO);
        init();
        generateReport();
    }

    public void generisanjeIzvestaja_EfikasnostServisa(String DatumOD, String DatumDO, boolean Radnici, boolean Ostali, int IDFirma)
            throws Exception {

        is = this.getClass().getClassLoader().getResourceAsStream(JASPER_EFIKASNOST_SERVISA);

        setUpJParamNames("DatumOD", "DatumDO", "Radnici", "Ostali", "IDFirma");
        setUpJParamObjects(DatumOD, DatumDO, Radnici, Ostali, IDFirma);
        init();
        generateReport();
    }

    public void generisanjeIzvestaja_EfikasnostServisa_MehLim(String DatumOD, String DatumDO, boolean Radnici, boolean Ostali, int IDFirma, boolean Mehanika)
            throws Exception {

        is = this.getClass().getClassLoader().getResourceAsStream(JASPER_EFIKASNOST_SERVISA_ELMEH);

        setUpJParamNames("DatumOD", "DatumDO", "Radnici", "Ostali", "IDFirma", "Mehanika");
        setUpJParamObjects(DatumOD, DatumDO, Radnici, Ostali, IDFirma, Mehanika);
        init();
        generateReport();
    }

    public void generisanjeIzvestaja_AnalizaVremena(long IDRadnik, String DatumOD, String DatumDO)
            throws Exception {

        is = this.getClass().getClassLoader().getResourceAsStream(JASPER_MAJSTORI_ANALIZAVREMENA);

        setUpJParamNames("IDRadnik", "DatumOD", "DatumDO");
        setUpJParamObjects(IDRadnik, DatumOD, DatumDO);
        init();
        generateReport();
    }

    public void generisanjeIzvestaja_ORGJED(int IDOrgJed, String DatumOD, String DatumDO)
            throws Exception {

        is = this.getClass().getClassLoader().getResourceAsStream(JASPER_ORGJED_EFIKASNOST);

        setUpJParamNames("IDOrgJed", "DATUM_OD", "DATUM_DO");
        setUpJParamObjects(IDOrgJed, DatumOD, DatumDO);
        init();
        generateReport();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Bar kodovi">
    public void generisanjeIzvestaja_BarKod_Radnici(int IDFirma, boolean RadnikAktivan)
            throws Exception {

        is = this.getClass().getClassLoader().getResourceAsStream(JASPER_BARKOD_RADNICI);

        setUpJParamNames("IDFirma", "RadnikAktivan");
        setUpJParamObjects(IDFirma, RadnikAktivan);
        init();
        generateReport();
    }

    public void generisanjeIzvestaja_BarKod_Statusi(String p)
            throws Exception {

        is = this.getClass().getClassLoader().getResourceAsStream(JASPER_BARKOD_STATUSI);

        setUpJParamNames("p");
        setUpJParamObjects("p");
        init();
        generateReport();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="toString()">
    @Override
    public String toString() {
        String parameters = "";

        for (String s : listParamNames) {
            parameters += s + "->" + jHashMap.get(s) + "   ";
        }

        return parameters;
    }
    //</editor-fold>
}
