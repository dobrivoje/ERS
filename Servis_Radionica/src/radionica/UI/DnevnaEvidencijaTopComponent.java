/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package radionica.UI;

import ERS.queries.ERSQuery;
import ent.Firma;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.swing.table.TableColumnModel;
import node_klase.interfejsi.globalrefresh.refreshEvidencijeRadnika;
import node_klase.radnici.dnevnaevidencija.EvidencijaSvihRadnikaFirmeZaDatumChildFactory;
import org.netbeans.api.settings.ConvertAsProperties;
import org.netbeans.swing.etable.ETableColumn;
import org.netbeans.swing.etable.ETableColumnModel;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;
import org.openide.windows.TopComponent;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//radionica.dnevnaevidencija//DnevnaEvidencija//EN",
        autostore = false)
@TopComponent.Description(
        preferredID = "DnevnaEvidencijaTopComponent",
        //iconBase = "ikonice/refresh/crvena.gif",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "editor", openAtStartup = false, position = 900)
@ActionID(category = "Window/Servis/Radionica/Evidencije", id = "ui.dnevnaevidencija.DnevnaEvidencijaTopComponent")
@ActionReference(path = "Menu/Window/Servis/Radionica/Evidencije")
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_DnevnaEvidencijaAction",
        preferredID = "DnevnaEvidencijaTopComponent"
)
@Messages({
    "CTL_DnevnaEvidencijaAction=Dnevna Evidencija Majstora",
    "CTL_DnevnaEvidencijaTopComponent=Dnevna Evidencija Majstora",
    "HINT_DnevnaEvidencijaTopComponent=Dnevna Evidencija - Evidencija MajstoraZa Posmatrani Datum"
})
public final class DnevnaEvidencijaTopComponent extends TopComponent
        implements LookupListener, ExplorerManager.Provider, refreshEvidencijeRadnika {

    private Lookup.Result<String> odabraniDatum = null;
    private Lookup.Result<Firma> odabranaFirma = null;
    private LookupListener llOdabraniDatum;
    private LookupListener llFirma;

    //<editor-fold defaultstate="collapsed" desc="Firma">
    private Firma firma;

    public Firma getFirma() {
        return firma;
    }

    public void setFirma(Firma Firma) {
        if (firma == null) {
            this.firma = aktivneFirme.iterator().next();
            refreshAktivniRadnici();
        } else if (!Firma.equals(this.firma)) {
            this.firma = Firma;
            jTextField1.setText(this.firma.getNaziv());
            evidencijeRadnikaRefresh(this.firma, kalendarDatum);
            // refreshAktivniRadnici();
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Kalendar">
    private String kalendarDatum;

    public String getKalendarDatum() {
        return kalendarDatum;
    }

    public void setKalendarDatum(String kalendarDatum) {
        if (this.kalendarDatum == null) {
            this.kalendarDatum = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            refreshAktivniRadnici();
        } else if (!this.kalendarDatum.equals(kalendarDatum)) {
            this.kalendarDatum = kalendarDatum;
            refreshAktivniRadnici();
        }
    }
    //</editor-fold>

    private final List<Firma> aktivneFirme = ERSQuery.AktivneFirme(true);

    private Node evidencijaSvihRadnikaZaDanRoot = null;
    private EvidencijaSvihRadnikaFirmeZaDatumChildFactory evidencijaChildFactory = null;
    private final ExplorerManager em = new ExplorerManager();

    private void evidencijeRadnikaNodeCreation(boolean aktivnaFirma) {
        evidencijeRadnikaRefresh(
                aktivnaFirma
                        ? aktivneFirme.iterator().next() : this.firma, kalendarDatum);

        jCheckBox_AktivneFirmeActionPerformed(null);

    }

    private void evidencijeRadnikaRefresh(Firma firma, String datum) {
        evidencijaChildFactory = new EvidencijaSvihRadnikaFirmeZaDatumChildFactory(firma, datum);
        evidencijaSvihRadnikaZaDanRoot = new AbstractNode(Children.create(evidencijaChildFactory, true));

        em.setRootContext(evidencijaSvihRadnikaZaDanRoot);
    }

    private void initOutLineViewDnevnaEvidencijeSvihRadnika() {
        outlineViewDnevnaEvidencijeSvihRadnika.getOutline().setRootVisible(false);
        outlineViewDnevnaEvidencijeSvihRadnika.setPropertyColumns(
                "imeRadnika", "Ime",
                "prezimeRadnika", "Prezime",
                "rbrstanja", "Rbr.",
                "status", "Status", "nalog", "NALOG",
                "pocStanja", "Početak", "krajStanja", "Kraj",
                "trajanje", "Trajanje");

        // Sakrij prvu kolonu koja je ima bezveze naziv node-a :
        TableColumnModel tm = outlineViewDnevnaEvidencijeSvihRadnika.getOutline().getColumnModel();
        ETableColumn etc = (ETableColumn) tm.getColumn(0);
        ((ETableColumnModel) tm).setColumnHidden(etc, true);

        evidencijeRadnikaNodeCreation(jCheckBox_AktivneFirme.isSelected());
    }

    public DnevnaEvidencijaTopComponent() {
        initComponents();
        setName(Bundle.CTL_DnevnaEvidencijaTopComponent());
        setToolTipText(Bundle.HINT_DnevnaEvidencijaTopComponent());

        setFirma(null);
        setKalendarDatum(null);
        jCheckBox_AktivneFirmeActionPerformed(null);

        initOutLineViewDnevnaEvidencijeSvihRadnika();
        associateLookup(ExplorerUtils.createLookup(em, getActionMap()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        outlineViewDnevnaEvidencijeSvihRadnika = new org.openide.explorer.view.OutlineView();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jCheckBox_AktivneFirme = new javax.swing.JCheckBox();
        jTextField1 = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(DnevnaEvidencijaTopComponent.class, "DnevnaEvidencijaTopComponent.jLabel4.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(DnevnaEvidencijaTopComponent.class, "DnevnaEvidencijaTopComponent.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox_AktivneFirme, org.openide.util.NbBundle.getMessage(DnevnaEvidencijaTopComponent.class, "DnevnaEvidencijaTopComponent.jCheckBox_AktivneFirme.text")); // NOI18N
        jCheckBox_AktivneFirme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox_AktivneFirmeActionPerformed(evt);
            }
        });

        jTextField1.setBackground(new java.awt.Color(224, 223, 227));
        jTextField1.setText(org.openide.util.NbBundle.getMessage(DnevnaEvidencijaTopComponent.class, "DnevnaEvidencijaTopComponent.jTextField1.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(outlineViewDnevnaEvidencijeSvihRadnika, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox_AktivneFirme)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addGap(3, 3, 3)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox_AktivneFirme))
                .addGap(33, 33, 33)
                .addComponent(outlineViewDnevnaEvidencijeSvihRadnika, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBox_AktivneFirmeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox_AktivneFirmeActionPerformed
        // TODO add your handling code here:
        if (jTextField1.getText().length() == 0) {
            jTextField1.setText(this.firma.getNaziv());
        }
    }//GEN-LAST:event_jCheckBox_AktivneFirmeActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBox_AktivneFirme;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private org.openide.explorer.view.OutlineView outlineViewDnevnaEvidencijeSvihRadnika;
    // End of variables declaration//GEN-END:variables

    @Override
    public void componentOpened() {
        odabraniDatum = Utilities.actionsGlobalContext().lookupResult(String.class);
        odabranaFirma = Utilities.actionsGlobalContext().lookupResult(Firma.class);

        llOdabraniDatum = new LookupListener() {
            @Override
            public void resultChanged(LookupEvent le) {
                Lookup.Result ls = (Lookup.Result) le.getSource();
                Collection<String> datumi = ls.allInstances();

                for (String ds1 : datumi) {
                    setKalendarDatum(ds1);
                }
            }
        };
        odabraniDatum.addLookupListener(llOdabraniDatum);

        llFirma = new LookupListener() {
            @Override
            public void resultChanged(LookupEvent le) {
                Lookup.Result lr = (Lookup.Result) le.getSource();
                Collection<Firma> firme = lr.allInstances();
                for (Firma f : firme) {
                    setFirma(f);
                }
            }
        };
        odabranaFirma.addLookupListener(llFirma);
    }

    @Override
    public void componentClosed() {
        odabraniDatum.removeLookupListener(llOdabraniDatum);
        odabraniDatum = null;

        odabranaFirma.removeLookupListener(llFirma);
        odabranaFirma = null;
    }

    //<editor-fold defaultstate="collapsed" desc="ne treba ...">
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

    @Override
    public void resultChanged(LookupEvent le) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    //</editor-fold>

    @Override
    public ExplorerManager getExplorerManager() {
        return em;
    }

    @Override
    public void refreshAktivniRadnici() {
        evidencijeRadnikaNodeCreation(true);
    }
}
