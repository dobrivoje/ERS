/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radionica.UI;

import INFSYS.Queries.INFSistemQuery;
import JFXChartGenerators.AbstractBASEChartGenerator;
import JFXChartGenerators.CssStyles.CSSStyles;
import JFXChartGenerators.StackedBars.AbstractStackedBarChartGenerator;
import JFXChartGenerators.StackedBars.StackedBarCategoryChartGenerator;
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
        dtd = "-//radionica.UI//DinamikaServSavetnici//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "DinamikaServSavetniciTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = false, position = 1200)
@ActionID(category = "Window/Servis", id = "radionica.UI.DinamikaServSavetniciTopComponent")
@ActionReference(path = "Menu/Window/Servis", position = 1200)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_DinamikaServSavetniciAction",
        preferredID = "DinamikaServSavetniciTopComponent"
)
@Messages({
    "CTL_DinamikaServSavetniciAction=Performanse Servisnih Savetnika",
    "CTL_DinamikaServSavetniciTopComponent=Performanse Servisnih Savetnika",
    "HINT_DinamikaServSavetniciTopComponent=Performanse Servisnih Savetnika"
})
public final class DinamikaServSavetniciTopComponent extends TopComponent {

    private Lookup.Result<String> kalendarLookup;
    private LookupListener ll;

    private final Calendar calendar = Calendar.getInstance();
    private int god, mesec;

    private final AbstractStackedBarChartGenerator bCSSavetnici1 = new StackedBarCategoryChartGenerator();
    private final AbstractStackedBarChartGenerator bCSSavetnici2 = new StackedBarCategoryChartGenerator();

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

    public DinamikaServSavetniciTopComponent() {
        initComponents();
        setName(Bundle.CTL_DinamikaFinPoslovanjaServisaTopComponent());
        setToolTipText(Bundle.HINT_DinamikaFinPoslovanjaServisaTopComponent());

        setKalendarDatum(null);

        bCSSavetnici1.lineChartSetUpPanel(jPanel_Kompanija_UP);
        bCSSavetnici1.setCSSStyle(CSSStyles.Style.RED_BAR);
        bCSSavetnici2.lineChartSetUpPanel(jPanel_Kompanija_DOWN);
        bCSSavetnici2.setCSSStyle(CSSStyles.Style.RED_BAR);

        setFX_FA_Mesec_SSavetnici_Performanse(god, mesec, bCSSavetnici1);
        setFX_FA_Mesec_SSavetnici_Performanse(mesec == 1 ? god - 1 : god, mesec == 12 ? mesec - 1 : mesec, bCSSavetnici2);
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
            .addComponent(jPanel_Kompanija_UP, javax.swing.GroupLayout.DEFAULT_SIZE, 664, Short.MAX_VALUE)
            .addComponent(jPanel_Kompanija_DOWN, javax.swing.GroupLayout.DEFAULT_SIZE, 664, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel_Kompanija_UP, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                .addGap(2, 2, 2)
                .addComponent(jPanel_Kompanija_DOWN, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE))
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

                        setFX_FA_Mesec_SSavetnici_Performanse(god, mesec, bCSSavetnici2);
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

    private void setFX_FA_Mesec_SSavetnici_Performanse(int Godina, int Mesec, AbstractStackedBarChartGenerator abc) {

        try {
            abc.setUpSeries(INFSistemQuery.Mesec_Svi_SSavetnici_Performanse_Serije_Cat(Godina, Mesec));

            abc.setChartTitle("Učešće Servisnih Savetnika za " + SrpskiKalendar.getMesecNazivLatinica(mesec) + " " + String.valueOf(god) + ". godine");
            abc.setSeriesTitles("Radovi", "Materijal");
            abc.createFXObject();

        } catch (NullPointerException ex) {
            Display.obavestenjeBaloncic("Greška.", ex.getLocalizedMessage(), Display.TIP_OBAVESTENJA.GRESKA);
        } catch (Exception e) {
            Display.obavestenjeBaloncic("Greška.", e.toString(), Display.TIP_OBAVESTENJA.GRESKA);
        }
    }
}
