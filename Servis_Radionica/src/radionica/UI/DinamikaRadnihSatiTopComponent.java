/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radionica.UI;

import static ERS.queries.ERSQuery.UKDnevnaFakturisanost;
import static ERS.queries.ERSQuery.UKSati;
import INFSYS.Adapt.Kategorije;
import INFSYS.Queries.INFSistemQuery;
import JFXChartGenerators.Lines.AbstractMonthLineGenerator;
import JFXChartGenerators.CssStyles.CSSStyles;
import JFXChartGenerators.Lines.LineGenerator;
import JFXChartGenerators.StackedBars.AbstractStackedBarGenerator;
import JFXChartGenerators.StackedBars.StackedBarCategoryGenerator;
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
    private int prethGod, prethMesec;

    private Lookup.Result<String> kalendarLookup;
    private LookupListener llKalendar;

    private final AbstractMonthLineGenerator lcgRN = new LineGenerator();
    private final AbstractMonthLineGenerator lcgRNKretanje = new LineGenerator();
    private final AbstractMonthLineGenerator lcgRNKretanjePreth = new LineGenerator();

    private final AbstractStackedBarGenerator bCSSavetnici1 = new StackedBarCategoryGenerator();
    private final AbstractStackedBarGenerator bCSSavetnici2 = new StackedBarCategoryGenerator();

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
            // PAZI NA MESEC POČINJE OD NULE !!!
            mesec = 1 + calendar.get(Calendar.MONTH);

            prethGod = (mesec == 1 ? god - 1 : god);
            prethMesec = (mesec == 1 ? 12 : mesec - 1);

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

        // Print funkcionalnost
        putClientProperty("print.printable", Boolean.TRUE);

        setKalendarDatum(null);

        lcgRN.lineChartSetUpPanel(jPanel_UP);
        lcgRN.setCSSStyle(CSSStyles.Style.GREEN_LINE);

        lcgRNKretanje.lineChartSetUpPanel(jPanel_MIDDLE_LEFT);
        lcgRNKretanje.setCSSStyle(CSSStyles.Style.GREEN_LINE);

        lcgRNKretanjePreth.lineChartSetUpPanel(jPanel_MIDDLE_RIGHT);
        lcgRNKretanjePreth.setCSSStyle(CSSStyles.Style.YELLOW_LINE);

        bCSSavetnici1.lineChartSetUpPanel(jPanel_DOWN_LEFT);
        bCSSavetnici1.setCSSStyle(CSSStyles.Style.RED_BAR);

        bCSSavetnici2.lineChartSetUpPanel(jPanel_DOWN_RIGHT);
        bCSSavetnici2.setCSSStyle(CSSStyles.Style.RED_BAR);

        setFX_DinamikaFA_TekIPreth(god, mesec, lcgRN);

        setFX_DinamikaFA(god, mesec, lcgRNKretanje);
        setFX_DinamikaFA(prethGod, prethMesec, lcgRNKretanjePreth);

        setFX_FA_Mesec_SSavetnici_Performanse(god, mesec, bCSSavetnici1);
        setFX_FA_Mesec_SSavetnici_Performanse(prethGod, prethMesec, bCSSavetnici2);
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
        jPanel_DOWN_LEFT = new javax.swing.JPanel();
        jPanel_DOWN_RIGHT = new javax.swing.JPanel();

        jPanel_MIDDLE_LEFT.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel_MIDDLE_LEFT.setVerifyInputWhenFocusTarget(false);
        jPanel_MIDDLE_LEFT.setLayout(new java.awt.BorderLayout());

        jPanel_MIDDLE_RIGHT.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel_MIDDLE_RIGHT.setVerifyInputWhenFocusTarget(false);
        jPanel_MIDDLE_RIGHT.setLayout(new java.awt.BorderLayout());

        jPanel_UP.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel_UP.setLayout(new java.awt.BorderLayout());

        jPanel_DOWN_LEFT.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel_DOWN_LEFT.setLayout(new java.awt.BorderLayout());

        jPanel_DOWN_RIGHT.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel_DOWN_RIGHT.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel_UP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel_DOWN_LEFT, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel_MIDDLE_LEFT, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE))
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel_MIDDLE_RIGHT, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
                            .addComponent(jPanel_DOWN_RIGHT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel_DOWN_LEFT, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                    .addComponent(jPanel_DOWN_RIGHT, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel_DOWN_LEFT;
    private javax.swing.JPanel jPanel_DOWN_RIGHT;
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
                            setFX_DinamikaFA(god, mesec, lcgRNKretanjePreth);
                            setFX_FA_Mesec_SSavetnici_Performanse(god, mesec, bCSSavetnici2);
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

    //<editor-fold defaultstate="collapsed" desc="setFX_Dinamika...">
    private void setFX_DinamikaFA(int Godina, int Mesec, AbstractMonthLineGenerator lcg) {
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

    /*
     private void setFX_DinamikaFA_Preth(int Godina, int Mesec, AbstractMonthLineGenerator lcg) {
     int m = (Mesec == 1 ? 12 : Mesec - 1);
     int g = (Mesec == 1 ? Godina - 1 : Godina);
    
     setFX_DinamikaFA(g, m, lcg);
     }
     */
    private void setFX_DinamikaFA_TekIPreth(int Godina, int Mesec, AbstractMonthLineGenerator lcg) {

        String tekMesGod = SrpskiKalendar.getMesecNazivLatinica(Mesec) + " " + String.valueOf(Godina);
        String tekUkSati = String.valueOf(",  Ukupno " + UKSati(Godina, Mesec)) + " sati.";

        String prethMesGod = SrpskiKalendar.getMesecNazivLatinica(prethMesec) + " " + String.valueOf(prethGod);
        String prethUkSati = String.valueOf(",  Ukupno " + UKSati(prethGod, prethMesec)) + " sati.";

        lcg.setYAxisTitle("Fakturisano (h)");
        try {
            lcg.setUpSeries(
                    UKDnevnaFakturisanost(Godina, Mesec),
                    UKDnevnaFakturisanost(prethGod, prethMesec)
            );

            lcg.setChartTitle("Dinamika Radnih Sati");
            lcg.setSeriesTitles((tekMesGod + tekUkSati), (prethMesGod + prethUkSati));
            lcg.createFXObject();
        } catch (NullPointerException ex) {
            Display.obavestenjeBaloncic("Greška.", ex.getLocalizedMessage(), Display.TIP_OBAVESTENJA.GRESKA);
        }
    }

    private void setFX_FA_Mesec_SSavetnici_Performanse(int Godina, int Mesec, AbstractStackedBarGenerator asbg) {

        try {
            asbg.setUpSeries(INFSistemQuery.Mesec_Svi_SSavetnici_Performanse_Serije_Cat(Godina, Mesec, Kategorije.ServisniSavetnik.IDINFSYSTEM));

            asbg.setChartTitle("Učešće Servisnih Savetnika," + SrpskiKalendar.getMesecNazivLatinica(Mesec) + " " + String.valueOf(Godina));
            asbg.setSeriesTitles("Radovi", "Materijal");
            asbg.createFXObject();

        } catch (NullPointerException ex) {
            Display.obavestenjeBaloncic("Greška.", ex.getLocalizedMessage(), Display.TIP_OBAVESTENJA.GRESKA);
        } catch (Exception e) {
            Display.obavestenjeBaloncic("Greška.", e.toString(), Display.TIP_OBAVESTENJA.GRESKA);
        }
    }
    //</editor-fold>
}
