/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radionica.UI;

import static INFSYS.Queries.INFSistemQuery.Mesec_DnevnoFA_UK_Serije;
import static INFSYS.Queries.INFSistemQuery.finansijskiAspekt_GodisnjiPregled_RadMat;
import JFXChartGenerators.CssStyles.CSSStyles;
import JFXChartGenerators.StackedBars.AbstractStackedBarGenerator;
import JFXChartGenerators.StackedBars.StackedBarGenerator;
import com.dobrivoje.utilities.datumi.SrpskiKalendar;
import com.dobrivoje.utilities.warnings.Display;
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
    private int prethGod, prethMesec;
    private boolean godIzmenjen, mesecIzmenjen;

    private final AbstractStackedBarGenerator sbFinDinamikaTekMesec = new StackedBarGenerator();
    private final AbstractStackedBarGenerator sbFinDinamikaPrethMesec = new StackedBarGenerator();
    private final AbstractStackedBarGenerator sbFinAspektTekGod = new StackedBarGenerator();

    //<editor-fold defaultstate="collapsed" desc="Kalendar Bind">
    private String kalendarDatum_bind;

    public String getKalendarDatum() {
        return kalendarDatum_bind;
    }

    public void setKalendarDatum(String kalendar) {
        this.kalendarDatum_bind = kalendar;

        try {
            calendar.setTime(
                    kalendar == null
                    ? new Date() : new SimpleDateFormat("yyyy-MM-dd").parse(kalendar));

            if (calendar.get(Calendar.YEAR) != god) {
                godIzmenjen = true;
                god = calendar.get(Calendar.YEAR);
            } else {
                godIzmenjen = false;
            }

            if (1 + calendar.get(Calendar.MONTH) != mesec) {
                mesecIzmenjen = true;
                mesec = 1 + calendar.get(Calendar.MONTH);
            } else {
                mesecIzmenjen = false;
            }

            prethGod = (mesec == 1 ? god - 1 : god);
            prethMesec = (mesec == 1 ? 12 : mesec - 1);

        } catch (ParseException ex) {
        }
    }
    //</editor-fold>

    public DinamikaFinPoslovanjaServisaTopComponent() {
        initComponents();
        setName(Bundle.CTL_DinamikaFinPoslovanjaServisaTopComponent());
        setToolTipText(Bundle.HINT_DinamikaFinPoslovanjaServisaTopComponent());

        // Print funkcionalnost
        putClientProperty("print.printable", Boolean.TRUE);

        setKalendarDatum(null);

        sbFinDinamikaTekMesec.setUpChartPanel(jPanel_Kompanija_UP);
        sbFinDinamikaTekMesec.setCSSStyle(CSSStyles.Style.YELLOW_LINE);
        sbFinDinamikaPrethMesec.setUpChartPanel(jPanel_Kompanija_UP_MIDDLE);
        sbFinDinamikaPrethMesec.setCSSStyle(CSSStyles.Style.YELLOW_LINE);

        sbFinAspektTekGod.setUpChartPanel(jPanel_Kompanija_DOWN);
        sbFinAspektTekGod.setCSSStyle(CSSStyles.Style.YELLOW_LINE);

        setFX_FA_DnevnoRadoviMaterijal(god, mesec, sbFinDinamikaTekMesec);
        setFX_FA_DnevnoRadoviMaterijal(prethGod, prethMesec, sbFinDinamikaPrethMesec);
        setFX_KretanjeRN_FinAspekt(god, sbFinAspektTekGod);
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
        jPanel_Kompanija_UP_MIDDLE = new javax.swing.JPanel();

        jPanel_Kompanija_UP.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel_Kompanija_UP.setLayout(new java.awt.BorderLayout());

        jPanel_Kompanija_DOWN.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel_Kompanija_DOWN.setLayout(new java.awt.BorderLayout());

        jPanel_Kompanija_UP_MIDDLE.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel_Kompanija_UP_MIDDLE.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jPanel_Kompanija_UP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel_Kompanija_UP_MIDDLE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel_Kompanija_DOWN, javax.swing.GroupLayout.DEFAULT_SIZE, 721, Short.MAX_VALUE))
                .addGap(2, 2, 2))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(jPanel_Kompanija_UP, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jPanel_Kompanija_UP_MIDDLE, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jPanel_Kompanija_DOWN, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel_Kompanija_DOWN;
    private javax.swing.JPanel jPanel_Kompanija_UP;
    private javax.swing.JPanel jPanel_Kompanija_UP_MIDDLE;
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

                        setFX_KretanjeRN_FinAspekt(god, sbFinAspektTekGod);
                        setFX_FA_DnevnoRadoviMaterijal(god, mesec, sbFinDinamikaTekMesec);
                        setFX_FA_DnevnoRadoviMaterijal(prethGod, prethMesec, sbFinDinamikaPrethMesec);
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

    private void setFX_FA_DnevnoRadoviMaterijal(int Godina, int Mesec, AbstractStackedBarGenerator sb) {
        if (godIzmenjen || mesecIzmenjen) {
            try {
                sb.setUpSeries(Mesec_DnevnoFA_UK_Serije(Godina, Mesec));

                sb.setChartTitle("Finansijska Dinamika Servisa za " + SrpskiKalendar.getMesecNazivLatinica(Mesec) + " " + String.valueOf(Godina) + ". godine");
                sb.setSeriesTitles("Radovi", "Materijal");
                sb.createFXObject();

            } catch (NullPointerException ex) {
                Display.obavestenjeBaloncic("Greška.", ex.getLocalizedMessage(), Display.TIP_OBAVESTENJA.GRESKA);
            } catch (Exception e) {
                Display.obavestenjeBaloncic("Greška.", e.toString(), Display.TIP_OBAVESTENJA.GRESKA);
            }
        }
    }

    private void setFX_KretanjeRN_FinAspekt(int Godina, AbstractStackedBarGenerator sb) {
        if (godIzmenjen) {
            try {
                sb.setUpSeries(finansijskiAspekt_GodisnjiPregled_RadMat(Godina));

                sb.setChartTitle("Finansijska Dinamika Servisa za " + String.valueOf(Godina) + " Godinu");
                sb.setSeriesTitles("Radovi", "Delovi");
                sb.createFXObject();

            } catch (NullPointerException ex) {
                Display.obavestenjeBaloncic("Greška.", ex.getLocalizedMessage(), Display.TIP_OBAVESTENJA.GRESKA);
            } catch (Exception e) {
                Display.obavestenjeBaloncic("Greška.", e.toString(), Display.TIP_OBAVESTENJA.GRESKA);
            }
        }
    }
}
