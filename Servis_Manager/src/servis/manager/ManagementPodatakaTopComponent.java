/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servis.manager;

import ERS.queries.ERSQuery;
import com.dobrivoje.utilities.comboboxmodeli.FirmaComboBoxModel;
import com.dobrivoje.utilities.comboboxmodeli.KompanijaComboBoxModel;
import com.dobrivoje.utilities.comboboxmodeli.OrgJedComboBoxModel;
import com.dobrivoje.utilities.comboboxmodeli.TipRadnikaComboBoxModel;
import com.dobrivoje.utilities.warnings.Display;
import ent.Firma;
import ent.Kompanija;
import ent.Orgjed;
import ent.Radnik;
import ent.TipRadnika;
import izvestaji.resursi.generatori.ReportGenerator;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collection;
import javax.persistence.EntityManager;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;
import javax.persistence.RollbackException;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//servis.manager//ManagementPodataka//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "ManagementPodatakaTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window/Servis/Manager", id = "servis.manager.ManagementPodatakaTopComponent")
@ActionReference(path = "Menu/Window/Servis/Manager" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_ManagementPodatakaAction",
        preferredID = "ManagementPodatakaTopComponent"
)
@Messages({
    "CTL_ManagementPodatakaAction=Management Podataka",
    "CTL_ManagementPodatakaTopComponent=Management Podataka",
    "HINT_ManagementPodatakaTopComponent=Upravljanje Podacima"
})
public final class ManagementPodatakaTopComponent extends TopComponent
        implements LookupListener {

    private Lookup.Result<Kompanija> kompanijaLookup = null;
    private Lookup.Result<Firma> firmaLookup = null;
    private Lookup.Result<Orgjed> orgJedLookup = null;
    private Lookup.Result<Radnik> radnikLookup = null;

    private static EntityManager em;
    private static ReportGenerator reportGenerator;

    //<editor-fold defaultstate="collapsed" desc="Kompanija Bind">
    private Kompanija kompanija_bind;

    public static final String PROP_KOMPANIJA_BIND = "kompanija_bind";

    /**
     * Get the value of kompanija_bind
     *
     * @return the value of kompanija_bind
     */
    public Kompanija getKompanija_bind() {
        return kompanija_bind;
    }

    /**
     * Set the value of kompanija_bind
     *
     * @param kompanija_bind new value of kompanija_bind
     */
    public void setKompanija_bind(Kompanija kompanija_bind) {
        Kompanija oldKompanija_bind = this.kompanija_bind;
        this.kompanija_bind = kompanija_bind;

        propertyChangeSupport.firePropertyChange(PROP_KOMPANIJA_BIND, oldKompanija_bind, kompanija_bind);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Firma Bind">
    private Firma firma_bind;

    public static final String PROP_FIRMA_BIND = "firma_bind";

    /**
     * Get the value of firma_bind
     *
     * @return the value of firma_bind
     */
    public Firma getFirma_bind() {
        return firma_bind;
    }

    /**
     * Set the value of firma_bind
     *
     * @param firma_bind new value of firma_bind
     */
    public void setFirma_bind(Firma firma_bind) {
        Firma oldFirma_bind = this.firma_bind;
        this.firma_bind = firma_bind;
        propertyChangeSupport.firePropertyChange(PROP_FIRMA_BIND, oldFirma_bind, firma_bind);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="OrgJed Bind">
    private Orgjed orgJed_bind;

    public static final String PROP_ORGJED_BIND = "orgJed_bind";

    /**
     * Get the value of orgJed_bind
     *
     * @return the value of orgJed_bind
     */
    public Orgjed getOrgJed_bind() {
        return orgJed_bind;
    }

    /**
     * Set the value of orgJed_bind
     *
     * @param orgJed_bind new value of orgJed_bind
     */
    public void setOrgJed_bind(Orgjed orgJed_bind) {
        Orgjed oldOrgJed_bind = this.orgJed_bind;
        this.orgJed_bind = orgJed_bind;
        propertyChangeSupport.firePropertyChange(PROP_ORGJED_BIND, oldOrgJed_bind, orgJed_bind);
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

    //<editor-fold defaultstate="collapsed" desc="Radnik Bind">
    private Radnik radnik_bind;

    public static final String PROP_RADNIK_BIND = "radnik_bind";

    /**
     * Get the value of radnik_bind
     *
     * @return the value of radnik_bind
     */
    public Radnik getRadnik_bind() {
        return radnik_bind;
    }

    /**
     * Set the value of radnik_bind
     *
     * @param radnik_bind new value of radnik_bind
     */
    public void setRadnik_bind(Radnik radnik_bind) {
        Radnik oldRadnik_bind = this.radnik_bind;
        this.radnik_bind = radnik_bind;
        propertyChangeSupport.firePropertyChange(PROP_RADNIK_BIND, oldRadnik_bind, radnik_bind);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="TipRadnika Bind">
    private TipRadnika tipRadnika_bind;

    public static final String PROP_TIPRADNIKA_BIND = "tipRadnika_bind";

    /**
     * Get the value of tipRadnika_bind
     *
     * @return the value of tipRadnika_bind
     */
    public TipRadnika getTipRadnika_bind() {
        return tipRadnika_bind;
    }

    /**
     * Set the value of tipRadnika_bind
     *
     * @param tipRadnika_bind new value of tipRadnika_bind
     */
    public void setTipRadnika_bind(TipRadnika tipRadnika_bind) {
        TipRadnika oldTipRadnika_bind = this.tipRadnika_bind;
        this.tipRadnika_bind = tipRadnika_bind;
        propertyChangeSupport.firePropertyChange(PROP_TIPRADNIKA_BIND, oldTipRadnika_bind, tipRadnika_bind);
    }
//</editor-fold>

    public ManagementPodatakaTopComponent() {
        initComponents();
        setName(Bundle.CTL_ManagementPodatakaTopComponent());
        setToolTipText(Bundle.HINT_ManagementPodatakaTopComponent());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        buttonGroup_Barkod = new javax.swing.ButtonGroup();
        jLabel_Naslov = new javax.swing.JLabel();
        jTP_DataManagement = new javax.swing.JTabbedPane();
        jPanel_Kompanija = new javax.swing.JPanel();
        jTextField_KOMPANIJA_Naziv = new javax.swing.JTextField();
        jTextField_KOMPANIJA_Adresa = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jButton_KOMPANIJA_IzmenaPodataka = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jTextField_KOMPANIJA_Grad = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jTextField_KOMPANIJA__Vlasnik = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jPanel_Firma = new javax.swing.JPanel();
        jTextField_FIRMA_Kompanija = new javax.swing.JTextField();
        jTextField_FIRMA_Naziv = new javax.swing.JTextField();
        jTextField_FIRMA_Grad = new javax.swing.JTextField();
        jTextField_FIRMA_Adresa = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jButton_FIRMA_Nova = new javax.swing.JButton();
        jButton_FIRMA_IzmenaPodataka = new javax.swing.JButton();
        jTextField_FIRMA_PIB = new javax.swing.JTextField();
        jTextField_FIRMA_MATBROJ = new javax.swing.JTextField();
        jTextField_FIRMA_PostanskiBroj = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jCheckBox_FIRMA_Aktivna = new javax.swing.JCheckBox();
        jPanel_Barkod = new javax.swing.JPanel();
        jRadioButton_Barkod_Radnici = new javax.swing.JRadioButton();
        jRadioButton_Barkod_Statusi = new javax.swing.JRadioButton();
        jButton_Barkod = new javax.swing.JButton();
        jPanel_OrgJed = new javax.swing.JPanel();
        jTextField_ORGJED_Kompanija = new javax.swing.JTextField();
        jComboBox_ORGJED_Kompanija = new javax.swing.JComboBox();
        jTextField_ORGJED_Firma = new javax.swing.JTextField();
        jComboBox_ORGJED_Firma = new javax.swing.JComboBox();
        jTextField_ORGJED_Naziv = new javax.swing.JTextField();
        jTextField_ORGJED_Sifra = new javax.swing.JTextField();
        jCheckBox_ORGJED_Mehanika = new javax.swing.JCheckBox();
        jPanel5 = new javax.swing.JPanel();
        jButton_ORGJED_Nova = new javax.swing.JButton();
        jButton_ORGJED_IzmenaPodataka = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jPanel_Radnik = new javax.swing.JPanel();
        jTextField_Radnik_Ime = new javax.swing.JTextField();
        jTextField_Radnik_Prezime = new javax.swing.JTextField();
        jTextField_Radnik_OrgJed = new javax.swing.JTextField();
        jComboBox_RADNICI_OrgJed = new javax.swing.JComboBox();
        jTextField_Radnik_TipRadnika = new javax.swing.JTextField();
        jComboBox_RADNICI_TipRadnika = new javax.swing.JComboBox();
        jTextField_Radnik_Sifra_INFSISTEM = new javax.swing.JTextField();
        jCheckBox_Radnik_Aktivan = new javax.swing.JCheckBox();
        jCheckBox_Radnik_Radnik = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jButton_RADNIK_Novi = new javax.swing.JButton();
        jButton_RADNIK_IzmenaPodataka = new javax.swing.JButton();
        jButton_RADNIK_Brisanje = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();

        jLabel_Naslov.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel_Naslov, org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jLabel_Naslov.text")); // NOI18N

        jPanel_Kompanija.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jTextField_KOMPANIJA_Naziv.setText(org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jTextField_KOMPANIJA_Naziv.text")); // NOI18N

        jTextField_KOMPANIJA_Adresa.setText(org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jTextField_KOMPANIJA_Adresa.text")); // NOI18N

        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        org.openide.awt.Mnemonics.setLocalizedText(jButton_KOMPANIJA_IzmenaPodataka, org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jButton_KOMPANIJA_IzmenaPodataka.text")); // NOI18N
        jButton_KOMPANIJA_IzmenaPodataka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_KOMPANIJA_IzmenaPodatakaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton_KOMPANIJA_IzmenaPodataka)
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(37, Short.MAX_VALUE)
                .addComponent(jButton_KOMPANIJA_IzmenaPodataka)
                .addGap(31, 31, 31))
        );

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel20, org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jLabel20.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel21, org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jLabel21.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel24, org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jLabel24.text")); // NOI18N

        jTextField_KOMPANIJA_Grad.setText(org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jTextField_KOMPANIJA_Grad.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel28, org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jLabel28.text")); // NOI18N

        jTextField_KOMPANIJA__Vlasnik.setText(org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jTextField_KOMPANIJA__Vlasnik.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel29, org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jLabel29.text")); // NOI18N

        javax.swing.GroupLayout jPanel_KompanijaLayout = new javax.swing.GroupLayout(jPanel_Kompanija);
        jPanel_Kompanija.setLayout(jPanel_KompanijaLayout);
        jPanel_KompanijaLayout.setHorizontalGroup(
            jPanel_KompanijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_KompanijaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_KompanijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator4)
                    .addComponent(jSeparator6)
                    .addGroup(jPanel_KompanijaLayout.createSequentialGroup()
                        .addGroup(jPanel_KompanijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addGroup(jPanel_KompanijaLayout.createSequentialGroup()
                                .addGroup(jPanel_KompanijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                                    .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel_KompanijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField_KOMPANIJA_Naziv, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField_KOMPANIJA_Adresa, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField_KOMPANIJA_Grad, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField_KOMPANIJA__Vlasnik, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(106, Short.MAX_VALUE))
        );
        jPanel_KompanijaLayout.setVerticalGroup(
            jPanel_KompanijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_KompanijaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_KompanijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_KompanijaLayout.createSequentialGroup()
                        .addGroup(jPanel_KompanijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_KOMPANIJA_Naziv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_KompanijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_KOMPANIJA_Adresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_KompanijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_KOMPANIJA_Grad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_KompanijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_KOMPANIJA__Vlasnik, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        jTP_DataManagement.addTab(org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jPanel_Kompanija.TabConstraints.tabTitle"), jPanel_Kompanija); // NOI18N

        jPanel_Firma.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jTextField_FIRMA_Kompanija.setEditable(false);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${kompanija_bind.nazivKompanije}"), jTextField_FIRMA_Kompanija, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jTextField_FIRMA_Naziv.setText(org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jTextField_FIRMA_Naziv.text")); // NOI18N

        jTextField_FIRMA_Grad.setText(org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jTextField_FIRMA_Grad.text")); // NOI18N

        jTextField_FIRMA_Adresa.setText(org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jTextField_FIRMA_Adresa.text")); // NOI18N

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        org.openide.awt.Mnemonics.setLocalizedText(jButton_FIRMA_Nova, org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jButton_FIRMA_Nova.text")); // NOI18N
        jButton_FIRMA_Nova.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_FIRMA_NovaActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jButton_FIRMA_IzmenaPodataka, org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jButton_FIRMA_IzmenaPodataka.text")); // NOI18N
        jButton_FIRMA_IzmenaPodataka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_FIRMA_IzmenaPodatakaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton_FIRMA_IzmenaPodataka, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton_FIRMA_Nova, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addComponent(jButton_FIRMA_Nova)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_FIRMA_IzmenaPodataka)
                .addGap(20, 20, 20))
        );

        jTextField_FIRMA_PIB.setText(org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jTextField_FIRMA_PIB.text")); // NOI18N

        jTextField_FIRMA_MATBROJ.setText(org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jTextField_FIRMA_MATBROJ.text")); // NOI18N

        jTextField_FIRMA_PostanskiBroj.setText(org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jTextField_FIRMA_PostanskiBroj.text")); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jLabel2.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jLabel3.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jLabel4.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jLabel5.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel6, org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jLabel6.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel7, org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jLabel7.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel8, org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jLabel8.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox_FIRMA_Aktivna, org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jCheckBox_FIRMA_Aktivna.text")); // NOI18N
        jCheckBox_FIRMA_Aktivna.setToolTipText(org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jCheckBox_FIRMA_Aktivna.toolTipText")); // NOI18N

        jPanel_Barkod.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jPanel_Barkod.border.title"))); // NOI18N

        buttonGroup_Barkod.add(jRadioButton_Barkod_Radnici);
        jRadioButton_Barkod_Radnici.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jRadioButton_Barkod_Radnici, org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jRadioButton_Barkod_Radnici.text")); // NOI18N

        buttonGroup_Barkod.add(jRadioButton_Barkod_Statusi);
        org.openide.awt.Mnemonics.setLocalizedText(jRadioButton_Barkod_Statusi, org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jRadioButton_Barkod_Statusi.text")); // NOI18N

        javax.swing.GroupLayout jPanel_BarkodLayout = new javax.swing.GroupLayout(jPanel_Barkod);
        jPanel_Barkod.setLayout(jPanel_BarkodLayout);
        jPanel_BarkodLayout.setHorizontalGroup(
            jPanel_BarkodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_BarkodLayout.createSequentialGroup()
                .addGroup(jPanel_BarkodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton_Barkod_Radnici)
                    .addComponent(jRadioButton_Barkod_Statusi))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        jPanel_BarkodLayout.setVerticalGroup(
            jPanel_BarkodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_BarkodLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jRadioButton_Barkod_Radnici, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton_Barkod_Statusi, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );

        org.openide.awt.Mnemonics.setLocalizedText(jButton_Barkod, org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jButton_Barkod.text")); // NOI18N
        jButton_Barkod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_BarkodActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_FirmaLayout = new javax.swing.GroupLayout(jPanel_Firma);
        jPanel_Firma.setLayout(jPanel_FirmaLayout);
        jPanel_FirmaLayout.setHorizontalGroup(
            jPanel_FirmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_FirmaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_FirmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_FirmaLayout.createSequentialGroup()
                        .addGroup(jPanel_FirmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel_FirmaLayout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField_FIRMA_Kompanija, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel_FirmaLayout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField_FIRMA_PostanskiBroj, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jCheckBox_FIRMA_Aktivna)))
                        .addGap(254, 266, Short.MAX_VALUE))
                    .addGroup(jPanel_FirmaLayout.createSequentialGroup()
                        .addComponent(jSeparator1)
                        .addContainerGap())
                    .addGroup(jPanel_FirmaLayout.createSequentialGroup()
                        .addGroup(jPanel_FirmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_FirmaLayout.createSequentialGroup()
                                .addGroup(jPanel_FirmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel_FirmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField_FIRMA_Grad, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField_FIRMA_Adresa, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel_FirmaLayout.createSequentialGroup()
                                        .addComponent(jTextField_FIRMA_Naziv, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel_FirmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel_FirmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextField_FIRMA_PIB, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                                            .addComponent(jTextField_FIRMA_MATBROJ)))
                                    .addGroup(jPanel_FirmaLayout.createSequentialGroup()
                                        .addGap(335, 335, 335)
                                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel_FirmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jButton_Barkod, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel_Barkod, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel_FirmaLayout.setVerticalGroup(
            jPanel_FirmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_FirmaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_FirmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_FirmaLayout.createSequentialGroup()
                        .addGroup(jPanel_FirmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTextField_FIRMA_Kompanija, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_FirmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_FIRMA_Naziv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel_FirmaLayout.createSequentialGroup()
                        .addGroup(jPanel_FirmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_FIRMA_PIB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_FirmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_FIRMA_MATBROJ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_FirmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_FirmaLayout.createSequentialGroup()
                        .addGroup(jPanel_FirmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_FIRMA_Grad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_FirmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_FIRMA_Adresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel_FirmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_FIRMA_PostanskiBroj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBox_FIRMA_Aktivna)))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel_Barkod, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_Barkod)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTP_DataManagement.addTab(org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jPanel_Firma.TabConstraints.tabTitle"), jPanel_Firma); // NOI18N

        jPanel_OrgJed.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jTextField_ORGJED_Kompanija.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${kompanija_bind.nazivKompanije}"), jTextField_ORGJED_Kompanija, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jComboBox_ORGJED_Kompanija.setModel(new KompanijaComboBoxModel());
        jComboBox_ORGJED_Kompanija.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_ORGJED_KompanijaActionPerformed(evt);
            }
        });

        jTextField_ORGJED_Firma.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${firma_bind.naziv}"), jTextField_ORGJED_Firma, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jComboBox_ORGJED_Firma.setModel(new FirmaComboBoxModel());
        jComboBox_ORGJED_Firma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_ORGJED_FirmaActionPerformed(evt);
            }
        });

        jTextField_ORGJED_Naziv.setText(org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jTextField_ORGJED_Naziv.text")); // NOI18N

        jTextField_ORGJED_Sifra.setText(org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jTextField_ORGJED_Sifra.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox_ORGJED_Mehanika, org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jCheckBox_ORGJED_Mehanika.text")); // NOI18N
        jCheckBox_ORGJED_Mehanika.setToolTipText(org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jCheckBox_ORGJED_Mehanika.toolTipText")); // NOI18N

        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        org.openide.awt.Mnemonics.setLocalizedText(jButton_ORGJED_Nova, org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jButton_ORGJED_Nova.text")); // NOI18N
        jButton_ORGJED_Nova.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ORGJED_NovaActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jButton_ORGJED_IzmenaPodataka, org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jButton_ORGJED_IzmenaPodataka.text")); // NOI18N
        jButton_ORGJED_IzmenaPodataka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ORGJED_IzmenaPodatakaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton_ORGJED_IzmenaPodataka, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton_ORGJED_Nova, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton_ORGJED_Nova)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_ORGJED_IzmenaPodataka)
                .addGap(20, 20, 20))
        );

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel15, org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jLabel15.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel16, org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jLabel16.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel17, org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jLabel17.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel18, org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jLabel18.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel19, org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jLabel19.text")); // NOI18N

        javax.swing.GroupLayout jPanel_OrgJedLayout = new javax.swing.GroupLayout(jPanel_OrgJed);
        jPanel_OrgJed.setLayout(jPanel_OrgJedLayout);
        jPanel_OrgJedLayout.setHorizontalGroup(
            jPanel_OrgJedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_OrgJedLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_OrgJedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_OrgJedLayout.createSequentialGroup()
                        .addGroup(jPanel_OrgJedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator5)
                            .addGroup(jPanel_OrgJedLayout.createSequentialGroup()
                                .addGroup(jPanel_OrgJedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel15)
                                    .addGroup(jPanel_OrgJedLayout.createSequentialGroup()
                                        .addGroup(jPanel_OrgJedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel_OrgJedLayout.createSequentialGroup()
                                                .addGroup(jPanel_OrgJedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel_OrgJedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jTextField_ORGJED_Kompanija)
                                                    .addComponent(jTextField_ORGJED_Firma)))
                                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(8, 8, 8)
                                        .addGroup(jPanel_OrgJedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jComboBox_ORGJED_Firma, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jComboBox_ORGJED_Kompanija, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(22, 22, 22))
                    .addGroup(jPanel_OrgJedLayout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addGroup(jPanel_OrgJedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField_ORGJED_Naziv)
                            .addGroup(jPanel_OrgJedLayout.createSequentialGroup()
                                .addComponent(jTextField_ORGJED_Sifra, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(60, 60, 60)
                                .addComponent(jCheckBox_ORGJED_Mehanika)))
                        .addContainerGap(248, Short.MAX_VALUE))))
        );
        jPanel_OrgJedLayout.setVerticalGroup(
            jPanel_OrgJedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_OrgJedLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_OrgJedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel_OrgJedLayout.createSequentialGroup()
                        .addGroup(jPanel_OrgJedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(jTextField_ORGJED_Kompanija, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox_ORGJED_Kompanija, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_OrgJedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_ORGJED_Firma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox_ORGJED_Firma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_OrgJedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_ORGJED_Naziv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_OrgJedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_ORGJED_Sifra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBox_ORGJED_Mehanika)))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(36, 36, 36)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(94, 94, 94))
        );

        jTP_DataManagement.addTab(org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jPanel_OrgJed.TabConstraints.tabTitle"), jPanel_OrgJed); // NOI18N

        jTextField_Radnik_Ime.setText(org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jTextField_Radnik_Ime.text")); // NOI18N

        jTextField_Radnik_Prezime.setText(org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jTextField_Radnik_Prezime.text")); // NOI18N

        jTextField_Radnik_OrgJed.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${orgJed_bind.naziv}"), jTextField_Radnik_OrgJed, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jComboBox_RADNICI_OrgJed.setModel(new OrgJedComboBoxModel());
        jComboBox_RADNICI_OrgJed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_RADNICI_OrgJedActionPerformed(evt);
            }
        });

        jTextField_Radnik_TipRadnika.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${tipRadnika_bind.naziv}"), jTextField_Radnik_TipRadnika, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jComboBox_RADNICI_TipRadnika.setModel(new TipRadnikaComboBoxModel());
        jComboBox_RADNICI_TipRadnika.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_RADNICI_TipRadnikaActionPerformed(evt);
            }
        });
        jComboBox_RADNICI_TipRadnika.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jComboBox_RADNICI_TipRadnikaPropertyChange(evt);
            }
        });

        jTextField_Radnik_Sifra_INFSISTEM.setText(org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jTextField_Radnik_Sifra_INFSISTEM.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox_Radnik_Aktivan, org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jCheckBox_Radnik_Aktivan.text")); // NOI18N
        jCheckBox_Radnik_Aktivan.setToolTipText(org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jCheckBox_Radnik_Aktivan.toolTipText")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox_Radnik_Radnik, org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jCheckBox_Radnik_Radnik.text")); // NOI18N
        jCheckBox_Radnik_Radnik.setToolTipText(org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jCheckBox_Radnik_Radnik.toolTipText")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel9, org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jLabel9.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel10, org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jLabel10.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel11, org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jLabel11.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel12, org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jLabel12.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel13, org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jLabel13.text")); // NOI18N

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        org.openide.awt.Mnemonics.setLocalizedText(jButton_RADNIK_Novi, org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jButton_RADNIK_Novi.text")); // NOI18N
        jButton_RADNIK_Novi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_RADNIK_NoviActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jButton_RADNIK_IzmenaPodataka, org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jButton_RADNIK_IzmenaPodataka.text")); // NOI18N
        jButton_RADNIK_IzmenaPodataka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_RADNIK_IzmenaPodatakaActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jButton_RADNIK_Brisanje, org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jButton_RADNIK_Brisanje.text")); // NOI18N
        jButton_RADNIK_Brisanje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_RADNIK_BrisanjeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton_RADNIK_Novi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton_RADNIK_IzmenaPodataka, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton_RADNIK_Brisanje, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jButton_RADNIK_Novi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_RADNIK_IzmenaPodataka)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_RADNIK_Brisanje)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel14, org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jLabel14.text")); // NOI18N

        javax.swing.GroupLayout jPanel_RadnikLayout = new javax.swing.GroupLayout(jPanel_Radnik);
        jPanel_Radnik.setLayout(jPanel_RadnikLayout);
        jPanel_RadnikLayout.setHorizontalGroup(
            jPanel_RadnikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_RadnikLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_RadnikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3)
                    .addGroup(jPanel_RadnikLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel_RadnikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_RadnikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_RadnikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField_Radnik_Prezime, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel_RadnikLayout.createSequentialGroup()
                                .addGroup(jPanel_RadnikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextField_Radnik_TipRadnika, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField_Radnik_Sifra_INFSISTEM, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField_Radnik_OrgJed, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel_RadnikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox_RADNICI_OrgJed, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBox_RADNICI_TipRadnika, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jTextField_Radnik_Ime))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31))
                    .addGroup(jPanel_RadnikLayout.createSequentialGroup()
                        .addGroup(jPanel_RadnikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_RadnikLayout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(jCheckBox_Radnik_Aktivan)
                                .addGap(11, 11, 11)
                                .addComponent(jCheckBox_Radnik_Radnik))
                            .addComponent(jLabel14))
                        .addGap(0, 411, Short.MAX_VALUE))))
        );
        jPanel_RadnikLayout.setVerticalGroup(
            jPanel_RadnikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_RadnikLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel14)
                .addGap(9, 9, 9)
                .addGroup(jPanel_RadnikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_RadnikLayout.createSequentialGroup()
                        .addGroup(jPanel_RadnikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField_Radnik_Ime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_RadnikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField_Radnik_Prezime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_RadnikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField_Radnik_OrgJed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(jComboBox_RADNICI_OrgJed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_RadnikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField_Radnik_TipRadnika, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)
                            .addComponent(jComboBox_RADNICI_TipRadnika, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_RadnikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField_Radnik_Sifra_INFSISTEM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addGroup(jPanel_RadnikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox_Radnik_Aktivan)
                    .addComponent(jCheckBox_Radnik_Radnik))
                .addContainerGap(131, Short.MAX_VALUE))
        );

        jTP_DataManagement.addTab(org.openide.util.NbBundle.getMessage(ManagementPodatakaTopComponent.class, "ManagementPodatakaTopComponent.jPanel_Radnik.TabConstraints.tabTitle"), jPanel_Radnik); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_Naslov, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTP_DataManagement)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel_Naslov, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTP_DataManagement))
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_FIRMA_NovaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_FIRMA_NovaActionPerformed
        try {
            ERSQuery.novaFirma(
                    kompanija_bind,
                    jTextField_FIRMA_Naziv.getText(),
                    jTextField_FIRMA_Grad.getText(),
                    jTextField_FIRMA_Adresa.getText(),
                    jTextField_FIRMA_PostanskiBroj.getText(),
                    jTextField_FIRMA_PIB.getText(),
                    jTextField_FIRMA_MATBROJ.getText(),
                    jCheckBox_FIRMA_Aktivna.isSelected());

            Display.obavestenjeBaloncic("Obaveštenje.", "Nova Firma Je Uspešno Uneta.", Display.TIP_OBAVESTENJA.INFORMATIVNO);
        } catch (RollbackException e1) {
            Display.obavestenjeBaloncic("Greška.", "Firma Sa Ovim Podacima Već Postoji!", Display.TIP_OBAVESTENJA.GRESKA);
        } catch (Exception ex) {
            Display.obavestenjeBaloncic("Greška.", ex.getMessage(), Display.TIP_OBAVESTENJA.GRESKA);
        }
    }//GEN-LAST:event_jButton_FIRMA_NovaActionPerformed

    private void jButton_FIRMA_IzmenaPodatakaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_FIRMA_IzmenaPodatakaActionPerformed
        try {
            ERSQuery.updateFirma(
                    kompanija_bind,
                    firma_bind,
                    jTextField_FIRMA_Naziv.getText(),
                    jTextField_FIRMA_Grad.getText(),
                    jTextField_FIRMA_Adresa.getText(),
                    jTextField_FIRMA_PostanskiBroj.getText(),
                    jTextField_FIRMA_PIB.getText(),
                    jTextField_FIRMA_MATBROJ.getText(),
                    jCheckBox_FIRMA_Aktivna.isSelected());

            Display.obavestenjeBaloncic("Obaveštenje.", "Firma Je Ažurirana !", Display.TIP_OBAVESTENJA.INFORMATIVNO);
        } catch (RollbackException e1) {
            Display.obavestenjeBaloncic("Greška.", e1.getMessage(), Display.TIP_OBAVESTENJA.GRESKA);
        } catch (Exception e) {
            Display.obavestenjeBaloncic("Greška.", e.getMessage(), Display.TIP_OBAVESTENJA.GRESKA);
        }
    }

    private void firmaInsertUpdate(boolean insert) {
    }//GEN-LAST:event_jButton_FIRMA_IzmenaPodatakaActionPerformed

    private void jButton_RADNIK_NoviActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_RADNIK_NoviActionPerformed
        try {
            ERSQuery.noviRadnik(
                    orgJed_bind,
                    tipRadnika_bind,
                    jTextField_Radnik_Ime.getText(),
                    jTextField_Radnik_Prezime.getText(),
                    0,
                    jCheckBox_Radnik_Aktivan.isSelected(),
                    null,
                    jTextField_Radnik_Sifra_INFSISTEM.getText());

            Display.obavestenjeBaloncic("Obaveštenje.", "Novi Radnik Je Uspešno Unet.", Display.TIP_OBAVESTENJA.INFORMATIVNO);
        } catch (RollbackException e1) {
            Display.obavestenjeBaloncic("Greška.", "Radnik Sa Ovim Podacima Već Postoji !", Display.TIP_OBAVESTENJA.GRESKA);
        } catch (NullPointerException e2) {
            Display.obavestenjeBaloncic("Greška.", e2.getMessage(), Display.TIP_OBAVESTENJA.UPOZORENJE);
        } catch (Exception e) {
            Display.obavestenjeBaloncic("Greška.", e.getMessage(), Display.TIP_OBAVESTENJA.GRESKA);
        }
    }

    private void radnikInsertUpdate(boolean insert) {
    }//GEN-LAST:event_jButton_RADNIK_NoviActionPerformed

    private void jButton_RADNIK_IzmenaPodatakaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_RADNIK_IzmenaPodatakaActionPerformed
        try {
            ERSQuery.updateRadnik(
                    radnik_bind,
                    orgJed_bind,
                    tipRadnika_bind,
                    jTextField_Radnik_Ime.getText(),
                    jTextField_Radnik_Prezime.getText(),
                    0,
                    jCheckBox_Radnik_Aktivan.isSelected(),
                    null,
                    jTextField_Radnik_Sifra_INFSISTEM.getText());

            Display.obavestenjeBaloncic("Obaveštenje.", "Radnik Je Uspešno Ažuriran.", Display.TIP_OBAVESTENJA.INFORMATIVNO);
        } catch (RollbackException e1) {
            Display.obavestenjeBaloncic("Greška.", e1.getMessage(), Display.TIP_OBAVESTENJA.GRESKA);
        } catch (NullPointerException e2) {
            Display.obavestenjeBaloncic("Greška.", e2.getMessage(), Display.TIP_OBAVESTENJA.UPOZORENJE);
        } catch (Exception e) {
            Display.obavestenjeBaloncic("Greška.", e.getMessage(), Display.TIP_OBAVESTENJA.GRESKA);
        }
    }//GEN-LAST:event_jButton_RADNIK_IzmenaPodatakaActionPerformed

    private void jButton_RADNIK_BrisanjeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_RADNIK_BrisanjeActionPerformed
        // TODO add your handling code here:
        try {
            Radnik r = em.find(Radnik.class, radnik_bind.getIDRadnik());

            em.getTransaction().begin();
            em.remove(r);
            em.getTransaction().commit();
            Display.obavestenjeBaloncic("Obaveštenje.", "Radnik Je Obrisan.", Display.TIP_OBAVESTENJA.INFORMATIVNO);
        } catch (NullPointerException e) {
            Display.obavestenjeBaloncic("Greška.", "Odaberite Radnika !", Display.TIP_OBAVESTENJA.GRESKA);
        } catch (Exception e) {
            Display.obavestenjeBaloncic("Greška.", e.getMessage(), Display.TIP_OBAVESTENJA.GRESKA);
        }
    }//GEN-LAST:event_jButton_RADNIK_BrisanjeActionPerformed

    private void jButton_ORGJED_NovaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ORGJED_NovaActionPerformed
        try {
            // TODO add your handling code here:
            ERSQuery.novaOrgJed(
                    firma_bind,
                    jTextField_ORGJED_Naziv.getText(),
                    jTextField_ORGJED_Sifra.getText(),
                    jCheckBox_ORGJED_Mehanika.isSelected());

            Display.obavestenjeBaloncic("Obaveštenje.", "Nova Org. Jedinica Je Uspešno Uneta.", Display.TIP_OBAVESTENJA.INFORMATIVNO);

        } catch (RollbackException e) {
            Display.obavestenjeBaloncic("Greška.", "Org. Jedinica Sa Ovim Podacima Već Postoji !", Display.TIP_OBAVESTENJA.GRESKA);
        } catch (Exception e) {
            Display.obavestenjeBaloncic("Greška.", e.getMessage(), Display.TIP_OBAVESTENJA.GRESKA);
        }
    }//GEN-LAST:event_jButton_ORGJED_NovaActionPerformed

    private void jButton_ORGJED_IzmenaPodatakaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ORGJED_IzmenaPodatakaActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            ERSQuery.updateOrgJed(
                    orgJed_bind,
                    firma_bind,
                    jTextField_ORGJED_Naziv.getText(),
                    jTextField_ORGJED_Sifra.getText(),
                    jCheckBox_ORGJED_Mehanika.isSelected());

            Display.obavestenjeBaloncic("Obaveštenje.", "Org. Jedinica Je Uspešno Ažurirana.", Display.TIP_OBAVESTENJA.INFORMATIVNO);

        } catch (RollbackException e) {
            Display.obavestenjeBaloncic("Greška.", e.getMessage(), Display.TIP_OBAVESTENJA.GRESKA);
        } catch (Exception e) {
            Display.obavestenjeBaloncic("Greška.", e.getMessage(), Display.TIP_OBAVESTENJA.GRESKA);
        }
    }//GEN-LAST:event_jButton_ORGJED_IzmenaPodatakaActionPerformed

    private void jComboBox_RADNICI_TipRadnikaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jComboBox_RADNICI_TipRadnikaPropertyChange
        // TODO add your handling code here:

    }//GEN-LAST:event_jComboBox_RADNICI_TipRadnikaPropertyChange

    private void jComboBox_RADNICI_OrgJedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_RADNICI_OrgJedActionPerformed
        // TODO add your handling code here:
        Orgjed o = (Orgjed) jComboBox_RADNICI_OrgJed.getSelectedItem();
        if (o != null) {
            setOrgJed_bind(o);
        }
    }//GEN-LAST:event_jComboBox_RADNICI_OrgJedActionPerformed

    private void jComboBox_RADNICI_TipRadnikaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_RADNICI_TipRadnikaActionPerformed
        // TODO add your handling code here:
        TipRadnika t = (TipRadnika) jComboBox_RADNICI_TipRadnika.getSelectedItem();
        if (t != null) {
            setTipRadnika_bind(t);
        }
    }//GEN-LAST:event_jComboBox_RADNICI_TipRadnikaActionPerformed

    private void jComboBox_ORGJED_FirmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_ORGJED_FirmaActionPerformed
        // TODO add your handling code here:
        Firma f = (Firma) jComboBox_ORGJED_Firma.getSelectedItem();
        if (f != null) {
            setFirma_bind(f);
        }
    }//GEN-LAST:event_jComboBox_ORGJED_FirmaActionPerformed

    private void jComboBox_ORGJED_KompanijaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_ORGJED_KompanijaActionPerformed
        // TODO add your handling code here:
        Kompanija k = (Kompanija) jComboBox_ORGJED_Kompanija.getSelectedItem();
        if (k != null) {
            setKompanija_bind(k);
        }
    }//GEN-LAST:event_jComboBox_ORGJED_KompanijaActionPerformed

    private void jButton_KOMPANIJA_IzmenaPodatakaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_KOMPANIJA_IzmenaPodatakaActionPerformed
        try {
            ERSQuery.updateKompanija(kompanija_bind,
                    jTextField_KOMPANIJA_Naziv.getText(),
                    jTextField_KOMPANIJA_Grad.getText(),
                    jTextField_KOMPANIJA_Adresa.getText(),
                    jTextField_KOMPANIJA__Vlasnik.getText());

            Display.obavestenjeBaloncic("Obaveštenje.", "Podaci o Kompaniji Su Uspešno Ažurirani.", Display.TIP_OBAVESTENJA.INFORMATIVNO);

        } catch (Exception e) {
            Display.obavestenjeBaloncic("Greška.", e.getMessage(), Display.TIP_OBAVESTENJA.GRESKA);
        }
    }//GEN-LAST:event_jButton_KOMPANIJA_IzmenaPodatakaActionPerformed

    private void jButton_BarkodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_BarkodActionPerformed
        // TODO add your handling code here:
        try {
            if (jRadioButton_Barkod_Radnici.isSelected()) {
                new ReportGenerator()
                        .generisanjeIzvestaja_BarKod_Radnici(firma_bind.getIDFirma(), true);
            } else if (jRadioButton_Barkod_Statusi.isSelected()) {
                new ReportGenerator()
                        .generisanjeIzvestaja_BarKod_Statusi("");
            }
        } catch (NullPointerException npe) {
            Display.obavestenjeBaloncic("Greška.", "Izabrati firmu.", Display.TIP_OBAVESTENJA.GRESKA);
        } catch (Exception e) {
            Display.obavestenjeBaloncic("Greška.", e.getMessage(), Display.TIP_OBAVESTENJA.GRESKA);
        }
    }//GEN-LAST:event_jButton_BarkodActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup_Barkod;
    private javax.swing.JButton jButton_Barkod;
    private javax.swing.JButton jButton_FIRMA_IzmenaPodataka;
    private javax.swing.JButton jButton_FIRMA_Nova;
    private javax.swing.JButton jButton_KOMPANIJA_IzmenaPodataka;
    private javax.swing.JButton jButton_ORGJED_IzmenaPodataka;
    private javax.swing.JButton jButton_ORGJED_Nova;
    private javax.swing.JButton jButton_RADNIK_Brisanje;
    private javax.swing.JButton jButton_RADNIK_IzmenaPodataka;
    private javax.swing.JButton jButton_RADNIK_Novi;
    private javax.swing.JCheckBox jCheckBox_FIRMA_Aktivna;
    private javax.swing.JCheckBox jCheckBox_ORGJED_Mehanika;
    private javax.swing.JCheckBox jCheckBox_Radnik_Aktivan;
    private javax.swing.JCheckBox jCheckBox_Radnik_Radnik;
    private javax.swing.JComboBox jComboBox_ORGJED_Firma;
    private javax.swing.JComboBox jComboBox_ORGJED_Kompanija;
    private javax.swing.JComboBox jComboBox_RADNICI_OrgJed;
    private javax.swing.JComboBox jComboBox_RADNICI_TipRadnika;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_Naslov;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel_Barkod;
    private javax.swing.JPanel jPanel_Firma;
    private javax.swing.JPanel jPanel_Kompanija;
    private javax.swing.JPanel jPanel_OrgJed;
    private javax.swing.JPanel jPanel_Radnik;
    private javax.swing.JRadioButton jRadioButton_Barkod_Radnici;
    private javax.swing.JRadioButton jRadioButton_Barkod_Statusi;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JTabbedPane jTP_DataManagement;
    private javax.swing.JTextField jTextField_FIRMA_Adresa;
    private javax.swing.JTextField jTextField_FIRMA_Grad;
    private javax.swing.JTextField jTextField_FIRMA_Kompanija;
    private javax.swing.JTextField jTextField_FIRMA_MATBROJ;
    private javax.swing.JTextField jTextField_FIRMA_Naziv;
    private javax.swing.JTextField jTextField_FIRMA_PIB;
    private javax.swing.JTextField jTextField_FIRMA_PostanskiBroj;
    private javax.swing.JTextField jTextField_KOMPANIJA_Adresa;
    private javax.swing.JTextField jTextField_KOMPANIJA_Grad;
    private javax.swing.JTextField jTextField_KOMPANIJA_Naziv;
    private javax.swing.JTextField jTextField_KOMPANIJA__Vlasnik;
    private javax.swing.JTextField jTextField_ORGJED_Firma;
    private javax.swing.JTextField jTextField_ORGJED_Kompanija;
    private javax.swing.JTextField jTextField_ORGJED_Naziv;
    private javax.swing.JTextField jTextField_ORGJED_Sifra;
    private javax.swing.JTextField jTextField_Radnik_Ime;
    private javax.swing.JTextField jTextField_Radnik_OrgJed;
    private javax.swing.JTextField jTextField_Radnik_Prezime;
    private javax.swing.JTextField jTextField_Radnik_Sifra_INFSISTEM;
    private javax.swing.JTextField jTextField_Radnik_TipRadnika;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //<editor-fold defaultstate="collapsed" desc="Lookup Utils">
    @Override
    public void componentOpened() {
        kompanijaLookup = Utilities.actionsGlobalContext().lookupResult(Kompanija.class);
        kompanijaLookup.addLookupListener(new LookupListener() {

            @Override
            public void resultChanged(LookupEvent le) {
                Lookup.Result k = (Lookup.Result) le.getSource();
                Collection<Kompanija> kompanije = k.allInstances();

                if (!kompanije.isEmpty()) {
                    for (Kompanija k1 : kompanije) {
                        setKompanija_bind(k1);
                        setKOMPANIJATextFields(kompanija_bind);

                        jTP_DataManagement.setSelectedIndex(0);
                    }
                }
            }
        });

        firmaLookup = Utilities.actionsGlobalContext().lookupResult(Firma.class);
        firmaLookup.addLookupListener(new LookupListener() {

            @Override
            public void resultChanged(LookupEvent le) {
                Lookup.Result k = (Lookup.Result) le.getSource();
                Collection<Firma> firme = k.allInstances();

                if (!firme.isEmpty()) {
                    for (Firma f1 : firme) {
                        setFirma_bind(f1);
                        setKompanija_bind(f1.getFkIdk());

                        setFIRMATextFields(getFirma_bind());

                        jTP_DataManagement.setSelectedIndex(1);
                    }
                }
            }
        });

        orgJedLookup = Utilities.actionsGlobalContext().lookupResult(Orgjed.class);
        orgJedLookup.addLookupListener(new LookupListener() {

            @Override
            public void resultChanged(LookupEvent le) {
                Lookup.Result k = (Lookup.Result) le.getSource();
                Collection<Orgjed> orgJedinice = k.allInstances();

                if (!orgJedinice.isEmpty()) {
                    for (Orgjed o1 : orgJedinice) {
                        setOrgJed_bind(o1);
                        setFirma_bind(o1.getFKIDFirma());
                        setKompanija_bind(firma_bind.getFkIdk());

                        setORGJEDTextFields(getOrgJed_bind());

                        jTP_DataManagement.setSelectedIndex(2);
                    }
                }
            }
        });

        radnikLookup = Utilities.actionsGlobalContext().lookupResult(Radnik.class);
        radnikLookup.addLookupListener(new LookupListener() {

            @Override
            public void resultChanged(LookupEvent le) {
                Lookup.Result s = (Lookup.Result) le.getSource();
                Collection<Radnik> radnici = s.allInstances();

                if (!radnici.isEmpty()) {
                    for (Radnik r1 : radnici) {
                        setRadnik_bind(r1);
                        setTipRadnika_bind(r1.getFKIDVrsta());
                        setOrgJed_bind(r1.getFKIDOrgjed());
                        setFirma_bind(orgJed_bind.getFKIDFirma());
                        setKompanija_bind(firma_bind.getFkIdk());

                        setRADNIKTextFields(getRadnik_bind());

                        jTP_DataManagement.setSelectedIndex(3);
                    }
                }
            }
        });
    }

    @Override
    public void componentClosed() {
        kompanijaLookup.removeLookupListener(this);
        kompanijaLookup = null;
        firmaLookup.removeLookupListener(this);
        firmaLookup = null;
        orgJedLookup.removeLookupListener(this);
        orgJedLookup = null;
        radnikLookup.removeLookupListener(this);
        radnikLookup = null;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="set text fields...">
    private void setKOMPANIJATextFields(Kompanija k) {
        jTextField_KOMPANIJA_Naziv.setText(k.getNazivKompanije());
        jTextField_KOMPANIJA_Adresa.setText(k.getAdresa());
        jTextField_KOMPANIJA_Grad.setText(k.getGrad());
        jTextField_KOMPANIJA__Vlasnik.setText(k.getVlasnik());
    }

    private void setFIRMATextFields(Firma f) {
        jTextField_FIRMA_Naziv.setText(f.getNaziv());
        jTextField_FIRMA_Grad.setText(f.getGrad());
        jTextField_FIRMA_Adresa.setText(f.getAdresa());
        jTextField_FIRMA_PIB.setText(f.getPib());
        jTextField_FIRMA_MATBROJ.setText(f.getMatbr());
        jTextField_FIRMA_Kompanija.setText(f.getFkIdk().getNazivKompanije() /*f.getFkIdk().getNazivKompanije()*/);
        jCheckBox_FIRMA_Aktivna.setSelected(f.isAktivna());
    }

    private void setORGJEDTextFields(Orgjed orgjed) {
        jTextField_ORGJED_Kompanija.setText(orgjed.getFKIDFirma().getFkIdk().getNazivKompanije());
        jTextField_ORGJED_Firma.setText(orgjed.getFKIDFirma().getNaziv());
        jTextField_ORGJED_Naziv.setText(orgjed.getNaziv());
        jTextField_ORGJED_Sifra.setText(orgjed.getSifra());
        jCheckBox_ORGJED_Mehanika.setSelected(orgjed.isMehanika());
    }

    private void setRADNIKTextFields(Radnik r) {
        jTextField_Radnik_Ime.setText(r.getIme());
        jTextField_Radnik_Prezime.setText(r.getPrezime());
        jTextField_Radnik_OrgJed.setText(r.getFKIDOrgjed().getSifra());
        jTextField_Radnik_TipRadnika.setText(r.getFKIDVrsta().getNaziv() /*r.getFKIDVrsta().getNaziv()*/);
        jTextField_Radnik_Sifra_INFSISTEM.setText(r.getSifraINFSISTEM());
        jCheckBox_Radnik_Aktivan.setSelected(r.getAktivan());
        jCheckBox_Radnik_Radnik.setSelected(r.isRadnik());
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="ne koristi se...">
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
//</editor-fold>
}
