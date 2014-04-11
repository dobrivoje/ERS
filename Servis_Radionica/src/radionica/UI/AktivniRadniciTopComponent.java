/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package radionica.UI;

import ERS.queries.ERSQuery;
import ent.Firma;
import node_klase.radnici.AktivniRadniciFirmeChildFactory;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//radionica//AktivniRadnici//EN",
        autostore = false)
@TopComponent.Description(
        preferredID = "AktivniRadniciTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "editor", openAtStartup = false, position = 200)
@ActionID(category = "Window/Servis/Radionica/Majstori", id = "ui.AktivniRadniciTopComponent")
@ActionReference(path = "Menu/Window/Servis/Radionica/Majstori" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_AktivniRadniciAction",
        preferredID = "AktivniRadniciTopComponent")
@Messages({
    "CTL_AktivniRadniciAction=Aktivni Majstori",
    "CTL_AktivniRadniciTopComponent=Aktivni Majstori",
    "HINT_AktivniRadniciTopComponent=Aktivni Majstori Organizacione jedinice"
})
public final class AktivniRadniciTopComponent extends TopComponent
        implements LookupListener, ExplorerManager.Provider /*, refreshAktivniRadnici */ {

    private final ExplorerManager em = new ExplorerManager();
    private Firma firmaLookup;
    //
    private Node aktivniRadniciRoot = null;

    private void aktivniRadniciNodeCreation() {
        for (Firma f1 : ERSQuery.AktivneFirme(true)) {
            firmaLookup = f1;
            aktivniRadniciRoot = new AbstractNode(
                    Children.create(new AktivniRadniciFirmeChildFactory(firmaLookup), true));
        }

        em.setRootContext(aktivniRadniciRoot);
    }

    public AktivniRadniciTopComponent() {
        initComponents();
        setName(Bundle.CTL_AktivniRadniciTopComponent());
        setToolTipText(Bundle.HINT_AktivniRadniciTopComponent());

        initOutLineAktivniRadnici();
        associateLookup(ExplorerUtils.createLookup(em, getActionMap()));
    }

    private void initOutLineAktivniRadnici() {
        outlineViewAktivniRadnici.getOutline().setRootVisible(false);
        outlineViewAktivniRadnici.setPropertyColumns(
                "IDRadnik", "ID Radnika",
                "ime", "IME",
                "prezime", "PREZIME",
                "grupa", "GRUPA");

        aktivniRadniciNodeCreation();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator6 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        outlineViewAktivniRadnici = new org.openide.explorer.view.OutlineView();

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(AktivniRadniciTopComponent.class, "AktivniRadniciTopComponent.jLabel5.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator6)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(outlineViewAktivniRadnici, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(outlineViewAktivniRadnici, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel5;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator6;
    private org.openide.explorer.view.OutlineView outlineViewAktivniRadnici;
    // End of variables declaration//GEN-END:variables

    @Override
    public void componentOpened() {
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

    @Override
    public void resultChanged(LookupEvent le) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ExplorerManager getExplorerManager() {
        return em;
    }

    //@Override
    public void refreshAktivniRadnici() {
        aktivniRadniciNodeCreation();
    }

    @Override
    protected void componentActivated() {
        refreshAktivniRadnici();
    }
}
