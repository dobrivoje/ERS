/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radionica.UI;

import static ERS.queries.ERSQuery.UKDnevnaFakturisanost;
import static ERS.queries.ERSQuery.UKSati;
import INFSYS.queries.INFSistemQuery;
import static INFSYS.queries.INFSistemQuery.Br_RNFA_Mesec_LineChartData;
import static INFSYS.queries.INFSistemQuery.finansijskiAspekt_GodisnjiPregled_RadMat;
import JFXChartGenerators.AbstractChartGenerator;
import JFXChartGenerators.LineChartGenerator;
import com.dobrivoje.utilities.datumi.SrpskiKalendar;
import com.dobrivoje.utilities.warnings.Display;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.StatusDisplayer;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//radionica.UI//DinamikaPoslovanja//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "DinamikaPoslovanjaTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = true, position = 1000)
@ActionID(category = "Window/Servis", id = "radionica.UI.DinamikaPoslovanjaTopComponent")
@ActionReference(path = "Menu/Window/Servis" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_DinamikaPoslovanjaAction",
        preferredID = "DinamikaPoslovanjaTopComponent"
)
@Messages({
    "CTL_DinamikaPoslovanjaAction=DinamikaPoslovanja",
    "CTL_DinamikaPoslovanjaTopComponent=DinamikaPoslovanja Window",
    "HINT_DinamikaPoslovanjaTopComponent=This is a DinamikaPoslovanja window"
})
public final class DinamikaPoslovanjaTopComponent extends TopComponent {

    private Lookup.Result<String> kalendarLookup;
    private final AbstractChartGenerator lcg1 = new LineChartGenerator();
    private final AbstractChartGenerator lcg2 = new LineChartGenerator();

    private final AbstractChartGenerator lcgDinamikaNaloga = new LineChartGenerator();
    private final AbstractChartGenerator lcgDinamikaFin = new LineChartGenerator();

    private final Calendar calendar = Calendar.getInstance();
    private int god, mesec;

    private String monitorPerformansi = "";
    private long t1;

    //<editor-fold defaultstate="collapsed" desc="Kalendar Bind">
    private String kalendarDatum;

    public static final String PROP_KALENDAR_BIND = "kalendar_bind";

    /**
     * Get the value of kalendarDatum_bind
     *
     * @return the value of kalendarDatum_bind
     */
    public String getKalendarDatum() {
        return kalendarDatum;
    }

    /**
     * Set the value of kalendarDatum_bind
     *
     * @param kalendar new value of kalendarDatum_bind
     */
    public void setKalendarDatum(String kalendar) {
        String oldKalendar_bind = this.kalendarDatum;
        this.kalendarDatum = kalendar;

        try {
            if (kalendar == null) {
                calendar.setTime(new Date());
            } else {
                calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(kalendar));
            }

            god = calendar.get(Calendar.YEAR);

            // PAZI NA MESEC POČINJE OD NULE !!!
            mesec = 1 + calendar.get(Calendar.MONTH);
        } catch (ParseException ex) {
        }

        propertyChangeSupport.firePropertyChange(PROP_KALENDAR_BIND, oldKalendar_bind, kalendar);
    }

    private transient final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    /**
     * Add PropertyChangeListener.
     *
     * @param listener
     */
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove PropertyChangeListener.
     *
     * @param listener
     */
    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
    //</editor-fold>

    public DinamikaPoslovanjaTopComponent() {
        initComponents();
        setName(Bundle.CTL_DinamikaPoslovanjaTopComponent());
        setToolTipText(Bundle.HINT_DinamikaPoslovanjaTopComponent());

        lcgDinamikaNaloga.lineChartSetUpPanel(jPanel_Kompanija_UP);
        lcgDinamikaFin.lineChartSetUpPanel(jPanel_Kompanija_DOWN);

        lcg1.lineChartSetUpPanel(jPanel_MIDDLE_LEFT);
        lcg2.lineChartSetUpPanel(jPanel_MIDDLE_RIGHT);

        setKalendarDatum(null);

        setFX_FA_DnevnoRadoviMaterijal(god, mesec, lcgDinamikaFin);
        setFX_KretanjeRN(kalendarDatum, lcgDinamikaNaloga);

        setFX_DinamikaFA(god, mesec, lcg1);
        setFX_DinamikaFA_Preth(god, mesec, lcg2);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel_MIDDLE_LEFT = new javax.swing.JPanel();
        jPanel_MIDDLE_RIGHT = new javax.swing.JPanel();
        jPanel_Kompanija_UP = new javax.swing.JPanel();
        jPanel_Kompanija_DOWN = new javax.swing.JPanel();

        jPanel_MIDDLE_LEFT.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel_MIDDLE_LEFT.setLayout(new java.awt.BorderLayout());

        jPanel_MIDDLE_RIGHT.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel_MIDDLE_RIGHT.setLayout(new java.awt.BorderLayout());

        jPanel_Kompanija_UP.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel_Kompanija_UP.setLayout(new java.awt.BorderLayout());

        jPanel_Kompanija_DOWN.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel_Kompanija_DOWN.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel_Kompanija_UP, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel_MIDDLE_LEFT, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                        .addGap(8, 8, 8)
                        .addComponent(jPanel_MIDDLE_RIGHT, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE))
                    .addComponent(jPanel_Kompanija_DOWN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel_Kompanija_UP, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel_MIDDLE_LEFT, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                    .addComponent(jPanel_MIDDLE_RIGHT, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel_Kompanija_DOWN, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel_Kompanija_DOWN;
    private javax.swing.JPanel jPanel_Kompanija_UP;
    private javax.swing.JPanel jPanel_MIDDLE_LEFT;
    private javax.swing.JPanel jPanel_MIDDLE_RIGHT;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        kalendarLookup = Utilities.actionsGlobalContext().lookupResult(String.class);
        kalendarLookup.addLookupListener(new LookupListener() {
            @Override
            public void resultChanged(LookupEvent le) {
                Lookup.Result lr = (Lookup.Result) le.getSource();
                Collection<String> datumi = lr.allInstances();

                if (!datumi.isEmpty()) {
                    for (String d1 : datumi) {
                        setKalendarDatum(d1);

                        
                        setFX_DinamikaFA(god, mesec, lcg1);
                        setFX_DinamikaFA_Preth(god, mesec, lcg2);

                        setFX_KretanjeRN(kalendarDatum, lcgDinamikaNaloga);

                        // setFX_KretanjeRN_FinAspekt(god, lcgDinamikaFin);
                        
                        setFX_FA_DnevnoRadoviMaterijal(god, mesec, lcgDinamikaFin);

                        StatusDisplayer.getDefault().setStatusText(monitorPerformansi);
                        monitorPerformansi = "";
                    }
                }
            }
        });
    }

    @Override
    public void componentClosed() {
        kalendarLookup = null;
    }

    //<editor-fold defaultstate="collapsed" desc="nb defaults">
    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
    //</editor-fold>

    private void setFX_DinamikaFA(int Godina, int Mesec, AbstractChartGenerator lcg) {
        t1 = System.currentTimeMillis();

        String tekMesGod = SrpskiKalendar.getMesecNazivLatinica(Mesec)
                + " " + String.valueOf(Godina);
        String ukSati = String.valueOf(", Ukupno " + UKSati(Godina, Mesec)) + " sati.";

        try {
            lcg.setUpSeries(UKDnevnaFakturisanost(Godina, Mesec));

            lcg.setChartTitle("Dinamika Radnih Sati Servisa");
            lcg.setSeriesTitles(tekMesGod + ukSati);
            lcg.createFXObject();
        } catch (NullPointerException ex) {
            Display.obavestenjeBaloncic("Greška.", ex.getLocalizedMessage(), Display.TIP_OBAVESTENJA.GRESKA);
        }

        monitorPerformansi += "setFX_DinamikaFA :" + Long.toString((System.currentTimeMillis() - t1)) + " ms.  ";
    }

    private void setFX_DinamikaFA_Preth(int Godina, int Mesec, AbstractChartGenerator lcg) {
        t1 = System.currentTimeMillis();

        int m = (Mesec == 1 ? 12 : Mesec - 1);
        int g = (Mesec == 1 ? Godina - 1 : Godina);

        setFX_DinamikaFA(g, m, lcg);

        monitorPerformansi += "setFX_DinamikaFA_Preth :" + Long.toString((System.currentTimeMillis() - t1)) + " ms.  ";
    }

    private void setFX_KretanjeRN(String Datum, AbstractChartGenerator lcg) {
        t1 = System.currentTimeMillis();

        try {
            lcg.setUpSeries(
                    Br_RNFA_Mesec_LineChartData(Datum, 1),
                    Br_RNFA_Mesec_LineChartData(Datum, 2),
                    Br_RNFA_Mesec_LineChartData(Datum, 3)
            );
            lcg.setChartTitle("Dinamika Rada Servisa za " + SrpskiKalendar.getMesecNazivLatinica(mesec) + " " + String.valueOf(god) + ". godine");
            lcg.setSeriesTitles("Radni Nalozi", "Fakture", "Storno Fakture");
            lcg.createFXObject();
        } catch (ParseException ex) {
            Display.obavestenjeBaloncic("Greška.", "Datum nije u pravilnoj formi.", Display.TIP_OBAVESTENJA.GRESKA);
        } catch (NullPointerException ex) {
            Display.obavestenjeBaloncic("Greška.", ex.getLocalizedMessage(), Display.TIP_OBAVESTENJA.GRESKA);
        }

        monitorPerformansi += "setFX_KretanjeRN :" + Long.toString((System.currentTimeMillis() - t1)) + " ms.  ";
    }

    private void setFX_KretanjeRN_FinAspekt(int Godina, AbstractChartGenerator lcg) {
        try {
            lcg.setUpSeries(finansijskiAspekt_GodisnjiPregled_RadMat(Godina));

            lcg.setChartTitle("Finansijska Dinamika Servisa za " + String.valueOf(Godina) + " Godinu");
            lcg.setSeriesTitles("Radovi", "Materijal");
            lcg.createFXObject();

        } catch (NullPointerException ex) {
            Display.obavestenjeBaloncic("Greška.", ex.getLocalizedMessage(), Display.TIP_OBAVESTENJA.GRESKA);
        } catch (Exception e) {
            Display.obavestenjeBaloncic("Greška.", e.toString(), Display.TIP_OBAVESTENJA.GRESKA);
        }
    }

    private void setFX_FA_DnevnoRadoviMaterijal(int Godina, int Mesec, AbstractChartGenerator lcg) {

        t1 = System.currentTimeMillis();

        try {
            lcg.setUpSeries(INFSistemQuery.Mesec_DnevnoFA_UK_Serije(Godina, Mesec));

            lcg.setChartTitle("Finansijska Dinamika Servisa za " + SrpskiKalendar.getMesecNazivLatinica(mesec) + " " + String.valueOf(god) + ". godine");
            lcg.setSeriesTitles("Radovi", "Materijal");
            lcg.createFXObject();

        } catch (NullPointerException ex) {
            Display.obavestenjeBaloncic("Greška.", ex.getLocalizedMessage(), Display.TIP_OBAVESTENJA.GRESKA);
        } catch (Exception e) {
            Display.obavestenjeBaloncic("Greška.", e.toString(), Display.TIP_OBAVESTENJA.GRESKA);
        }

        monitorPerformansi += "setFX_FA_DnevnoRadoviMaterijal :" + Long.toString((System.currentTimeMillis() - t1)) + " ms.  ";
    }
}
