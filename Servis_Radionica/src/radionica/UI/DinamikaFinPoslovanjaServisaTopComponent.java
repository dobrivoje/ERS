/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radionica.UI;

import INFSYS.Queries.INFSistemQuery;
import static INFSYS.Queries.INFSistemQuery.finansijskiAspekt_GodisnjiPregled_RadMat;
import JFXChartGenerators.AbstractChartGenerator;
import JFXChartGenerators.CSSStyles;
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
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.WindowManager;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//radionica.UI//DinamikaFinPoslovanjaServisa//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "DinamikaFinPoslovanjaServisaTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = false, position = 1100)
@ActionID(category = "Window/Servis", id = "radionica.UI.DinamikaFinPoslovanjaServisaTopComponent")
@ActionReference(path = "Menu/Window/Servis", position = 1100)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_DinamikaFinPoslovanjaServisaAction",
        preferredID = "DinamikaFinPoslovanjaServisaTopComponent"
)
@Messages({
    "CTL_DinamikaFinPoslovanjaServisaAction=Dinamika Fin. Poslovanja Servisa",
    "CTL_DinamikaFinPoslovanjaServisaTopComponent=Dinamika Fin. Poslovanja Servisa",
    "HINT_DinamikaFinPoslovanjaServisaTopComponent=Dinamika Fin. Poslovanja Servisa"
})
public final class DinamikaFinPoslovanjaServisaTopComponent extends TopComponent {

    private Lookup.Result<String> kalendarLookup;
    private LookupListener ll;

    private final Calendar calendar = Calendar.getInstance();
    private int god, mesec;

    private long t1;

    private final AbstractChartGenerator lcgDinamikaFin = new LineChartGenerator();
    private final AbstractChartGenerator lcgDinamikaFinAspekt = new LineChartGenerator();

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

    public DinamikaFinPoslovanjaServisaTopComponent() {
        initComponents();
        setName(Bundle.CTL_DinamikaFinPoslovanjaServisaTopComponent());
        setToolTipText(Bundle.HINT_DinamikaFinPoslovanjaServisaTopComponent());

        setKalendarDatum(null);

        lcgDinamikaFin.lineChartSetUpPanel(jPanel_Kompanija_UP);
        lcgDinamikaFin.setCSSStyle(CSSStyles.Style.YELLOW);
        lcgDinamikaFinAspekt.lineChartSetUpPanel(jPanel_Kompanija_DOWN);
        lcgDinamikaFinAspekt.setCSSStyle(CSSStyles.Style.YELLOW);

        setFX_FA_DnevnoRadoviMaterijal(god, mesec, lcgDinamikaFin);
        setFX_KretanjeRN_FinAspekt(god, lcgDinamikaFinAspekt);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel_Kompanija_UP = new javax.swing.JPanel();
        jPanel_Kompanija_DOWN = new javax.swing.JPanel();

        jPanel_Kompanija_UP.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel_Kompanija_UP.setLayout(new java.awt.BorderLayout());

        jPanel_Kompanija_DOWN.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel_Kompanija_DOWN.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel_Kompanija_UP, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
                    .addComponent(jPanel_Kompanija_DOWN, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(3, 3, 3))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(jPanel_Kompanija_UP, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                .addGap(3, 3, 3)
                .addComponent(jPanel_Kompanija_DOWN, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                .addGap(3, 3, 3))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel_Kompanija_DOWN;
    private javax.swing.JPanel jPanel_Kompanija_UP;
    // End of variables declaration//GEN-END:variables
//<editor-fold defaultstate="collapsed" desc="UI Lookup">
    @Override
    public void componentOpened() {
        kalendarLookup = WindowManager.getDefault()
                .findTopComponent("PretrazivacTopComponent")
                .getLookup().lookupResult(String.class);

        ll = new LookupListener() {
            @Override
            public void resultChanged(LookupEvent le) {
                Lookup.Result lr = (Lookup.Result) le.getSource();
                Collection<String> datumi = lr.allInstances();

                if (!datumi.isEmpty()) {
                    for (String d1 : datumi) {
                        setKalendarDatum(d1);

                        setFX_KretanjeRN_FinAspekt(god, lcgDinamikaFinAspekt);
                        setFX_FA_DnevnoRadoviMaterijal(god, mesec, lcgDinamikaFin);
                    }
                }
            }
        };

        kalendarLookup.addLookupListener(ll);
    }

    @Override
    public void componentClosed() {
        kalendarLookup.removeLookupListener(ll);
        kalendarLookup = null;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="...">
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
    }

    private void setFX_KretanjeRN_FinAspekt(int Godina, AbstractChartGenerator lcg) {
        try {
            lcg.setUpSeries(finansijskiAspekt_GodisnjiPregled_RadMat(Godina));

            lcg.setChartTitle("Finansijska Dinamika Servisa za " + String.valueOf(Godina) + " Godinu");
            lcg.setSeriesTitles("Radovi", "Delovi");
            lcg.createFXObject();

        } catch (NullPointerException ex) {
            Display.obavestenjeBaloncic("Greška.", ex.getLocalizedMessage(), Display.TIP_OBAVESTENJA.GRESKA);
        } catch (Exception e) {
            Display.obavestenjeBaloncic("Greška.", e.toString(), Display.TIP_OBAVESTENJA.GRESKA);
        }
    }
}
