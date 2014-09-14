/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package radionica.UI;

import ERS.queries.ERSQuery;
import Exceptions.AktivanRadnikException;
import Exceptions.NalogException;
import Exceptions.RadnikException;
import com.dobrivoje.utilities.comboboxmodeli.StatusiComboBoxModel;
import com.dobrivoje.utilities.warnings.Display;
import ent.Firma;
import ent.Orgjed;
import ent.Radnik;
import ent.Statusi;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_ENTER;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import javax.persistence.NoResultException;
import javax.swing.table.TableColumnModel;
import node_klase.RADDANZaDatumChildFactory;
import org.dobrivoje.calendarutilities.exceptions.DatumException;
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
import radionica.QuickSearch.IRadnik;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//radionica//KlijentEditor//EN",
        autostore = false)
@TopComponent.Description(
        preferredID = "RadnikTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "editor", openAtStartup = false, position = 1000)
@ActionID(category = "Window/Servis/Radionica/Evidencije", id = "ui.RadnikTopComponent")
@ActionReference(path = "Menu/Window/Servis/Radionica/Evidencije" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_RadnikAction",
        preferredID = "RadnikTopComponent")
@Messages({
    "CTL_RadnikAction=Evidencija Majstora",
    "CTL_RadnikTopComponent=Evidencija Majstora",
    "HINT_RadnikTopComponent=Evidencija Majstora."
})
public final class RadnikTopComponent extends TopComponent
        implements LookupListener, ExplorerManager.Provider {

    private Lookup.Result<Firma> odabraneFirme = null;
    private Lookup.Result<Orgjed> odabraneOrgJed = null;
    private Lookup.Result<Radnik> odabraniRadnici = null;
    private Lookup.Result<String> odabraniDatum = null;

    private LookupListener llOdabraneFirme;
    private LookupListener llOdabraneOrgJed;
    private LookupListener llOdabraniRadnici;
    private LookupListener llOdabraniDatum;

    // pomoćne varijable
    //<editor-fold defaultstate="collapsed" desc="Firma Bind">
    private Firma firma_bind;
    public static final String PROP_FIRMA_BIND = "firma_bind";

    public Firma getFirma_bind() {
        return firma_bind;
    }

    public void setFirma_bind(Firma firma_bind) {
        Firma oldFirma_bind = this.firma_bind;
        this.firma_bind = firma_bind;
        propertyChangeSupport.firePropertyChange(PROP_FIRMA_BIND, oldFirma_bind, firma_bind);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="OrgJed Bind">
    private Orgjed orgJed_bind;

    public static final String PROP_ORGJED_BIND = "orgJed_bind";

    public Orgjed getOrgJed_bind() {
        return orgJed_bind;
    }

    public void setOrgJed_bind(Orgjed orgJed_bind) {
        Orgjed oldOrgJed_bind = this.orgJed_bind;
        this.orgJed_bind = orgJed_bind;
        propertyChangeSupport.firePropertyChange(PROP_ORGJED_BIND, oldOrgJed_bind, orgJed_bind);
    }

    private transient final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Radnik Bind">
    private Radnik radnik_bind;

    public static final String PROP_RADNIK_BIND = "radnik_bind";

    public Radnik getRadnik_bind() {
        return radnik_bind;
    }

    public void setRadnik_bind(Radnik radnik_bind) {
        Radnik oldRadnik_bind = this.radnik_bind;
        this.radnik_bind = radnik_bind;
        propertyChangeSupport.firePropertyChange(PROP_RADNIK_BIND, oldRadnik_bind, radnik_bind);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Datum Bind">
    private String datum_bind;

    public static final String PROP_DATUM_BIND = "datum_bind";

    public String getDatum_bind() {
        return datum_bind;
    }

    public void setDatum_bind(String datum_bind) {
        String oldDatum_bind = this.datum_bind;
        this.datum_bind = datum_bind;
        propertyChangeSupport.firePropertyChange(PROP_DATUM_BIND, oldDatum_bind, datum_bind);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Statusi Bind">
    private Statusi statusi_bind;

    public static final String PROP_STATUSI_BIND = "statusi_bind";

    /**
     * Get the value of statusi_bind
     *
     * @return the value of statusi_bind
     */
    public Statusi getStatusi_bind() {
        return statusi_bind;
    }

    /**
     * Set the value of statusi_bind
     *
     * @param statusi_bind new value of statusi_bind
     */
    public void setStatusi_bind(Statusi statusi_bind) {
        Statusi oldStatusi_bind = this.statusi_bind;
        this.statusi_bind = statusi_bind;
        propertyChangeSupport.firePropertyChange(PROP_STATUSI_BIND, oldStatusi_bind, statusi_bind);
    }
//</editor-fold>

    private Node evidencijeRadnikaRoot = null;
    private RADDANZaDatumChildFactory evidencijeChildFactory = null;
    //
    private final ExplorerManager em = new ExplorerManager();

    private void evidencijeRadnikaNodeCreation(Radnik radnik, String datum) {
        evidencijeChildFactory = new RADDANZaDatumChildFactory(radnik, datum);
        evidencijeRadnikaRoot = new AbstractNode(
                Children.create(evidencijeChildFactory, true));

        em.setRootContext(evidencijeRadnikaRoot);
    }

    private void initOutLineViewEvidencijeRadnikaZaDan(Radnik r, String datum) {
        outlineViewEvidencijeRadnikaZaDan.getOutline().setRootVisible(false);
        outlineViewEvidencijeRadnikaZaDan.setPropertyColumns(
                "rbrstanja", "Rbr.", "datum", "Datum",
                "status", "Status", "nalog", "NALOG",
                "pocStanja", "Početak", "krajStanja", "Kraj",
                "trajanje", "Trajanje");

        // Sakrij prvu kolonu koja je ima bezveze naziv node-a :
        TableColumnModel tm = outlineViewEvidencijeRadnikaZaDan.getOutline().getColumnModel();
        ETableColumn etc = (ETableColumn) tm.getColumn(0);
        ((ETableColumnModel) tm).setColumnHidden(etc, true);
        
        evidencijeRadnikaNodeCreation(r, datum);
        associateLookup(ExplorerUtils.createLookup(em, getActionMap()));
    }

    private void refreshOutlineViewEvidencija(Radnik r, String datum) {
        evidencijeRadnikaNodeCreation(r, datum);
    }

    public RadnikTopComponent() {
        initComponents();
        setName(Bundle.CTL_RadnikAction());
        setToolTipText(Bundle.HINT_RadnikTopComponent());

        resetKontola();
        initOutLineViewEvidencijeRadnikaZaDan(radnik_bind, datum_bind);

        // is = Lookup.getDefault().lookup(ISifarnik.class);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField_FIRMA = new javax.swing.JTextField();
        jTextField_ORGJED = new javax.swing.JTextField();
        jTextField_SIFRA_RADNIKA = new javax.swing.JTextField();
        jTextField_SIFRA_STATUSA = new javax.swing.JTextField();
        jTextField_RADNI_NALOG = new javax.swing.JTextField();
        jTextField_RADNIK = new javax.swing.JTextField();
        jTextField_SIFRA_STATUSA_NALOGA = new javax.swing.JTextField();
        jTextField_STATUS_NALOGA_OPIS = new javax.swing.JTextField();
        jButton_EVIDENCIJA = new javax.swing.JButton();
        jButton_EVIDENCIJA_ODUSTAJANJE = new javax.swing.JButton();
        outlineViewEvidencijeRadnikaZaDan = new org.openide.explorer.view.OutlineView();
        jComboBox_STATUSI = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();

        jTextField_FIRMA.setEditable(false);
        jTextField_FIRMA.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField_FIRMA.setText(org.openide.util.NbBundle.getMessage(RadnikTopComponent.class, "RadnikTopComponent.jTextField_FIRMA.text")); // NOI18N
        jTextField_FIRMA.setFocusable(false);

        jTextField_ORGJED.setEditable(false);
        jTextField_ORGJED.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField_ORGJED.setText(org.openide.util.NbBundle.getMessage(RadnikTopComponent.class, "RadnikTopComponent.jTextField_ORGJED.text")); // NOI18N
        jTextField_ORGJED.setFocusable(false);

        jTextField_SIFRA_RADNIKA.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField_SIFRA_RADNIKA.setText(org.openide.util.NbBundle.getMessage(RadnikTopComponent.class, "RadnikTopComponent.jTextField_SIFRA_RADNIKA.text")); // NOI18N
        jTextField_SIFRA_RADNIKA.setToolTipText(org.openide.util.NbBundle.getMessage(RadnikTopComponent.class, "RadnikTopComponent.jTextField_SIFRA_RADNIKA.toolTipText")); // NOI18N
        jTextField_SIFRA_RADNIKA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_SIFRA_RADNIKAKeyReleased(evt);
            }
        });

        jTextField_SIFRA_STATUSA.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField_SIFRA_STATUSA.setText(org.openide.util.NbBundle.getMessage(RadnikTopComponent.class, "RadnikTopComponent.jTextField_SIFRA_STATUSA.text")); // NOI18N
        jTextField_SIFRA_STATUSA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_SIFRA_STATUSAKeyReleased(evt);
            }
        });

        jTextField_RADNI_NALOG.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField_RADNI_NALOG.setText(org.openide.util.NbBundle.getMessage(RadnikTopComponent.class, "RadnikTopComponent.jTextField_RADNI_NALOG.text")); // NOI18N
        jTextField_RADNI_NALOG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_RADNI_NALOGKeyReleased(evt);
            }
        });

        jTextField_RADNIK.setEditable(false);
        jTextField_RADNIK.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField_RADNIK.setText(org.openide.util.NbBundle.getMessage(RadnikTopComponent.class, "RadnikTopComponent.jTextField_RADNIK.text")); // NOI18N
        jTextField_RADNIK.setFocusable(false);

        jTextField_SIFRA_STATUSA_NALOGA.setEditable(false);
        jTextField_SIFRA_STATUSA_NALOGA.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField_SIFRA_STATUSA_NALOGA.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_SIFRA_STATUSA_NALOGA.setText(org.openide.util.NbBundle.getMessage(RadnikTopComponent.class, "RadnikTopComponent.jTextField_SIFRA_STATUSA_NALOGA.text_1")); // NOI18N

        jTextField_STATUS_NALOGA_OPIS.setEditable(false);
        jTextField_STATUS_NALOGA_OPIS.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField_STATUS_NALOGA_OPIS.setFocusable(false);

        jButton_EVIDENCIJA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ikonice/errors_warnings_info/ok.gif"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jButton_EVIDENCIJA, org.openide.util.NbBundle.getMessage(RadnikTopComponent.class, "RadnikTopComponent.jButton_EVIDENCIJA.text")); // NOI18N
        jButton_EVIDENCIJA.setToolTipText(org.openide.util.NbBundle.getMessage(RadnikTopComponent.class, "RadnikTopComponent.jButton_EVIDENCIJA.toolTipText")); // NOI18N
        jButton_EVIDENCIJA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_EVIDENCIJAActionPerformed(evt);
            }
        });
        jButton_EVIDENCIJA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jButton_EVIDENCIJAKeyReleased(evt);
            }
        });

        jButton_EVIDENCIJA_ODUSTAJANJE.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ikonice/errors_warnings_info/error_circle.gif"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jButton_EVIDENCIJA_ODUSTAJANJE, org.openide.util.NbBundle.getMessage(RadnikTopComponent.class, "RadnikTopComponent.jButton_EVIDENCIJA_ODUSTAJANJE.text")); // NOI18N
        jButton_EVIDENCIJA_ODUSTAJANJE.setToolTipText(org.openide.util.NbBundle.getMessage(RadnikTopComponent.class, "RadnikTopComponent.jButton_EVIDENCIJA_ODUSTAJANJE.toolTipText")); // NOI18N
        jButton_EVIDENCIJA_ODUSTAJANJE.setPreferredSize(new java.awt.Dimension(155, 31));
        jButton_EVIDENCIJA_ODUSTAJANJE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_EVIDENCIJA_ODUSTAJANJEActionPerformed(evt);
            }
        });

        outlineViewEvidencijeRadnikaZaDan.setToolTipText(org.openide.util.NbBundle.getMessage(RadnikTopComponent.class, "RadnikTopComponent.outlineViewEvidencijeRadnikaZaDan.toolTipText")); // NOI18N
        outlineViewEvidencijeRadnikaZaDan.setName(""); // NOI18N

        jComboBox_STATUSI.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBox_STATUSI.setModel(new StatusiComboBoxModel());
        jComboBox_STATUSI.setEnabled(false);
        jComboBox_STATUSI.setFocusable(false);
        jComboBox_STATUSI.setOpaque(false);
        jComboBox_STATUSI.setPreferredSize(new java.awt.Dimension(35, 28));
        jComboBox_STATUSI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_STATUSIActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(RadnikTopComponent.class, "RadnikTopComponent.jLabel1.text")); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(RadnikTopComponent.class, "RadnikTopComponent.jLabel2.text")); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(RadnikTopComponent.class, "RadnikTopComponent.jLabel3.text")); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(RadnikTopComponent.class, "RadnikTopComponent.jLabel4.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField_SIFRA_RADNIKA, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                                    .addComponent(jTextField_SIFRA_STATUSA))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jTextField_SIFRA_STATUSA_NALOGA, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField_STATUS_NALOGA_OPIS))
                                    .addComponent(jTextField_RADNIK)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextField_RADNI_NALOG, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBox_STATUSI, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(outlineViewEvidencijeRadnikaZaDan, javax.swing.GroupLayout.DEFAULT_SIZE, 536, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jTextField_FIRMA)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField_ORGJED, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton_EVIDENCIJA)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_EVIDENCIJA_ODUSTAJANJE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator6, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_FIRMA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_ORGJED, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField_SIFRA_RADNIKA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_RADNIK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField_STATUS_NALOGA_OPIS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)
                        .addComponent(jTextField_SIFRA_STATUSA_NALOGA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextField_SIFRA_STATUSA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField_RADNI_NALOG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2))
                    .addComponent(jComboBox_STATUSI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jButton_EVIDENCIJA)
                    .addComponent(jButton_EVIDENCIJA_ODUSTAJANJE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(outlineViewEvidencijeRadnikaZaDan, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox_STATUSIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_STATUSIActionPerformed
        // TODO add your handling code here:
        statusi_bind = (Statusi) jComboBox_STATUSI.getSelectedItem();

        jTextField_SIFRA_STATUSA_NALOGA.setText(statusi_bind.getIDStatus().toString());
        jTextField_STATUS_NALOGA_OPIS.setText(statusi_bind.getZnacenje());
    }//GEN-LAST:event_jComboBox_STATUSIActionPerformed

    private void jButton_EVIDENCIJAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_EVIDENCIJAActionPerformed
        String Nalog;

        try {
            Nalog = (jTextField_RADNI_NALOG.getText().isEmpty() ? "" : jTextField_RADNI_NALOG.getText());

            if (proveraZaUpis(radnik_bind, datum_bind, statusi_bind, Nalog)) {
                ERSQuery.evidentirajAktivnostRadnika4(radnik_bind, statusi_bind, Nalog);
            }

            refreshOutlineViewEvidencija(radnik_bind, datum_bind);
        } catch (Exception e) {
            Display.obavestenjeBaloncic("Greška.", "Greška Prilikom Upisa. Opis Greške: " + e.getMessage(), Display.TIP_OBAVESTENJA.GRESKA);

        } finally {
            resetKontola();
        }
    }//GEN-LAST:event_jButton_EVIDENCIJAActionPerformed

    private void jTextField_SIFRA_STATUSAKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_SIFRA_STATUSAKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                statusi_bind = ERSQuery.statusPoID(Integer.parseInt(jTextField_SIFRA_STATUSA.getText()));

                displayStatusUI(statusi_bind);

                if (statusi_bind.getUnosNaloga()) {
                    jTextField_RADNI_NALOG.setEnabled(true);
                    jTextField_RADNI_NALOG.requestFocus();
                } else {
                    jTextField_RADNI_NALOG.setEnabled(false);
                    jButton_EVIDENCIJA.setEnabled(true);
                    jButton_EVIDENCIJA.requestFocus();
                }

                jTextField_SIFRA_RADNIKA.setEnabled(false);
                jTextField_SIFRA_STATUSA.setEnabled(false);

                jButton_EVIDENCIJA_ODUSTAJANJE.setEnabled(true);
            } catch (NullPointerException e1) {
            } catch (NumberFormatException e) {

                displayStatusUI(null);

                jButton_EVIDENCIJA.setEnabled(false);
                jButton_EVIDENCIJA_ODUSTAJANJE.setEnabled(true);

                if (jTextField_SIFRA_STATUSA.getText().length() > 0) {
                    Display.obavestenjeBaloncic("Greška", "Mora Se Uneti Ispravna Šifra Radnika !", Display.TIP_OBAVESTENJA.GRESKA);
                }
            }
        }
    }//GEN-LAST:event_jTextField_SIFRA_STATUSAKeyReleased

    private void jTextField_SIFRA_RADNIKAKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_SIFRA_RADNIKAKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            Radnik r;
            try {
                r = ERSQuery.radnikID(Long.parseLong(jTextField_SIFRA_RADNIKA.getText()));

                if (r.isRadnik()) {
                    if (r.getAktivan()) {
                        radnik_bind = r;
                        setUpRadnik(r);
                        displayUI();

                        jTextField_SIFRA_RADNIKA.setEnabled(false);
                        jButton_EVIDENCIJA_ODUSTAJANJE.setEnabled(true);

                        jTextField_SIFRA_STATUSA.setEnabled(true);
                        jTextField_SIFRA_STATUSA.requestFocus();
                    } else {
                        throw new AktivanRadnikException();
                    }
                } else {
                    throw new RadnikException("");
                }

            } catch (NullPointerException e) {
                Display.obavestenjeBaloncic("Greška", "Ne Postoji Radnik Sa Ovom Šifrom !", Display.TIP_OBAVESTENJA.GRESKA);
                resetKontola();
            } catch (NumberFormatException e) {
                if (jTextField_SIFRA_RADNIKA.getText().length() > 0) {
                    Display.obavestenjeBaloncic("Greška", "Mora Se Uneti Ispravna Šifra Radnika !", Display.TIP_OBAVESTENJA.GRESKA);
                }
                resetKontola();
            } catch (RadnikException re) {
                Display.obavestenjeBaloncic("Greška", "Samo Majstori Se Mogu Evidentirati !", Display.TIP_OBAVESTENJA.GRESKA);
                resetKontola();
            } catch (AktivanRadnikException are) {
                Display.obavestenjeBaloncic("Greška", "Samo Aktivni Majstori Se Mogu Evidentirati !", Display.TIP_OBAVESTENJA.GRESKA);
                resetKontola();
            }
        }
    }//GEN-LAST:event_jTextField_SIFRA_RADNIKAKeyReleased

    private void jTextField_RADNI_NALOGKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_RADNI_NALOGKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jTextField_RADNI_NALOG.getText().length() > 0) {
                jButton_EVIDENCIJA.setEnabled(true);
                jButton_EVIDENCIJA.requestFocus();

                jTextField_SIFRA_RADNIKA.setEnabled(false);
                jTextField_SIFRA_STATUSA.setEnabled(false);
                jTextField_RADNI_NALOG.setEnabled(false);
            } else {
                // jTextField_RADNIK.setText("");
                jButton_EVIDENCIJA.setEnabled(false);

                Display.obavestenjeBaloncic("Greška", "Mora se Uneti Radni nalog !", Display.TIP_OBAVESTENJA.GRESKA);
            }

            jButton_EVIDENCIJA_ODUSTAJANJE.setEnabled(true);
        }
    }//GEN-LAST:event_jTextField_RADNI_NALOGKeyReleased

    private void jButton_EVIDENCIJA_ODUSTAJANJEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_EVIDENCIJA_ODUSTAJANJEActionPerformed
        // TODO add your handling code here:
        resetKontola();
    }//GEN-LAST:event_jButton_EVIDENCIJA_ODUSTAJANJEActionPerformed

    private void jButton_EVIDENCIJAKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton_EVIDENCIJAKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jButton_EVIDENCIJAActionPerformed(null);
        }
    }//GEN-LAST:event_jButton_EVIDENCIJAKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_EVIDENCIJA;
    private javax.swing.JButton jButton_EVIDENCIJA_ODUSTAJANJE;
    private javax.swing.JComboBox jComboBox_STATUSI;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JTextField jTextField_FIRMA;
    private javax.swing.JTextField jTextField_ORGJED;
    private javax.swing.JTextField jTextField_RADNIK;
    private javax.swing.JTextField jTextField_RADNI_NALOG;
    private javax.swing.JTextField jTextField_SIFRA_RADNIKA;
    private javax.swing.JTextField jTextField_SIFRA_STATUSA;
    private javax.swing.JTextField jTextField_SIFRA_STATUSA_NALOGA;
    private javax.swing.JTextField jTextField_STATUS_NALOGA_OPIS;
    private org.openide.explorer.view.OutlineView outlineViewEvidencijeRadnikaZaDan;
    // End of variables declaration//GEN-END:variables

    //<editor-fold defaultstate="collapsed" desc="Lookup">
    @Override
    public void componentOpened() {
        odabraneFirme = Utilities.actionsGlobalContext().lookupResult(Firma.class);
        odabraneOrgJed = Utilities.actionsGlobalContext().lookupResult(Orgjed.class);
        odabraniRadnici = Utilities.actionsGlobalContext().lookupResult(Radnik.class);
        odabraniDatum = Utilities.actionsGlobalContext().lookupResult(String.class);

        llOdabraneFirme = new LookupListener() {
            @Override
            public void resultChanged(LookupEvent le) {
                Lookup.Result f = (Lookup.Result) le.getSource();
                Collection<Firma> firme = f.allInstances();

                if (!firme.isEmpty()) {
                    for (Firma f1 : firme) {
                        setFirma_bind(f1);
                        displayFirmaUI(firma_bind);
                    }
                }
            }
        };
        odabraneFirme.addLookupListener(llOdabraneFirme);

        llOdabraneOrgJed = new LookupListener() {
            @Override
            public void resultChanged(LookupEvent le) {
                Lookup.Result o = (Lookup.Result) le.getSource();
                Collection<Orgjed> orgJedinice = o.allInstances();

                if (!orgJedinice.isEmpty()) {
                    for (Orgjed o1 : orgJedinice) {
                        setOrgJed_bind(o1);
                        setFirma_bind(o1.getFKIDFirma());

                        displayOrgJedUI(o1);
                    }
                }
            }
        };
        odabraneOrgJed.addLookupListener(llOdabraneOrgJed);

        llOdabraniRadnici = new LookupListener() {
            @Override
            public void resultChanged(LookupEvent le) {
                Lookup.Result lr = (Lookup.Result) le.getSource();
                Collection<Radnik> radnici = lr.allInstances();

                if (!radnici.isEmpty()) {
                    for (Radnik r1 : radnici) {
                        setUpRadnik(r1);

                        displayRadnikUI(r1);
                        displayOrgJedUI(orgJed_bind);
                        displayFirmaUI(firma_bind);

                        refreshOutlineViewEvidencija(radnik_bind, datum_bind);
                    }
                }
            }
        };
        odabraniRadnici.addLookupListener(llOdabraniRadnici);

        llOdabraniDatum = new LookupListener() {
            @Override
            public void resultChanged(LookupEvent le) {
                Lookup.Result lr = (Lookup.Result) le.getSource();
                Collection<String> datumi = lr.allInstances();

                if (!datumi.isEmpty()) {
                    for (String d1 : datumi) {
                        setDatum_bind(d1);

                        refreshOutlineViewEvidencija(radnik_bind, datum_bind);
                    }
                }
            }
        };
        odabraniDatum.addLookupListener(llOdabraniDatum);
    }

    @Override
    public void componentClosed() {
        odabraneFirme.removeLookupListener(llOdabraneFirme);
        odabraneFirme = null;

        odabraneOrgJed.removeLookupListener(llOdabraneOrgJed);
        odabraneOrgJed = null;

        odabraniRadnici.removeLookupListener(llOdabraniRadnici);
        odabraniRadnici = null;

        odabraniDatum.removeLookupListener(llOdabraniDatum);
        odabraniDatum = null;
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
    }

    @Override
    public ExplorerManager getExplorerManager() {
        return em;
    }
    //</editor-fold>

    private boolean proveraZaUpis(Radnik radnik, String datum_bind, Statusi status, String nalog)
            throws DatumException, NoResultException, NumberFormatException, NalogException, Exception {

        boolean UpisStatusaIspravan = false;

        if (radnik == null || !radnik.getAktivan()) {
            throw new RadnikException("Dozvoljen Je Samo Upis Aktivno Zaposlenog Radnika.");
        } else {
            UpisStatusaIspravan = true;
        }

        if (datum_bind.isEmpty()) {
            throw new DatumException("Mora se izabrati datum !");
        } else {
            UpisStatusaIspravan = true;
        }

        if (status.getUnosNaloga() && nalog.isEmpty()) {
            throw new NalogException("Za Izabrani Status Se Mora Uneti Broj Radnog Naloga.");
        } else {
            UpisStatusaIspravan = true;
        }

        return UpisStatusaIspravan;
    }

    @Override
    protected void componentActivated() {
        jTextField_SIFRA_RADNIKA.requestFocus();
        componentOpened();
    }

    @Override
    public void requestActive() {
        QSRadnik();
    }

    //<editor-fold defaultstate="collapsed" desc="UI kontrole">
    private void resetKontola() {
        jTextField_FIRMA.setText("");
        jTextField_ORGJED.setText("");

        jTextField_SIFRA_STATUSA.setText("");
        jTextField_RADNI_NALOG.setText("");
        jTextField_RADNIK.setText("");
        jTextField_SIFRA_STATUSA.setText("");
        jTextField_SIFRA_STATUSA_NALOGA.setText("");
        jTextField_STATUS_NALOGA_OPIS.setText("");

        jButton_EVIDENCIJA.setEnabled(false);
        jButton_EVIDENCIJA_ODUSTAJANJE.setEnabled(false);
        jTextField_SIFRA_STATUSA.setEnabled(false);
        jTextField_RADNI_NALOG.setEnabled(false);

        jTextField_SIFRA_RADNIKA.setEnabled(true);
        jTextField_SIFRA_RADNIKA.setText("");
        jTextField_SIFRA_RADNIKA.requestFocus();
    }

    private void displayUI() {
        displayRadnikUI(radnik_bind);
        displayOrgJedUI(orgJed_bind);
        displayFirmaUI(firma_bind);
    }

    private void displayFirmaUI(Firma firma) {
        jTextField_FIRMA.setText(firma == null ? "" : firma_bind.getNaziv());
    }

    private void displayOrgJedUI(Orgjed orgjed) {
        jTextField_ORGJED.setText(orgjed == null ? "" : orgjed.getNaziv());
    }

    private void displayRadnikUI(Radnik r1) {
        jTextField_SIFRA_RADNIKA.setText(r1 != null ? Long.toString(r1.getIDRadnik()) : "");
        jTextField_RADNIK.setText(r1 != null ? r1.getIme() + " " + r1.getPrezime() : "");
    }

    private void displayStatusUI(Statusi status) {
        jTextField_SIFRA_STATUSA_NALOGA.setText(status != null ? status.getStatus() : "");
        jTextField_STATUS_NALOGA_OPIS.setText(status != null ? status.getZnacenje() : "");
    }

    private void setUpRadnik(Radnik r1) {
        setRadnik_bind(r1);
        setOrgJed_bind(radnik_bind.getFKIDOrgjed());
        setFirma_bind(orgJed_bind.getFKIDFirma());

    }
    //</editor-fold>

    private void QSRadnik() {
        IRadnik ir = Lookup.getDefault().lookup(IRadnik.class);

        setDatum_bind(datum_bind == null ? new SimpleDateFormat("yyyy-MM-dd").format(new Date()) : datum_bind);

        if (ir.getRadnik() != null) {
            Radnik r = ir.getRadnik();

            setUpRadnik(r);
            displayUI();

            jTextField_SIFRA_RADNIKAKeyReleased(new KeyEvent(this, 1, 1, 0x1, VK_ENTER, Character.MIN_VALUE));
            refreshOutlineViewEvidencija(radnik_bind, datum_bind);
        }
    }
}
