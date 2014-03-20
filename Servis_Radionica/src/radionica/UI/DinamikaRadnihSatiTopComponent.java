/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radionica.UI;

import static ERS.queries.ERSQuery.UKDnevnaFakturisanost;
import static ERS.queries.ERSQuery.UKSati;
import static INFSYS.Queries.INFSistemQuery.Mesec_Svi_SSavetnici_Performanse_Serije;
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
import org.openide.util.Utilities;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//radionica.UI//DinamikaPoslovanja//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "DinamikaRadnihSatiTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = false, position = 1000)
@ActionID(category = "Window/Servis", id = "radionica.UI.DinamikaRadnihSatiTopComponent")
@ActionReference(path = "Menu/Window/Servis", position = 1000)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_DinamikaPoslovanjaAction",
        preferredID = "DinamikaRadnihSatiTopComponent"
)
@Messages({
    "CTL_DinamikaPoslovanjaAction=Dinamika Radnih Sati",
    "CTL_DinamikaRadnihSatiTopComponent=Dinamika Radnih Sati",
    "HINT_DinamikaRadnihSatiTopComponent=Dinamika Radnih Sati"
})
public final class DinamikaRadnihSatiTopComponent extends TopComponent {

    private final Calendar calendar = Calendar.getInstance();
    private int god, mesec;

    private Lookup.Result<String> kalendarLookup;
    private LookupListener llKalendar;

    private final AbstractChartGenerator lcgRN = new LineChartGenerator();
    private final AbstractChartGenerator lcgRNKretanje = new LineChartGenerator();
    private final AbstractChartGenerator lcgRNKretanjePreth = new LineChartGenerator();
    private final AbstractChartGenerator lcgSSavetnici = new LineChartGenerator();

    //<editor-fold defaultstate="collapsed" desc="Kalendar Bind">
    private String kalendarDatum_bind;

    public static final String PROP_KALENDAR_BIND = "kalendar_bind";
    private boolean kalendarDatumIzmenjen;

    /**
     * Get the value of kalendarDatum_bind
     *
     * @return the value of kalendarDatum_bind
     */
    public String getKalendarDatum() {
        return kalendarDatum_bind;
    }

    /**
     * Set the value of kalendarDatum_bind
     *
     * @param kalendar new value of kalendarDatum_bind
     */
    public void setKalendarDatum(String kalendar) {
        String oldKalendar_bind = this.kalendarDatum_bind;
        this.kalendarDatum_bind = kalendar;

        try {
            if (kalendar == null) {
                calendar.setTime(new Date());
                kalendarDatumIzmenjen = true;
            } else {
                calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(kalendar));
                kalendarDatumIzmenjen = calendar.getTime() != (new SimpleDateFormat("yyyy-MM-dd").parse(kalendar));
            }

            god = calendar.get(Calendar.YEAR);
            // PAZI NA MESEC POÄŒINJE OD NULE !!!
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

    public DinamikaRadnihSatiTopComponent() {
        initComponents();
        setName(Bundle.CTL_DinamikaRadnihSatiTopComponent());
        setToolTipText(Bundle.HINT_DinamikaRadnihSatiTopComponent());

        lcgRN.lineChartSetUpPanel(jPanel_UP);
        lcgRN.setCSSStyle(CSSStyles.Style.RED);

        lcgRNKretanje.lineChartSetUpPanel(jPanel_MIDDLE_LEFT);
        lcgRNKretanje.setCSSStyle(CSSStyles.Style.RED);

        lcgRNKretanjePreth.lineChartSetUpPanel(jPanel_MIDDLE_RIGHT);
        lcgRNKretanjePreth.setCSSStyle(CSSStyles.Style.YELLOW);

        lcgSSavetnici.lineChartSetUpPanel(jPanel_DOWN);
        lcgSSavetnici.setCSSStyle(CSSStyles.Style.RED);

        setKalendarDatum(null);

        setFX_DinamikaFA_TekIPreth(god, mesec, lcgRN);

        setFX_DinamikaFA(god, mesec, lcgRNKretanje);
        setFX_DinamikaFA_Preth(god, mesec, lcgRNKretanjePreth);

        setFX_DinamikaFA_Savetnici(god, mesec, lcgSSavetnici);
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
        jPanel_UP = new javax.swing.JPanel();
        jPanel_DOWN = new javax.swing.JPanel();

        jPanel_MIDDLE_LEFT.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel_MIDDLE_LEFT.setVerifyInputWhenFocusTarget(false);
        jPanel_MIDDLE_LEFT.setLayout(new java.awt.BorderLayout());

        jPanel_MIDDLE_RIGHT.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel_MIDDLE_RIGHT.setVerifyInputWhenFocusTarget(false);
        jPanel_MIDDLE_RIGHT.setLayout(new java.awt.BorderLayout());

        jPanel_UP.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel_UP.setLayout(new java.awt.BorderLayout());

        jPanel_DOWN.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel_DOWN.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel_DOWN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel_MIDDLE_LEFT, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                        .addGap(3, 3, 3)
                        .addComponent(jPanel_MIDDLE_RIGHT, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE))
                    .addComponent(jPanel_UP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(3, 3, 3))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(jPanel_UP, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel_MIDDLE_LEFT, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel_MIDDLE_RIGHT, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addComponent(jPanel_DOWN, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel_DOWN;
    private javax.swing.JPanel jPanel_MIDDLE_LEFT;
    private javax.swing.JPanel jPanel_MIDDLE_RIGHT;
    private javax.swing.JPanel jPanel_UP;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        kalendarLookup = Utilities.actionsGlobalContext().lookupResult(String.class);
        llKalendar = new LookupListener() {
            @Override
            public void resultChanged(LookupEvent le) {
                Lookup.Result lr = (Lookup.Result) le.getSource();
                Collection<String> datumi = lr.allInstances();

                if (!datumi.isEmpty()) {
                    for (String d1 : datumi) {
                        if (kalendarDatumIzmenjen) {
                            setKalendarDatum(d1);
                            setFX_DinamikaFA_TekIPreth(god, mesec, lcgRN);

                            setFX_DinamikaFA_Savetnici(god, mesec, lcgSSavetnici);
                        }
                    }
                }
            }
        };
        kalendarLookup.addLookupListener(llKalendar);
    }

    @Override
    public void componentClosed() {
        kalendarLookup.removeLookupListener(llKalendar);
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
        String tekMesGod = SrpskiKalendar.getMesecNazivLatinica(Mesec) + " " + String.valueOf(Godina);
        String ukSati = String.valueOf(",  Ukupno " + UKSati(Godina, Mesec)) + " sati.";

        lcg.setYAxisTitle("Uk. Fakturisano (h)");

        try {
            lcg.setUpSeries(UKDnevnaFakturisanost(Godina, Mesec));

            lcg.setChartTitle("Dinamika Radnih Sati");
            lcg.setSeriesTitles(tekMesGod + ukSati);
            lcg.createFXObject();
        } catch (NullPointerException ex) {
            Display.obavestenjeBaloncic("Greška.", ex.getLocalizedMessage(), Display.TIP_OBAVESTENJA.GRESKA);
        }
    }

    private void setFX_DinamikaFA_Preth(int Godina, int Mesec, AbstractChartGenerator lcg) {
        int m = (Mesec == 1 ? 12 : Mesec - 1);
        int g = (Mesec == 1 ? Godina - 1 : Godina);

        setFX_DinamikaFA(g, m, lcg);
    }

    private void setFX_DinamikaFA_TekIPreth(int Godina, int Mesec, AbstractChartGenerator lcg) {

        int m = (Mesec == 1 ? 12 : Mesec - 1);
        int g = (Mesec == 1 ? Godina - 1 : Godina);

        String tekMesGod = SrpskiKalendar.getMesecNazivLatinica(Mesec)
                + " " + String.valueOf(Godina);
        String tekUkSati = String.valueOf(",  Ukupno " + UKSati(Godina, Mesec)) + " sati.";

        String prethMesGod = SrpskiKalendar.getMesecNazivLatinica(m)
                + " " + String.valueOf(g);
        String prethUkSati = String.valueOf(",  Ukupno " + UKSati(g, m)) + " sati.";

        lcg.setYAxisTitle("Uk. Fakturisano (h)");
        try {
            lcg.setUpSeries(
                    UKDnevnaFakturisanost(Godina, Mesec),
                    UKDnevnaFakturisanost(g, m)
            );

            lcg.setChartTitle("Dinamika Radnih Sati");
            lcg.setSeriesTitles((tekMesGod + tekUkSati), (prethMesGod + prethUkSati));
            lcg.createFXObject();
        } catch (NullPointerException ex) {
            Display.obavestenjeBaloncic("Greška.", ex.getLocalizedMessage(), Display.TIP_OBAVESTENJA.GRESKA);
        }
    }

    private void setFX_DinamikaFA_Savetnici(int Godina, int Mesec, AbstractChartGenerator lcg) {
        String tekMesGod = SrpskiKalendar.getMesecNazivLatinica(Mesec) + " " + String.valueOf(Godina);

        lcg.setYAxisTitle("Uk. Fakturisano (Din.)");

        try {
            lcg.setUpSeries(Mesec_Svi_SSavetnici_Performanse_Serije(Godina, Mesec));

            lcg.setChartTitle("Servisni Savetnici");
            lcg.setSeriesTitles("123","456");
            lcg.createFXObject();
        } catch (NullPointerException ex) {
            Display.obavestenjeBaloncic("Greška.", ex.getLocalizedMessage(), Display.TIP_OBAVESTENJA.GRESKA);
        }
    }
}
