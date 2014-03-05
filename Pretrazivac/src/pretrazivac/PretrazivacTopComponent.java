/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pretrazivac;

import ERS.queries.ERSQuery;
import Exceptions.PomesaniDatumiException;
import com.dobrivoje.utilities.datumi.DatumSelektor;
import com.dobrivoje.utilities.icons.icon_renderer.INodeIconRenderer;
import com.dobrivoje.utilities.icons.icon_renderer.IconRenderer;
import static com.dobrivoje.utilities.icons.icon_renderer.IconType.ProfCentar.FIRMA;
import static com.dobrivoje.utilities.icons.icon_renderer.IconType.ProfCentar.KOMPANIJA;
import com.dobrivoje.utilities.warnings.Display;
import ent.Firma;
import ent.Kompanija;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import node_klase.firma.AktivnaFirmaChildFactory;
import node_klase.firma.AktivnaFirmaZaDatumChildFactory;
import node_klase.firma.FirmaPoIDChildFactory;
import node_klase.kompanija.KompanijaChildFactory;
import node_klase.kompanija.KompanijaPoIDChildFactory;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.StatusDisplayer;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.ProxyLookup;
import org.openide.windows.TopComponent;
import pretrazivac.beans.Kalendar;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//pretrazivac//Pretrazivac//EN",
        autostore = false)
@TopComponent.Description(
        preferredID = "PretrazivacTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "explorer", openAtStartup = true, position = 100)
@ActionID(category = "Window/Pretraživač", id = "pretrazivac.PretrazivacTopComponent")
@ActionReference(path = "Menu/Window/Pretraživač", position = 100)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_PretrazivacAction",
        preferredID = "PretrazivacTopComponent")
@Messages({
    "CTL_PretrazivacAction=Pretraživač",
    "CTL_PretrazivacTopComponent=Pretraživač",
    "HINT_PretrazivacTopComponent=Ovo je pretraživač podataka sistema."
})
public final class PretrazivacTopComponent extends TopComponent
        implements ExplorerManager.Provider, INodeIconRenderer {

    private final ExplorerManager em = new ExplorerManager();

    private final InstanceContent ic = new InstanceContent();
    private final InstanceContent ic2 = new InstanceContent();
    private final InstanceContent ic3 = new InstanceContent();

    private final DatumSelektor sd = DatumSelektor.getDafault();
    //
    private AbstractNode root;
    // pomoćni datum,...
    private String datum;
    private final Calendar calendar = Calendar.getInstance();
    private final Kalendar kalendar;
    //
    private Kompanija kompanija;

    private final IconRenderer iconRenderer = IconRenderer.getDefault();

    //<editor-fold defaultstate="collapsed" desc="Ostale implementacije, init-i ..">
    private void init(int IDFirme) {
        Firma f = ERSQuery.FirmaID(IDFirme);
        Kompanija k = f.getFkIdk();

        setName(k.getNazivKompanije() + "->" + f.getNaziv());

        root = new AbstractNode(
                Children.create(new KompanijaChildFactory(), true));

        em.setRootContext(root);

        iconRenderer.generateIcons(FIRMA, this);
        root.setName("Evidencija Rada Servisa");

    }

    private void initKompanija(int IDKompanije) {
        Kompanija k = ERSQuery.kompanijaPoID(IDKompanije);

        setName(k.getNazivKompanije() + "->");

        root = new AbstractNode(
                Children.create(new KompanijaPoIDChildFactory(IDKompanije), true));

        em.setRootContext(root);

        iconRenderer.generateIcons(FIRMA, this);
        root.setName("Evidencija Rada Servisa");
    }

    public void initFirma(int IDFirma) {
        Firma f = ERSQuery.FirmaID(IDFirma);

        setName(f.getFkIdk().getNazivKompanije() + "-" + f.getNaziv());

        root = new AbstractNode(
                Children.create(new FirmaPoIDChildFactory(IDFirma), true));

        em.setRootContext(root);

        iconRenderer.generateIcons(FIRMA, this);
        root.setName("Evidencija Rada Servisa");
    }
    //</editor-fold>

    private void initAktivneFirme(boolean aktivne) throws NullPointerException {
        // izbacuje NullPointerException ako ne uspe da se poveže sa SQL serverom !
        // Postavi naziv kompanije, naziv može da se uzme iz bilo koje aktivne firme,...

        kompanija = ERSQuery.AktivneFirme(aktivne).iterator().next().getFkIdk();

        // ic2.add(kompanija);
        // ic2.set(Collections.singleton(kompanija), null);

        setName(kompanija.getNazivKompanije());

        // Prikaži sve aktivne firme ! 
        root = new AbstractNode(Children.create(new AktivnaFirmaChildFactory(true), true));
        em.setRootContext(root);

        root.setName(kompanija.getNazivKompanije());
        iconRenderer.generateIcons(KOMPANIJA, this);

        // Ispiši koliko je aktivnih radnika trenutno koji se evidentiraju
        jLabel_UK_BR_RADNIKA.setText(Integer.toString(ERSQuery.radniciZaDatum(datum).size()));
    }

    public void aktivnaFirmaRadniciZaDatum(String datum) throws NullPointerException {
        // izbacuje NullPointerException ako ne uspe da se poveže sa SQL serverom !
        // Postavi naziv kompanije, naziv može da se uzme iz bilo koje aktivne firme,...

        kompanija = ERSQuery.AktivneFirme(true).iterator().next().getFkIdk();
        setName(kompanija.getNazivKompanije());

        root = new AbstractNode(Children.create(new AktivnaFirmaZaDatumChildFactory(true, datum), true));
        em.setRootContext(root);

        iconRenderer.generateIcons(KOMPANIJA, this);
        root.setName(kompanija.getNazivKompanije());
    }

    public PretrazivacTopComponent() {
        initComponents();
        setName(Bundle.CTL_PretrazivacTopComponent());
        setToolTipText(Bundle.HINT_PretrazivacTopComponent());
        putClientProperty(TopComponent.PROP_KEEP_PREFERRED_SIZE_WHEN_SLIDED_IN, Boolean.TRUE);

        calendar.setTime(jCalendar1.getDate());
        // Pazi na msesec : mesec počinje od nule !!!
        kalendar = new Kalendar(calendar.get(Calendar.YEAR), 1 + calendar.get(Calendar.MONTH));

        associateLookup(
                new ProxyLookup(
                        ExplorerUtils.createLookup(em, getActionMap()),
                        new AbstractLookup(ic),
                        new AbstractLookup(ic2)
                )
        );

        jCalendar1PropertyChange(null);

        // Inicijalizacija firme koju želimo da pratimo (Aktivna firma) = Autokomerc Komision :
        // int aktivnaFirmaID = ERSQuery.AktivneFirme(true).iterator().next().getIDFirma();
        // initFirma(aktivnaFirmaID);
        try {
            initAktivneFirme(true);
        } catch (NullPointerException e) {
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        beanTreeView_ERS_Radnici = new org.openide.explorer.view.BeanTreeView();
        jCalendar1 = new com.toedter.calendar.JCalendar();
        jDateChooser_Datum_OD = new com.toedter.calendar.JDateChooser();
        jDateChooser_Datum_DO = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel_UK_BR_RADNIKA = new javax.swing.JLabel();

        jScrollPane1.setViewportView(jEditorPane1);

        jCalendar1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jCalendar1PropertyChange(evt);
            }
        });

        jDateChooser_Datum_OD.setDateFormatString(org.openide.util.NbBundle.getMessage(PretrazivacTopComponent.class, "PretrazivacTopComponent.jDateChooser_Datum_OD.dateFormatString")); // NOI18N
        jDateChooser_Datum_OD.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser_Datum_ODPropertyChange(evt);
            }
        });

        jDateChooser_Datum_DO.setDateFormatString(org.openide.util.NbBundle.getMessage(PretrazivacTopComponent.class, "PretrazivacTopComponent.jDateChooser_Datum_DO.dateFormatString")); // NOI18N
        jDateChooser_Datum_DO.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser_Datum_DOPropertyChange(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(PretrazivacTopComponent.class, "PretrazivacTopComponent.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(PretrazivacTopComponent.class, "PretrazivacTopComponent.jLabel3.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(PretrazivacTopComponent.class, "PretrazivacTopComponent.jLabel4.text")); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(PretrazivacTopComponent.class, "PretrazivacTopComponent.jLabel5.text")); // NOI18N

        jLabel_UK_BR_RADNIKA.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel_UK_BR_RADNIKA.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel_UK_BR_RADNIKA, org.openide.util.NbBundle.getMessage(PretrazivacTopComponent.class, "PretrazivacTopComponent.jLabel_UK_BR_RADNIKA.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(beanTreeView_ERS_Radnici, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel_UK_BR_RADNIKA, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jDateChooser_Datum_OD, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jDateChooser_Datum_DO, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jCalendar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jCalendar1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jDateChooser_Datum_OD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jDateChooser_Datum_DO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel_UK_BR_RADNIKA))
                .addGap(9, 9, 9)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(beanTreeView_ERS_Radnici, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jCalendar1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jCalendar1PropertyChange
        // TODO add your handling code here:

        // ic.set(Collections.emptyList(), null);
        datum = new SimpleDateFormat("yyyy-MM-dd").format(jCalendar1.getDate());
        /// ic.set(Collections.singleton(datum), null);
        ic2.set(Collections.singleton(datum), null);

        try {
            aktivnaFirmaRadniciZaDatum(datum);

            // Prikaži koliko radnika se evidentiralo za datum...
            jLabel_UK_BR_RADNIKA.setText(Integer.toString(ERSQuery.radniciZaDatum(datum).size()));
        } catch (NullPointerException e) {
        }

        /*
        calendar.setTime(jCalendar1.getDate());
        kalendar.setGM(calendar.get(Calendar.YEAR), 1 + calendar.get(Calendar.MONTH));
        if (kalendar.getNoviUpis()) {
        ic3.set(Collections.singleton(kalendar), null);
        }
        */

        StatusDisplayer.getDefault().setStatusText(datum.toString() + ",  " + kalendar.toString());
    }//GEN-LAST:event_jCalendar1PropertyChange

    private void jDateChooser_Datum_ODPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser_Datum_ODPropertyChange
        // TODO add your handling code here:
        ic.set(Collections.emptyList(), null);

        try {
            sd.setDatumOD(jDateChooser_Datum_OD.getDate());
            
            // ic.add(sd);
            ic.set(Collections.singleton(sd), null);
            
        } catch (NullPointerException e1) {
        } catch (PomesaniDatumiException e1) {
            Display.obavestenjeBaloncic("Greška.", "Izabrati ispravno početni i krajnji datum.",
                    Display.TIP_OBAVESTENJA.GRESKA);
        }
    }//GEN-LAST:event_jDateChooser_Datum_ODPropertyChange

    private void jDateChooser_Datum_DOPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser_Datum_DOPropertyChange
        // TODO add your handling code here:
        ic.set(Collections.emptyList(), null);

        try {
            sd.setDatumDO(jDateChooser_Datum_DO.getDate());
            
            //ic.add(sd);
            ic.set(Collections.singleton(sd), null);

        } catch (NullPointerException e1) {
        } catch (PomesaniDatumiException e1) {
            Display.obavestenjeBaloncic("Unet obrnut redosled datuma.",
                    "Datum Od: " + sd.getDatumStringOD() + ", datum do: " + sd.getDatumStringDO(),
                    Display.TIP_OBAVESTENJA.GRESKA);
        }
    }//GEN-LAST:event_jDateChooser_Datum_DOPropertyChange

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.openide.explorer.view.BeanTreeView beanTreeView_ERS_Radnici;
    private com.toedter.calendar.JCalendar jCalendar1;
    private com.toedter.calendar.JDateChooser jDateChooser_Datum_DO;
    private com.toedter.calendar.JDateChooser jDateChooser_Datum_OD;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel_UK_BR_RADNIKA;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    // End of variables declaration//GEN-END:variables

    //<editor-fold defaultstate="collapsed" desc="ne treba,..">
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

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

    @Override
    public ExplorerManager getExplorerManager() {
        return em;
    }

    @Override
    public void node_setIconBaseWithExtension(String URL) {
        root.setIconBaseWithExtension(URL);
    }
}
