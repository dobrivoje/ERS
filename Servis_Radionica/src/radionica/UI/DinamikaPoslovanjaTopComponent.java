/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radionica.UI;

import static ERS.queries.ERSQuery.UKDnevnaFakturisanost;
import static ERS.queries.ERSQuery.UKSati;
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
        preferredID = "DinamikaPoslovanjaTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = false, position = 1000)
@ActionID(category = "Window/Servis", id = "radionica.UI.DinamikaPoslovanjaTopComponent")
@ActionReference(path = "Menu/Window/Servis", position = 1000)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_DinamikaPoslovanjaAction",
        preferredID = "DinamikaPoslovanjaTopComponent"
)
@Messages({
    "CTL_DinamikaPoslovanjaAction=Dinamika Radnih Naloga",
    "CTL_DinamikaPoslovanjaTopComponent=Dinamika Radnih Naloga",
    "HINT_DinamikaPoslovanjaTopComponent=Dinamika Radnih Naloga"
})
public final class DinamikaPoslovanjaTopComponent extends TopComponent {

    private final Calendar calendar = Calendar.getInstance();
    private int god, mesec;

    private Lookup.Result<String> kalendarLookup;
    private LookupListener llKalendar;

    private final AbstractChartGenerator lcgRNKretanje = new LineChartGenerator();
    private final AbstractChartGenerator lcgRNKretanjePreth = new LineChartGenerator();
    private final AbstractChartGenerator lcgRN = new LineChartGenerator();

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

                if (calendar.getTime() == (new SimpleDateFormat("yyyy-MM-dd").parse(kalendar))) {
                    kalendarDatumIzmenjen = false;
                } else {
                    kalendarDatumIzmenjen = true;
                }
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

    public DinamikaPoslovanjaTopComponent() {
        initComponents();
        setName(Bundle.CTL_DinamikaPoslovanjaTopComponent());
        setToolTipText(Bundle.HINT_DinamikaPoslovanjaTopComponent());

        lcgRNKretanje.lineChartSetUpPanel(jPanel_DOWN_LEFT);
        lcgRNKretanje.setCSSStyle(CSSStyles.Style.RED);
        lcgRNKretanjePreth.lineChartSetUpPanel(jPanel_DOWN_RIGHT);
        lcgRNKretanjePreth.setCSSStyle(CSSStyles.Style.YELLOW);
        lcgRN.lineChartSetUpPanel(jPanel_Kompanija_UP);
        lcgRN.setCSSStyle(CSSStyles.Style.RED);

        setKalendarDatum(null);

        setFX_DinamikaFA(god, mesec, lcgRNKretanje);
        setFX_DinamikaFA_Preth(god, mesec, lcgRNKretanjePreth);
        setFX_DinamikaFA_TekIPreth(god, mesec, lcgRN);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel_DOWN_LEFT = new javax.swing.JPanel();
        jPanel_DOWN_RIGHT = new javax.swing.JPanel();
        jPanel_Kompanija_UP = new javax.swing.JPanel();

        jPanel_DOWN_LEFT.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel_DOWN_LEFT.setVerifyInputWhenFocusTarget(false);
        jPanel_DOWN_LEFT.setLayout(new java.awt.BorderLayout());

        jPanel_DOWN_RIGHT.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel_DOWN_RIGHT.setVerifyInputWhenFocusTarget(false);
        jPanel_DOWN_RIGHT.setLayout(new java.awt.BorderLayout());

        jPanel_Kompanija_UP.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel_Kompanija_UP.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel_DOWN_LEFT, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                        .addGap(8, 8, 8)
                        .addComponent(jPanel_DOWN_RIGHT, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE))
                    .addComponent(jPanel_Kompanija_UP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel_Kompanija_UP, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel_DOWN_LEFT, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel_DOWN_RIGHT, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel_DOWN_LEFT;
    private javax.swing.JPanel jPanel_DOWN_RIGHT;
    private javax.swing.JPanel jPanel_Kompanija_UP;
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
}
