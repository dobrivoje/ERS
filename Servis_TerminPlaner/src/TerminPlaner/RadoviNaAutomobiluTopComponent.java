/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TerminPlaner;

import INFSYS.queries.INFSistemQuery;
import TerminPlaner.QuickSearch.NalogDelimicanOpisQS;
import TerminPlaner.QuickSearch.ServisiKlijenataQS;
import com.dobrivoje.utilities.datumi.DatumSelektor;
import com.dobrivoje.utilities.icons.icon_renderer.INodeIconRenderer;
import com.dobrivoje.utilities.icons.icon_renderer.IconRenderer;
import com.dobrivoje.utilities.icons.icon_renderer.IconType;
import static com.dobrivoje.utilities.icons.icon_renderer.IconType.Automobil.TABLICE;
import com.dobrivoje.utilities.infsistem.Pretraga.AUTO_PRETRAGA;
import com.dobrivoje.utilities.infsistem.Pretraga.DATUM_PRETRAGE;
import com.dobrivoje.utilities.infsistem.Pretraga.NACIN_PRETRAGE;
import com.dobrivoje.utilities.infsistem.Pretraga.RADNI_NALOG_NACIN_PRETRAGE;
import com.dobrivoje.utilities.infsistem.Pretraga.SIFARNIK_PRETRAGA;
import com.dobrivoje.utilities.warnings.Display;
import static com.dobrivoje.utilities.icons.icon_renderer.IconType.ProfCentar.RADDAN;
import static com.dobrivoje.utilities.icons.icon_renderer.IconType.Univerzalne.RACUNI;
import static com.dobrivoje.utilities.icons.icon_renderer.IconType.Univerzalne.RADOVI;
import static com.dobrivoje.utilities.icons.icon_renderer.IconType.Univerzalne.STRANKE;
import static com.dobrivoje.utilities.infsistem.Pretraga.AUTO_PRETRAGA.MATBR;
import static com.dobrivoje.utilities.infsistem.Pretraga.AUTO_PRETRAGA.NAZIV;
import static com.dobrivoje.utilities.infsistem.Pretraga.AUTO_PRETRAGA.PIB;
import static com.dobrivoje.utilities.infsistem.Pretraga.AUTO_PRETRAGA.REGISTRACIJA;
import static com.dobrivoje.utilities.infsistem.Pretraga.AUTO_PRETRAGA.SASIJA;
import static com.dobrivoje.utilities.infsistem.Pretraga.AUTO_PRETRAGA.SIFRA;
import static com.dobrivoje.utilities.infsistem.Pretraga.DATUM_PRETRAGE.BEZ_DATUMA;
import static com.dobrivoje.utilities.infsistem.Pretraga.NACIN_PRETRAGE.DELIMICNO;
import static com.dobrivoje.utilities.infsistem.Pretraga.NACIN_PRETRAGE.KOMPLETNO;
import static com.dobrivoje.utilities.infsistem.Pretraga.RADNI_NALOG_NACIN_PRETRAGE.FAKTURISAN;
import static com.dobrivoje.utilities.infsistem.Pretraga.RADNI_NALOG_NACIN_PRETRAGE.RADNI_NALOG;
import static com.dobrivoje.utilities.infsistem.Pretraga.RADNI_NALOG_NACIN_PRETRAGE.STORNO;
import static com.dobrivoje.utilities.warnings.Display.TIP_OBAVESTENJA.GRESKA;
import ent.infsistem.Nald001;
import ent.infsistem.Sifarnik;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import javax.persistence.NoResultException;
import node_klase.infsistem.klijent.servisi.VozilaKlijentaChildFactory;
import node_klase.infsistem.radninalozi.RN_Faktura_ChildFactory;
import node_klase.infsistem.radninalozi.RadniNaloziRegBrojChildFactory;
import node_klase.infsistem.radninalozi.RadniNaloziZaDatumChildFactory;
import node_klase.infsistem.radninalozi.RadniNaloziZaSasijuChildFactory;
import node_klase.infsistem.sifarnik.SifarnikChildFactory;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;
import org.openide.windows.WindowManager;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//TerminPlaner//RadoviNaAutomobilu//EN",
        autostore = false)
@TopComponent.Description(
        preferredID = "RadoviNaAutomobiluTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "editor", openAtStartup = true)
@ActionID(category = "Window/Servis/Termin Planer", id = "TerminPlaner.RadoviNaAutomobiluTopComponent")
@ActionReference(path = "Menu/Window/Servis/Termin Planer", position = 1100)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_RadoviNaAutomobiluAction",
        preferredID = "RadoviNaAutomobiluTopComponent")
@Messages({
    "CTL_RadoviNaAutomobiluAction=Radni Nalozi i Servisi",
    "CTL_RadoviNaAutomobiluTopComponent=Radni Nalozi i Servisi",
    "HINT_RadoviNaAutomobiluTopComponent=Radni Nalozi i Servisi"
})
public final class RadoviNaAutomobiluTopComponent extends TopComponent
        implements ExplorerManager.Provider, LookupListener, INodeIconRenderer {

    // definicije pretrage
    //<editor-fold defaultstate="collapsed" desc="Pretraga,...">
    private AUTO_PRETRAGA AutoPretraga;
    private RADNI_NALOG_NACIN_PRETRAGE RN_Nacin_Pretrage;
    private NACIN_PRETRAGE NacinPretrage;
    private DATUM_PRETRAGE DatumPretrage;
    //</editor-fold>
    private String datumLookup;
    private Nald001 nalog;
    private static Sifarnik sifra;
    private DatumSelektor ds; // = DatumSelektor.getDafault();
    //
    private Lookup.Result<String> odabraniDatum;
    private Lookup.Result<Nald001> odabraniNalog;
    private Lookup.Result<Sifarnik> odabranaSifra;
    private Lookup.Result<DatumSelektor> odabraniDatumi;
    //
    private AbstractNode RNRoot;
    private RadniNaloziZaSasijuChildFactory RNSChildFactory;
    private RadniNaloziZaDatumChildFactory RNDChildFactory;
    private RadniNaloziRegBrojChildFactory RNRBChildFactory;
    private SifarnikChildFactory SChildFactory;
    //
    private VozilaKlijentaChildFactory VKChildFactory;
    //
    private RN_Faktura_ChildFactory rnfChildFactory;
    //
    private static final ExplorerManager em = new ExplorerManager();
    //
    private final IconRenderer iconRenderer = IconRenderer.getDefault();

    public RadoviNaAutomobiluTopComponent() {
        initComponents();
        setName(Bundle.CTL_RadoviNaAutomobiluTopComponent());
        setToolTipText(Bundle.HINT_RadoviNaAutomobiluTopComponent());

        associateLookup(ExplorerUtils.createLookup(em, getActionMap()));

        AutoPretraga = SASIJA;
        NacinPretrage = KOMPLETNO;
        DatumPretrage = BEZ_DATUMA;
        RN_Nacin_Pretrage = RADNI_NALOG;

        datumLookup = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        RN_ZA_DATUM_Refresh(datumLookup);
    }

    //<editor-fold defaultstate="collapsed" desc="Node Factories">
    private void RN_FAKT_Refresh(String rn_faktura, boolean radniNalog, boolean saDatumom) {
        rnfChildFactory = new RN_Faktura_ChildFactory(rn_faktura, radniNalog, saDatumom);
        RNRoot = new AbstractNode(Children.create(rnfChildFactory, true));

        em.setRootContext(RNRoot);

        iconRenderer.generateIcons(RADOVI, this);
        RNRoot.setName(radniNalog ? " RN - " + rn_faktura : " FA - " + rn_faktura);

        jLabeles_Visible(false);
        jLabel_UkRezultata.setText((radniNalog ? " Radnih Naloga - " : " Faktura - ") + rnfChildFactory.getUkupnoString());
    }

    private void RN_FAKT_ZA_PERIOD_Refresh(String DatumOD, String DatumDO, boolean radniNalog) {
        rnfChildFactory = new RN_Faktura_ChildFactory(DatumOD, DatumDO, radniNalog);
        RNRoot = new AbstractNode(Children.create(rnfChildFactory, true));

        em.setRootContext(RNRoot);

        iconRenderer.generateIcons(RADOVI, this);
        RNRoot.setName("Nalozi u periodu: " + DatumOD + " - " + DatumDO);

        jLabeles_Visible(false);
        jLabel_UkRezultata.setText(" RN/FA - " + rnfChildFactory.getUkupnoString());
    }

    private void STORNO_FAKT_ZA_PERIOD_Refresh(String DatumOD, String DatumDO) {
        rnfChildFactory = new RN_Faktura_ChildFactory(DatumOD, DatumDO, true, true);
        RNRoot = new AbstractNode(Children.create(rnfChildFactory, true));

        em.setRootContext(RNRoot);

        iconRenderer.generateIcons(RACUNI, this);
        RNRoot.setName(" Nalozi u periodu: " + DatumOD + " - " + DatumDO);

        jLabeles_Visible(false);
        jLabel_UkRezultata.setText("ST/FA - " + rnfChildFactory.getUkupnoString());
    }

    private void VOZILA_KLIJENATA_Refresh(String sifraKlijenta) {

        VKChildFactory = new VozilaKlijentaChildFactory(sifraKlijenta);
        RNRoot = new AbstractNode(Children.create(VKChildFactory, true));

        em.setRootContext(RNRoot);

        iconRenderer.generateIcons(STRANKE, this);
        RNRoot.setName("  " + sifraKlijenta);

        jLabeles_Visible(false);
        jLabel_UkRezultata.setText(" Održavanih Vozila - " + VKChildFactory.getUkupnoString());
    }

    private void RN_PO_SASIJI_Refresh(String Sasija) {
        RNSChildFactory = new RadniNaloziZaSasijuChildFactory(Sasija);
        RNRoot = new AbstractNode(Children.create(RNSChildFactory, true));

        em.setRootContext(RNRoot);

        iconRenderer.generateIcons(IconType.Automobil.SASIJA, this);
        RNRoot.setName(Sasija);

        jLabeles_Visible(false);
        jLabel_UkRezultata.setText(" RN/FA za ovu šasiju - " + RNSChildFactory.getUkupnoString());
    }

    private void RN_PO_REGBR_Refresh(String regBroj, boolean delimicno) {
        RNRBChildFactory = new RadniNaloziRegBrojChildFactory(
                delimicno ? "%" + regBroj + "%" : regBroj, delimicno);
        RNRoot = new AbstractNode(Children.create(RNRBChildFactory, true));

        em.setRootContext(RNRoot);

        iconRenderer.generateIcons(TABLICE, this);
        RNRoot.setName(regBroj);

        jLabeles_Visible(false);
        jLabel_UkRezultata.setText(" RN/FA Za Reg.Broj - " + RNRBChildFactory.getUkupno());
    }

    private void RN_ZA_DATUM_Refresh(String datum) {
        RNDChildFactory = new RadniNaloziZaDatumChildFactory(datum);
        RNRoot = new AbstractNode(Children.create(RNDChildFactory, true));

        em.setRootContext(RNRoot);

        iconRenderer.generateIcons(RADOVI, this);
        RNRoot.setName(" Nalozi za datum : " + datum);

        jLabeles_Visible(true);

        jLabel_UkRezultata.setText(" Fakturisano");
        jLabel_Fakturisano.setText(RNDChildFactory.getUkFakturisanoString());
        jLabel_UkRadnihNaloga.setText(RNDChildFactory.getUkupnoString());
        jLabel_Procenat_Fakturisanosti.setText(RNDChildFactory.getFakturisanostString());
    }

    private void SIFARNIK_Refresh(String kupac, SIFARNIK_PRETRAGA nacinPretrage) {
        SChildFactory = new SifarnikChildFactory(kupac, nacinPretrage);
        RNRoot = new AbstractNode(Children.create(SChildFactory, true));

        em.setRootContext(RNRoot);

        iconRenderer.generateIcons(RADDAN, this);
        RNRoot.setName(" Šifra - " + kupac);

        jLabeles_Visible(false);
        jLabel_UkRezultata.setText(" Šifara - " + SChildFactory.getUkupnoString());
    }
    //</editor-fold>

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup_StaTrebaTraziti = new javax.swing.ButtonGroup();
        buttonGroup_NacinPretrage = new javax.swing.ButtonGroup();
        buttonGroup_IntervalDatuma = new javax.swing.ButtonGroup();
        buttonGroup_Vrsta_Fakture = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel_Naslov = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        beanTreeView = new org.openide.explorer.view.BeanTreeView();
        jPanel_NacinPretrage = new javax.swing.JPanel();
        jRadioButton_Pretraga_Delimicna = new javax.swing.JRadioButton();
        jRadioButton_Pretraga_Kompletna = new javax.swing.JRadioButton();
        jPanel_Interval_NacinPretrage = new javax.swing.JPanel();
        jRadioButton_Pretraga_SaDatumom = new javax.swing.JRadioButton();
        jRadioButton_Pretraga_BezDatuma = new javax.swing.JRadioButton();
        jPanel_NacinPretrage1 = new javax.swing.JPanel();
        jTextField_Naziv = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jCheckBox_FizickoLice = new javax.swing.JCheckBox();
        jLabel10 = new javax.swing.JLabel();
        jTextField_Adresa = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextField_UKBrojServisa = new javax.swing.JTextField();
        jTextField_UKPromet = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel13 = new javax.swing.JLabel();
        jTextField_Sifra_Fakturisanja = new javax.swing.JTextField();
        jTextField_Naziv_Faktura = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jPanel_RadovinaAutu = new javax.swing.JPanel();
        jTextField_RegBroj = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField_Tip = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField_Model = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField_Sasija = new javax.swing.JTextField();
        jTextField_KM = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea_RADOVI = new javax.swing.JTextArea();
        jPanel_Automobil1 = new javax.swing.JPanel();
        jLabel_UkRezultata = new javax.swing.JLabel();
        jLabel_Fakturisano = new javax.swing.JLabel();
        jLabel_Fakturisano_od = new javax.swing.JLabel();
        jLabel_UkRadnihNaloga = new javax.swing.JLabel();
        jLabel_fakt1 = new javax.swing.JLabel();
        jLabel_Procenat_Fakturisanosti = new javax.swing.JLabel();
        jPanel_NacinPretrage4 = new javax.swing.JPanel();
        jPanel_Automobil = new javax.swing.JPanel();
        jRadioButton_PoBrojuSasije = new javax.swing.JRadioButton();
        jRadioButton_PoRegBroju = new javax.swing.JRadioButton();
        jPanel_Klijent = new javax.swing.JPanel();
        jRadioButton_PoNazivu = new javax.swing.JRadioButton();
        jRadioButton_PoPIBu = new javax.swing.JRadioButton();
        jRadioButton_PoMatBroju = new javax.swing.JRadioButton();
        jRadioButton_PoSifriKlijenta = new javax.swing.JRadioButton();
        jPanel_NaloziFakture = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jRadioButton_Nalog = new javax.swing.JRadioButton();
        jRadioButton_Faktura = new javax.swing.JRadioButton();
        jCheckBox_Storno = new javax.swing.JCheckBox();
        jCheckBox_RN_Zatvoren = new javax.swing.JCheckBox();
        jCheckBox_FA_Regularna = new javax.swing.JCheckBox();
        jButton_Trazi = new javax.swing.JButton();
        jTextField_PojamZaPretragu = new javax.swing.JTextField();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setPreferredSize(new java.awt.Dimension(700, 644));

        jLabel_Naslov.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel_Naslov, org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jLabel_Naslov.text")); // NOI18N

        jPanel_NacinPretrage.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jPanel_NacinPretrage.border.title"))); // NOI18N

        buttonGroup_NacinPretrage.add(jRadioButton_Pretraga_Delimicna);
        org.openide.awt.Mnemonics.setLocalizedText(jRadioButton_Pretraga_Delimicna, org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jRadioButton_Pretraga_Delimicna.text")); // NOI18N
        jRadioButton_Pretraga_Delimicna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_Pretraga_DelimicnaActionPerformed(evt);
            }
        });

        buttonGroup_NacinPretrage.add(jRadioButton_Pretraga_Kompletna);
        jRadioButton_Pretraga_Kompletna.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jRadioButton_Pretraga_Kompletna, org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jRadioButton_Pretraga_Kompletna.text")); // NOI18N
        jRadioButton_Pretraga_Kompletna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_Pretraga_KompletnaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_NacinPretrageLayout = new javax.swing.GroupLayout(jPanel_NacinPretrage);
        jPanel_NacinPretrage.setLayout(jPanel_NacinPretrageLayout);
        jPanel_NacinPretrageLayout.setHorizontalGroup(
            jPanel_NacinPretrageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_NacinPretrageLayout.createSequentialGroup()
                .addComponent(jRadioButton_Pretraga_Kompletna)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton_Pretraga_Delimicna))
        );
        jPanel_NacinPretrageLayout.setVerticalGroup(
            jPanel_NacinPretrageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_NacinPretrageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jRadioButton_Pretraga_Kompletna, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jRadioButton_Pretraga_Delimicna, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel_Interval_NacinPretrage.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jPanel_Interval_NacinPretrage.border.title"))); // NOI18N

        buttonGroup_IntervalDatuma.add(jRadioButton_Pretraga_SaDatumom);
        org.openide.awt.Mnemonics.setLocalizedText(jRadioButton_Pretraga_SaDatumom, org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jRadioButton_Pretraga_SaDatumom.text")); // NOI18N
        jRadioButton_Pretraga_SaDatumom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_Pretraga_SaDatumomActionPerformed(evt);
            }
        });

        buttonGroup_IntervalDatuma.add(jRadioButton_Pretraga_BezDatuma);
        jRadioButton_Pretraga_BezDatuma.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jRadioButton_Pretraga_BezDatuma, org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jRadioButton_Pretraga_BezDatuma.text")); // NOI18N
        jRadioButton_Pretraga_BezDatuma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_Pretraga_BezDatumaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_Interval_NacinPretrageLayout = new javax.swing.GroupLayout(jPanel_Interval_NacinPretrage);
        jPanel_Interval_NacinPretrage.setLayout(jPanel_Interval_NacinPretrageLayout);
        jPanel_Interval_NacinPretrageLayout.setHorizontalGroup(
            jPanel_Interval_NacinPretrageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_Interval_NacinPretrageLayout.createSequentialGroup()
                .addComponent(jRadioButton_Pretraga_BezDatuma)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton_Pretraga_SaDatumom)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_Interval_NacinPretrageLayout.setVerticalGroup(
            jPanel_Interval_NacinPretrageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_Interval_NacinPretrageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jRadioButton_Pretraga_BezDatuma, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jRadioButton_Pretraga_SaDatumom, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel_NacinPretrage1.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jPanel_NacinPretrage1.border.title"))); // NOI18N

        jTextField_Naziv.setEditable(false);
        jTextField_Naziv.setText(org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jTextField_Naziv.text")); // NOI18N
        jTextField_Naziv.setToolTipText(org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jTextField_Naziv.toolTipText")); // NOI18N

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jLabel5.text")); // NOI18N
        jLabel5.setToolTipText(org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jLabel5.toolTipText")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox_FizickoLice, org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jCheckBox_FizickoLice.text")); // NOI18N
        jCheckBox_FizickoLice.setToolTipText(org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jCheckBox_FizickoLice.toolTipText")); // NOI18N
        jCheckBox_FizickoLice.setEnabled(false);
        jCheckBox_FizickoLice.setFocusable(false);
        jCheckBox_FizickoLice.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jCheckBox_FizickoLice.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel10, org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jLabel10.text")); // NOI18N
        jLabel10.setToolTipText(org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jLabel10.toolTipText")); // NOI18N

        jTextField_Adresa.setEditable(false);
        jTextField_Adresa.setText(org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jTextField_Adresa.text")); // NOI18N

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel11, org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jLabel11.text")); // NOI18N
        jLabel11.setToolTipText(org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jLabel11.toolTipText")); // NOI18N

        jTextField_UKBrojServisa.setEditable(false);
        jTextField_UKBrojServisa.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_UKBrojServisa.setText(org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jTextField_UKBrojServisa.text")); // NOI18N

        jTextField_UKPromet.setEditable(false);
        jTextField_UKPromet.setText(org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jTextField_UKPromet.text")); // NOI18N

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel12, org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jLabel12.text")); // NOI18N
        jLabel12.setToolTipText(org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jLabel12.toolTipText")); // NOI18N

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel13, org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jLabel13.text")); // NOI18N
        jLabel13.setToolTipText(org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jLabel13.toolTipText")); // NOI18N

        jTextField_Sifra_Fakturisanja.setEditable(false);
        jTextField_Sifra_Fakturisanja.setText(org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jTextField_Sifra_Fakturisanja.text")); // NOI18N
        jTextField_Sifra_Fakturisanja.setToolTipText(org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jTextField_Sifra_Fakturisanja.toolTipText")); // NOI18N
        jTextField_Sifra_Fakturisanja.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 255, 153), 1, true));
        jTextField_Sifra_Fakturisanja.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField_Sifra_FakturisanjaMouseClicked(evt);
            }
        });

        jTextField_Naziv_Faktura.setEditable(false);
        jTextField_Naziv_Faktura.setText(org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jTextField_Naziv_Faktura.text")); // NOI18N
        jTextField_Naziv_Faktura.setToolTipText(org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jTextField_Naziv_Faktura.toolTipText")); // NOI18N
        jTextField_Naziv_Faktura.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 255, 153), 1, true));
        jTextField_Naziv_Faktura.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField_Naziv_FakturaMouseClicked(evt);
            }
        });

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel14, org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jLabel14.text")); // NOI18N
        jLabel14.setToolTipText(org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jLabel14.toolTipText")); // NOI18N

        javax.swing.GroupLayout jPanel_NacinPretrage1Layout = new javax.swing.GroupLayout(jPanel_NacinPretrage1);
        jPanel_NacinPretrage1.setLayout(jPanel_NacinPretrage1Layout);
        jPanel_NacinPretrage1Layout.setHorizontalGroup(
            jPanel_NacinPretrage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_NacinPretrage1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel_NacinPretrage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_NacinPretrage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_Naziv, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_Adresa, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel_NacinPretrage1Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel_NacinPretrage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel_NacinPretrage1Layout.createSequentialGroup()
                        .addComponent(jCheckBox_FizickoLice)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField_Sifra_Fakturisanja, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_NacinPretrage1Layout.createSequentialGroup()
                        .addGroup(jPanel_NacinPretrage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_NacinPretrage1Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField_Naziv_Faktura, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel_NacinPretrage1Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField_UKBrojServisa, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField_UKPromet, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel_NacinPretrage1Layout.setVerticalGroup(
            jPanel_NacinPretrage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_NacinPretrage1Layout.createSequentialGroup()
                .addGroup(jPanel_NacinPretrage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_Naziv, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_NacinPretrage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_Adresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_NacinPretrage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_Sifra_Fakturisanja, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel_NacinPretrage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jCheckBox_FizickoLice)
                        .addComponent(jLabel13)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_NacinPretrage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_Naziv_Faktura, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_NacinPretrage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTextField_UKBrojServisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jTextField_UKPromet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel_RadovinaAutu.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jPanel_RadovinaAutu.border.title"))); // NOI18N

        jTextField_RegBroj.setEditable(false);
        jTextField_RegBroj.setText(org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jTextField_RegBroj.text")); // NOI18N
        jTextField_RegBroj.setToolTipText(org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jTextField_RegBroj.toolTipText")); // NOI18N
        jTextField_RegBroj.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 255, 153), 1, true));
        jTextField_RegBroj.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField_RegBrojMouseClicked(evt);
            }
        });

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel9, org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jLabel9.text")); // NOI18N

        jTextField_Tip.setEditable(false);
        jTextField_Tip.setText(org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jTextField_Tip.text")); // NOI18N

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel7, org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jLabel7.text")); // NOI18N

        jTextField_Model.setEditable(false);
        jTextField_Model.setText(org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jTextField_Model.text")); // NOI18N

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel8, org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jLabel8.text")); // NOI18N

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel6, org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jLabel6.text")); // NOI18N

        jTextField_Sasija.setEditable(false);
        jTextField_Sasija.setText(org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jTextField_Sasija.text")); // NOI18N
        jTextField_Sasija.setToolTipText(org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jTextField_Sasija.toolTipText")); // NOI18N
        jTextField_Sasija.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 255, 153), 1, true));
        jTextField_Sasija.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField_SasijaMouseClicked(evt);
            }
        });

        jTextField_KM.setEditable(false);
        jTextField_KM.setText(org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jTextField_KM.text")); // NOI18N

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jLabel3.text")); // NOI18N

        jScrollPane2.setToolTipText(org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jScrollPane2.toolTipText")); // NOI18N

        jTextArea_RADOVI.setEditable(false);
        jTextArea_RADOVI.setBackground(new java.awt.Color(230, 230, 230));
        jTextArea_RADOVI.setColumns(15);
        jTextArea_RADOVI.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextArea_RADOVI.setLineWrap(true);
        jTextArea_RADOVI.setRows(8);
        jTextArea_RADOVI.setText(org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jTextArea_RADOVI.text")); // NOI18N
        jTextArea_RADOVI.setToolTipText(org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jTextArea_RADOVI.toolTipText")); // NOI18N
        jTextArea_RADOVI.setWrapStyleWord(true);
        jTextArea_RADOVI.setMinimumSize(new java.awt.Dimension(325, 112));
        jScrollPane2.setViewportView(jTextArea_RADOVI);

        javax.swing.GroupLayout jPanel_RadovinaAutuLayout = new javax.swing.GroupLayout(jPanel_RadovinaAutu);
        jPanel_RadovinaAutu.setLayout(jPanel_RadovinaAutuLayout);
        jPanel_RadovinaAutuLayout.setHorizontalGroup(
            jPanel_RadovinaAutuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_RadovinaAutuLayout.createSequentialGroup()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextField_Model, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel_RadovinaAutuLayout.createSequentialGroup()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_Tip, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel_RadovinaAutuLayout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_Sasija, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel_RadovinaAutuLayout.createSequentialGroup()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_RegBroj, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_KM, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel_RadovinaAutuLayout.setVerticalGroup(
            jPanel_RadovinaAutuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_RadovinaAutuLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_RadovinaAutuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_Sasija, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_RadovinaAutuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_RegBroj, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel3)
                    .addComponent(jTextField_KM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_RadovinaAutuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_Tip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_RadovinaAutuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_Model, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)))
        );

        jPanel_Automobil1.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jPanel_Automobil1.border.title"))); // NOI18N

        jLabel_UkRezultata.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel_UkRezultata.setForeground(new java.awt.Color(102, 102, 102));
        org.openide.awt.Mnemonics.setLocalizedText(jLabel_UkRezultata, org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jLabel_UkRezultata.text")); // NOI18N

        jLabel_Fakturisano.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel_Fakturisano.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel_Fakturisano, org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jLabel_Fakturisano.text")); // NOI18N

        jLabel_Fakturisano_od.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel_Fakturisano_od.setForeground(new java.awt.Color(102, 102, 102));
        jLabel_Fakturisano_od.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel_Fakturisano_od, org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jLabel_Fakturisano_od.text")); // NOI18N

        jLabel_UkRadnihNaloga.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel_UkRadnihNaloga.setForeground(new java.awt.Color(212, 170, 46));
        jLabel_UkRadnihNaloga.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel_UkRadnihNaloga, org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jLabel_UkRadnihNaloga.text")); // NOI18N

        jLabel_fakt1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel_fakt1.setForeground(new java.awt.Color(102, 102, 102));
        org.openide.awt.Mnemonics.setLocalizedText(jLabel_fakt1, org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jLabel_fakt1.text")); // NOI18N

        jLabel_Procenat_Fakturisanosti.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel_Procenat_Fakturisanosti.setForeground(new java.awt.Color(0, 204, 0));
        org.openide.awt.Mnemonics.setLocalizedText(jLabel_Procenat_Fakturisanosti, org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jLabel_Procenat_Fakturisanosti.text")); // NOI18N

        javax.swing.GroupLayout jPanel_Automobil1Layout = new javax.swing.GroupLayout(jPanel_Automobil1);
        jPanel_Automobil1.setLayout(jPanel_Automobil1Layout);
        jPanel_Automobil1Layout.setHorizontalGroup(
            jPanel_Automobil1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_Automobil1Layout.createSequentialGroup()
                .addComponent(jLabel_UkRezultata, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel_Fakturisano, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_Fakturisano_od, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_UkRadnihNaloga, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel_fakt1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_Procenat_Fakturisanosti, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2))
        );
        jPanel_Automobil1Layout.setVerticalGroup(
            jPanel_Automobil1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_Automobil1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel_UkRezultata)
                .addComponent(jLabel_Fakturisano)
                .addComponent(jLabel_Fakturisano_od)
                .addComponent(jLabel_UkRadnihNaloga)
                .addComponent(jLabel_fakt1)
                .addComponent(jLabel_Procenat_Fakturisanosti))
        );

        jPanel_NacinPretrage4.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jPanel_NacinPretrage4.border.title"))); // NOI18N

        jPanel_Automobil.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jPanel_Automobil.border.title"))); // NOI18N

        buttonGroup_StaTrebaTraziti.add(jRadioButton_PoBrojuSasije);
        jRadioButton_PoBrojuSasije.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jRadioButton_PoBrojuSasije, org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jRadioButton_PoBrojuSasije.text")); // NOI18N
        jRadioButton_PoBrojuSasije.setToolTipText(org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jRadioButton_PoBrojuSasije.toolTipText")); // NOI18N
        jRadioButton_PoBrojuSasije.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_PoBrojuSasijeActionPerformed(evt);
            }
        });

        buttonGroup_StaTrebaTraziti.add(jRadioButton_PoRegBroju);
        org.openide.awt.Mnemonics.setLocalizedText(jRadioButton_PoRegBroju, org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jRadioButton_PoRegBroju.text")); // NOI18N
        jRadioButton_PoRegBroju.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_PoRegBrojuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_AutomobilLayout = new javax.swing.GroupLayout(jPanel_Automobil);
        jPanel_Automobil.setLayout(jPanel_AutomobilLayout);
        jPanel_AutomobilLayout.setHorizontalGroup(
            jPanel_AutomobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jRadioButton_PoBrojuSasije)
            .addComponent(jRadioButton_PoRegBroju)
        );
        jPanel_AutomobilLayout.setVerticalGroup(
            jPanel_AutomobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_AutomobilLayout.createSequentialGroup()
                .addComponent(jRadioButton_PoBrojuSasije, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton_PoRegBroju, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel_Klijent.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jPanel_Klijent.border.title"))); // NOI18N

        buttonGroup_StaTrebaTraziti.add(jRadioButton_PoNazivu);
        org.openide.awt.Mnemonics.setLocalizedText(jRadioButton_PoNazivu, org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jRadioButton_PoNazivu.text")); // NOI18N
        jRadioButton_PoNazivu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_PoNazivuActionPerformed(evt);
            }
        });

        buttonGroup_StaTrebaTraziti.add(jRadioButton_PoPIBu);
        org.openide.awt.Mnemonics.setLocalizedText(jRadioButton_PoPIBu, org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jRadioButton_PoPIBu.text")); // NOI18N
        jRadioButton_PoPIBu.setEnabled(false);
        jRadioButton_PoPIBu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_PoPIBuActionPerformed(evt);
            }
        });

        buttonGroup_StaTrebaTraziti.add(jRadioButton_PoMatBroju);
        org.openide.awt.Mnemonics.setLocalizedText(jRadioButton_PoMatBroju, org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jRadioButton_PoMatBroju.text")); // NOI18N
        jRadioButton_PoMatBroju.setEnabled(false);
        jRadioButton_PoMatBroju.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_PoMatBrojuActionPerformed(evt);
            }
        });

        buttonGroup_StaTrebaTraziti.add(jRadioButton_PoSifriKlijenta);
        org.openide.awt.Mnemonics.setLocalizedText(jRadioButton_PoSifriKlijenta, org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jRadioButton_PoSifriKlijenta.text")); // NOI18N
        jRadioButton_PoSifriKlijenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_PoSifriKlijentaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_KlijentLayout = new javax.swing.GroupLayout(jPanel_Klijent);
        jPanel_Klijent.setLayout(jPanel_KlijentLayout);
        jPanel_KlijentLayout.setHorizontalGroup(
            jPanel_KlijentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_KlijentLayout.createSequentialGroup()
                .addGroup(jPanel_KlijentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton_PoNazivu)
                    .addComponent(jRadioButton_PoSifriKlijenta))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_KlijentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton_PoMatBroju)
                    .addComponent(jRadioButton_PoPIBu)))
        );
        jPanel_KlijentLayout.setVerticalGroup(
            jPanel_KlijentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_KlijentLayout.createSequentialGroup()
                .addGroup(jPanel_KlijentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton_PoNazivu, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton_PoPIBu, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel_KlijentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton_PoSifriKlijenta, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton_PoMatBroju, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46))
        );

        jPanel_NaloziFakture.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jPanel_NaloziFakture.border.title"))); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        buttonGroup_StaTrebaTraziti.add(jRadioButton_Nalog);
        org.openide.awt.Mnemonics.setLocalizedText(jRadioButton_Nalog, org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jRadioButton_Nalog.text")); // NOI18N
        jRadioButton_Nalog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_NalogActionPerformed(evt);
            }
        });

        buttonGroup_StaTrebaTraziti.add(jRadioButton_Faktura);
        org.openide.awt.Mnemonics.setLocalizedText(jRadioButton_Faktura, org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jRadioButton_Faktura.text")); // NOI18N
        jRadioButton_Faktura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_FakturaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioButton_Nalog)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
                .addComponent(jRadioButton_Faktura)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton_Nalog, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton_Faktura, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        buttonGroup_Vrsta_Fakture.add(jCheckBox_Storno);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox_Storno, org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jCheckBox_Storno.text")); // NOI18N
        jCheckBox_Storno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox_StornoActionPerformed(evt);
            }
        });

        buttonGroup_Vrsta_Fakture.add(jCheckBox_RN_Zatvoren);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox_RN_Zatvoren, org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jCheckBox_RN_Zatvoren.text")); // NOI18N
        jCheckBox_RN_Zatvoren.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox_RN_ZatvorenActionPerformed(evt);
            }
        });

        buttonGroup_Vrsta_Fakture.add(jCheckBox_FA_Regularna);
        jCheckBox_FA_Regularna.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox_FA_Regularna, org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jCheckBox_FA_Regularna.text")); // NOI18N
        jCheckBox_FA_Regularna.setToolTipText(org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jCheckBox_FA_Regularna.toolTipText")); // NOI18N
        jCheckBox_FA_Regularna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox_FA_RegularnaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_NaloziFaktureLayout = new javax.swing.GroupLayout(jPanel_NaloziFakture);
        jPanel_NaloziFakture.setLayout(jPanel_NaloziFaktureLayout);
        jPanel_NaloziFaktureLayout.setHorizontalGroup(
            jPanel_NaloziFaktureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_NaloziFaktureLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_NaloziFaktureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox_RN_Zatvoren)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_NaloziFaktureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jCheckBox_Storno)
                        .addComponent(jCheckBox_FA_Regularna)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_NaloziFaktureLayout.setVerticalGroup(
            jPanel_NaloziFaktureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_NaloziFaktureLayout.createSequentialGroup()
                .addGroup(jPanel_NaloziFaktureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_NaloziFaktureLayout.createSequentialGroup()
                        .addComponent(jCheckBox_FA_Regularna, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox_Storno, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox_RN_Zatvoren, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_NaloziFaktureLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel_NacinPretrage4Layout = new javax.swing.GroupLayout(jPanel_NacinPretrage4);
        jPanel_NacinPretrage4.setLayout(jPanel_NacinPretrage4Layout);
        jPanel_NacinPretrage4Layout.setHorizontalGroup(
            jPanel_NacinPretrage4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_NacinPretrage4Layout.createSequentialGroup()
                .addComponent(jPanel_Automobil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel_Klijent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel_NaloziFakture, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_NacinPretrage4Layout.setVerticalGroup(
            jPanel_NacinPretrage4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_NaloziFakture, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel_NacinPretrage4Layout.createSequentialGroup()
                .addGroup(jPanel_NacinPretrage4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel_Klijent, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel_Automobil, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jButton_Trazi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ikonice/errors_warnings_info/info.gif"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jButton_Trazi, org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.text")); // NOI18N
        jButton_Trazi.setName(""); // NOI18N
        jButton_Trazi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_TraziActionPerformed(evt);
            }
        });

        jTextField_PojamZaPretragu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextField_PojamZaPretragu.setText(org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jTextField_PojamZaPretragu.text")); // NOI18N
        jTextField_PojamZaPretragu.setToolTipText(org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jTextField_PojamZaPretragu.toolTipText")); // NOI18N
        jTextField_PojamZaPretragu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_PojamZaPretraguKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextField_PojamZaPretragu, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton_Trazi, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel_Naslov, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel_NacinPretrage4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel_NacinPretrage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel_Interval_NacinPretrage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(beanTreeView, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel_Automobil1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel_RadovinaAutu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel_NacinPretrage1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel_Naslov, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel_NacinPretrage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel_Interval_NacinPretrage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel_NacinPretrage4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_PojamZaPretragu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_Trazi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel_Automobil1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(beanTreeView, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel_NacinPretrage1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel_RadovinaAutu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel_NacinPretrage.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jPanel_NacinPretrage.AccessibleContext.accessibleName")); // NOI18N
        jPanel_Interval_NacinPretrage.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(RadoviNaAutomobiluTopComponent.class, "RadoviNaAutomobiluTopComponent.jPanel_Interval_NacinPretrage.AccessibleContext.accessibleName")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents

    private boolean proveriDuzinu(String komponenta, int duzina) {
        if (komponenta.length() < duzina) {
            Display.obavestenjeBaloncic("Greška.", "Pretraga mora imati najmanje " + duzina + " karaktera.", GRESKA);
            return false;
        } else {
            return true;
        }
    }

    private void jButton_TraziActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_TraziActionPerformed
        if (jTextField_PojamZaPretragu.getText().isEmpty()) {
            Display.obavestenjeBaloncic("Greška u Unosu.", "Prazan Upit Nije Moguć.", GRESKA);
        } else if ((jRadioButton_PoBrojuSasije.isSelected()
                || jRadioButton_PoSifriKlijenta.isSelected()
                || jRadioButton_Nalog.isSelected()
                || jRadioButton_Faktura.isSelected()) && jRadioButton_Pretraga_Delimicna.isSelected()) {

            Display.obavestenjeBaloncic("Greška u Unosu.", "Delimičan Unos podataka Nije Moguć. Unos mora biti kompletan.", GRESKA);
            jRadioButton_Pretraga_Kompletna.setSelected(true);

        } else if (jRadioButton_PoBrojuSasije.isSelected()
                && jRadioButton_Pretraga_Kompletna.isSelected()
                && proveriDuzinu(jTextField_PojamZaPretragu.getText(), 10)) {
            RN_PO_SASIJI_Refresh(jTextField_PojamZaPretragu.getText());

        } else if (jRadioButton_PoRegBroju.isSelected()) {
            if (jRadioButton_Pretraga_Delimicna.isSelected()
                    && proveriDuzinu(jTextField_PojamZaPretragu.getText(), 4)) {
                RN_PO_REGBR_Refresh(jTextField_PojamZaPretragu.getText(), true);
            } else {
                RN_PO_REGBR_Refresh(jTextField_PojamZaPretragu.getText(), false);
            }
        } else if (jRadioButton_PoNazivu.isSelected()
                && proveriDuzinu(jTextField_PojamZaPretragu.getText(), 4)) {
            SIFARNIK_Refresh(jTextField_PojamZaPretragu.getText(), SIFARNIK_PRETRAGA.KUPAC_PO_NAZIVU);
        } else if (jRadioButton_PoSifriKlijenta.isSelected()
                && jRadioButton_Pretraga_Kompletna.isSelected()) {
            try {
                VOZILA_KLIJENATA_Refresh(PodesavanjeSifre(jTextField_PojamZaPretragu.getText()));
            } catch (Exception e) {
                Display.obavestenjeBaloncic("Greška u Unosu.", "Šifra Klijenta Moraju Biti Uneti Isključivo Kao Broj. Ostali znaci nisu dozvoljeni.", GRESKA);
            }
        } else if (jRadioButton_Nalog.isSelected()) {
            if (jRadioButton_Pretraga_BezDatuma.isSelected()) {
                RN_FAKT_Refresh(jTextField_PojamZaPretragu.getText(), true, false);
            } else {
                try {
                    RN_FAKT_ZA_PERIOD_Refresh(ds.getYMDDatumOD(), ds.getYMDDatumDO(), true);
                } catch (Exception e) {
                    Display.obavestenjeBaloncic("Greška u Unosu.", "Početni i Krajnji Datum Moraju Biti Uneti.", GRESKA);
                }
            }
        } else if (jRadioButton_Faktura.isSelected()) {
            if (jRadioButton_Pretraga_BezDatuma.isSelected() && !jCheckBox_Storno.isSelected()) {
                RN_FAKT_Refresh(jTextField_PojamZaPretragu.getText(), false, false);
            }
            if (jRadioButton_Pretraga_SaDatumom.isSelected() && !jCheckBox_Storno.isSelected()) {
                try {
                    RN_FAKT_ZA_PERIOD_Refresh(ds.getYMDDatumOD(), ds.getYMDDatumDO(), false);
                } catch (Exception e) {
                    Display.obavestenjeBaloncic("Greška u Unosu.", "Početni i Krajnji Datum Moraju Biti Uneti.", GRESKA);
                }
            }
            if (jRadioButton_Pretraga_SaDatumom.isSelected() && jCheckBox_Storno.isSelected()) {
                try {
                    STORNO_FAKT_ZA_PERIOD_Refresh(ds.getYMDDatumOD(), ds.getYMDDatumDO());
                } catch (Exception e) {
                    Display.obavestenjeBaloncic("Greška u Unosu.", "Početni i Krajnji Datum Moraju Biti Uneti.", GRESKA);
                }
            }
        }
    }//GEN-LAST:event_jButton_TraziActionPerformed
    private void jTextField_SasijaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField_SasijaMouseClicked
        if (evt.getClickCount() == 2) {
            jTextField_PojamZaPretragu.setText(jTextField_Sasija.getText());

            jRadioButton_PoBrojuSasije.setSelected(true);
            jRadioButton_Pretraga_Kompletna.setSelected(true);

            jButton_TraziActionPerformed(null);
        }
    }//GEN-LAST:event_jTextField_SasijaMouseClicked

    private void jTextField_RegBrojMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField_RegBrojMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            jTextField_PojamZaPretragu.setText(jTextField_RegBroj.getText());

            jRadioButton_PoRegBroju.setSelected(true);
            jRadioButton_Pretraga_Kompletna.setSelected(true);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    jButton_TraziActionPerformed(null);
                }
            }).start();
        }
    }//GEN-LAST:event_jTextField_RegBrojMouseClicked

    private void jTextField_Sifra_FakturisanjaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField_Sifra_FakturisanjaMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            jRadioButton_Pretraga_Kompletna.setSelected(true);

            if (jTextField_Sifra_Fakturisanja.getText() != null && !jTextField_Sifra_Fakturisanja.getText().isEmpty()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        VOZILA_KLIJENATA_Refresh(PodesavanjeSifre(jTextField_Sifra_Fakturisanja.getText()));
                    }
                }).start();

                // jTextField_PojamZaPretragu.setText(PodesavanjeSifre(sifraKlijenta));
            }
        }
    }//GEN-LAST:event_jTextField_Sifra_FakturisanjaMouseClicked

    private void jTextField_PojamZaPretraguKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_PojamZaPretraguKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jButton_TraziActionPerformed(null);
        }
    }//GEN-LAST:event_jTextField_PojamZaPretraguKeyReleased

    private void jRadioButton_PoRegBrojuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_PoRegBrojuActionPerformed
        // TODO add your handling code here:
        AutoPretraga = REGISTRACIJA;
    }//GEN-LAST:event_jRadioButton_PoRegBrojuActionPerformed

    private void jRadioButton_PoBrojuSasijeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_PoBrojuSasijeActionPerformed
        // TODO add your handling code here:
        AutoPretraga = SASIJA;
        jRadioButton_Pretraga_KompletnaActionPerformed(null);
    }//GEN-LAST:event_jRadioButton_PoBrojuSasijeActionPerformed

    private void jRadioButton_Pretraga_KompletnaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_Pretraga_KompletnaActionPerformed
        // TODO add your handling code here:
        jRadioButton_Pretraga_Kompletna.setSelected(true);
        NacinPretrage = KOMPLETNO;
    }//GEN-LAST:event_jRadioButton_Pretraga_KompletnaActionPerformed

    private void jRadioButton_Pretraga_DelimicnaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_Pretraga_DelimicnaActionPerformed
        // TODO add your handling code here:
        jRadioButton_Pretraga_Delimicna.setSelected(true);
        NacinPretrage = DELIMICNO;
    }//GEN-LAST:event_jRadioButton_Pretraga_DelimicnaActionPerformed

    private void jRadioButton_Pretraga_BezDatumaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_Pretraga_BezDatumaActionPerformed
        // TODO add your handling code here:
        DatumPretrage = DATUM_PRETRAGE.BEZ_DATUMA;
        jRadioButton_Pretraga_BezDatuma.setSelected(true);
    }//GEN-LAST:event_jRadioButton_Pretraga_BezDatumaActionPerformed

    private void jRadioButton_Pretraga_SaDatumomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_Pretraga_SaDatumomActionPerformed
        // TODO add your handling code here:
        DatumPretrage = DATUM_PRETRAGE.PO_DATUMU;
        jRadioButton_Pretraga_SaDatumom.setSelected(true);
    }//GEN-LAST:event_jRadioButton_Pretraga_SaDatumomActionPerformed

    private void jRadioButton_PoSifriKlijentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_PoSifriKlijentaActionPerformed
        // TODO add your handling code here:
        AutoPretraga = SIFRA;
        jRadioButton_Pretraga_KompletnaActionPerformed(null);
    }//GEN-LAST:event_jRadioButton_PoSifriKlijentaActionPerformed

    private void jRadioButton_PoMatBrojuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_PoMatBrojuActionPerformed
        // TODO add your handling code here:
        AutoPretraga = MATBR;
        jRadioButton_Pretraga_KompletnaActionPerformed(null);
    }//GEN-LAST:event_jRadioButton_PoMatBrojuActionPerformed

    private void jRadioButton_PoPIBuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_PoPIBuActionPerformed
        // TODO add your handling code here:
        AutoPretraga = PIB;
        jRadioButton_Pretraga_KompletnaActionPerformed(null);
    }//GEN-LAST:event_jRadioButton_PoPIBuActionPerformed

    private void jRadioButton_PoNazivuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_PoNazivuActionPerformed
        // TODO add your handling code here:
        AutoPretraga = NAZIV;
        jRadioButton_Pretraga_DelimicnaActionPerformed(null);
    }//GEN-LAST:event_jRadioButton_PoNazivuActionPerformed

    private void jTextField_Naziv_FakturaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField_Naziv_FakturaMouseClicked
        // TODO add your handling code here:
        jTextField_Sifra_FakturisanjaMouseClicked(evt);
    }//GEN-LAST:event_jTextField_Naziv_FakturaMouseClicked

    private void jCheckBox_RN_ZatvorenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox_RN_ZatvorenActionPerformed
        // TODO add your handling code here:
        jRadioButton_Pretraga_SaDatumom.setSelected(true);
    }//GEN-LAST:event_jCheckBox_RN_ZatvorenActionPerformed

    private void jCheckBox_StornoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox_StornoActionPerformed
        // TODO add your handling code here:
        if (jCheckBox_Storno.isSelected()) {
            RN_Nacin_Pretrage = STORNO;
            jRadioButton_Faktura.setSelected(true);
            jRadioButton_Pretraga_SaDatumom.setSelected(true);
        }
    }//GEN-LAST:event_jCheckBox_StornoActionPerformed

    private void jRadioButton_FakturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_FakturaActionPerformed
        // TODO add your handling code here:
        RN_Nacin_Pretrage = FAKTURISAN;
        jRadioButton_Pretraga_Kompletna.setSelected(true);
        // jRadioButton_Pretraga_KompletnaActionPerformed(null);
        // jRadioButton_Pretraga_BezDatumaActionPerformed(null);
    }//GEN-LAST:event_jRadioButton_FakturaActionPerformed

    private void jRadioButton_NalogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_NalogActionPerformed
        // TODO add your handling code here:
        RN_Nacin_Pretrage = RADNI_NALOG;
        jRadioButton_Pretraga_Kompletna.setSelected(true);
    }//GEN-LAST:event_jRadioButton_NalogActionPerformed

    private void jCheckBox_FA_RegularnaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox_FA_RegularnaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox_FA_RegularnaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.openide.explorer.view.BeanTreeView beanTreeView;
    private javax.swing.ButtonGroup buttonGroup_IntervalDatuma;
    private javax.swing.ButtonGroup buttonGroup_NacinPretrage;
    private javax.swing.ButtonGroup buttonGroup_StaTrebaTraziti;
    private javax.swing.ButtonGroup buttonGroup_Vrsta_Fakture;
    private javax.swing.JButton jButton_Trazi;
    private javax.swing.JCheckBox jCheckBox_FA_Regularna;
    private javax.swing.JCheckBox jCheckBox_FizickoLice;
    private javax.swing.JCheckBox jCheckBox_RN_Zatvoren;
    private javax.swing.JCheckBox jCheckBox_Storno;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_Fakturisano;
    private javax.swing.JLabel jLabel_Fakturisano_od;
    private javax.swing.JLabel jLabel_Naslov;
    private javax.swing.JLabel jLabel_Procenat_Fakturisanosti;
    private javax.swing.JLabel jLabel_UkRadnihNaloga;
    private javax.swing.JLabel jLabel_UkRezultata;
    private javax.swing.JLabel jLabel_fakt1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel_Automobil;
    private javax.swing.JPanel jPanel_Automobil1;
    private javax.swing.JPanel jPanel_Interval_NacinPretrage;
    private javax.swing.JPanel jPanel_Klijent;
    private javax.swing.JPanel jPanel_NacinPretrage;
    private javax.swing.JPanel jPanel_NacinPretrage1;
    private javax.swing.JPanel jPanel_NacinPretrage4;
    private javax.swing.JPanel jPanel_NaloziFakture;
    private javax.swing.JPanel jPanel_RadovinaAutu;
    private javax.swing.JRadioButton jRadioButton_Faktura;
    private javax.swing.JRadioButton jRadioButton_Nalog;
    private javax.swing.JRadioButton jRadioButton_PoBrojuSasije;
    private javax.swing.JRadioButton jRadioButton_PoMatBroju;
    private javax.swing.JRadioButton jRadioButton_PoNazivu;
    private javax.swing.JRadioButton jRadioButton_PoPIBu;
    private javax.swing.JRadioButton jRadioButton_PoRegBroju;
    private javax.swing.JRadioButton jRadioButton_PoSifriKlijenta;
    private javax.swing.JRadioButton jRadioButton_Pretraga_BezDatuma;
    private javax.swing.JRadioButton jRadioButton_Pretraga_Delimicna;
    private javax.swing.JRadioButton jRadioButton_Pretraga_Kompletna;
    private javax.swing.JRadioButton jRadioButton_Pretraga_SaDatumom;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextArea jTextArea_RADOVI;
    private javax.swing.JTextField jTextField_Adresa;
    private javax.swing.JTextField jTextField_KM;
    private javax.swing.JTextField jTextField_Model;
    private javax.swing.JTextField jTextField_Naziv;
    private javax.swing.JTextField jTextField_Naziv_Faktura;
    private javax.swing.JTextField jTextField_PojamZaPretragu;
    private javax.swing.JTextField jTextField_RegBroj;
    private javax.swing.JTextField jTextField_Sasija;
    private javax.swing.JTextField jTextField_Sifra_Fakturisanja;
    private javax.swing.JTextField jTextField_Tip;
    private javax.swing.JTextField jTextField_UKBrojServisa;
    private javax.swing.JTextField jTextField_UKPromet;
    // End of variables declaration//GEN-END:variables

    //<editor-fold defaultstate="collapsed" desc="Component Opened Handler">
    @Override
    public void componentOpened() {

        odabraniDatum = Utilities
                .actionsGlobalContext()
                .lookupResult(String.class);

        odabraniDatum.addLookupListener(
                new LookupListener() {
                    @Override
                    public void resultChanged(LookupEvent le) {
                        Lookup.Result lr = (Lookup.Result) le.getSource();
                        Collection<String> datumi = lr.allInstances();

                        for (String d : datumi) {
                            if (!datumLookup.equals(d)) {
                                datumLookup = d;
                                RN_ZA_DATUM_Refresh(datumLookup);
                            }
                        }
                    }
                });

        odabranaSifra = Utilities
                .actionsGlobalContext()
                .lookupResult(Sifarnik.class);

        odabranaSifra.addLookupListener(
                new LookupListener() {
                    @Override
                    public void resultChanged(LookupEvent le) {
                        Lookup.Result lr = (Lookup.Result) le.getSource();
                        Collection<? extends Sifarnik> sifre = lr.allInstances();

                        if (!sifre.isEmpty()) {
                            for (Sifarnik n : sifre) {
                                sifra = n;
                            }

                            clearUIComponents();

                            jTextField_Naziv.setText(sifra.getIme());
                            jTextField_Adresa.setText(sifra.getAdresa());
                            jCheckBox_FizickoLice.setSelected(
                                    sifra.getFizickoLice() ? sifra.getFizickoLice() : false);
                            jTextField_Sifra_Fakturisanja.setText(sifra.getKupac().toString());

                            jTextField_Naziv_Faktura.setText(sifra.getIme());
                        }
                    }
                });

        odabraniNalog = WindowManager
                .getDefault()
                .findTopComponent("RadoviNaAutomobiluTopComponent")
                .getLookup()
                .lookupResult(Nald001.class);

        odabraniNalog.addLookupListener(
                new LookupListener() {
                    @Override
                    public void resultChanged(LookupEvent le) {
                        Lookup.Result lr = (Lookup.Result) le.getSource();
                        Collection<Nald001> nalozi = lr.allInstances();

                        if (!nalozi.isEmpty()) {
                            for (Nald001 n : nalozi) {
                                nalog = n;
                            }

                            try {
                                jTextArea_RADOVI.setText(
                                        nalog.getUradi().isEmpty() || nalog.getUradi() == null
                                        ? nalog.getUradjeno() : nalog.getUradi());
                            } catch (NullPointerException npe) {
                                jTextArea_RADOVI.setText("nema podataka o radovima,...");
                            }

                            jTextField_KM.setText(nalog.getKm().toString());

                            jTextField_Naziv.setText(
                                    (nalog.getIme() != null ? nalog.getIme() + " " : "")
                                    + (nalog.getPrezime() != null ? nalog.getPrezime() : ""));

                            jTextField_Adresa.setText(
                                    (nalog.getAdresa() != null && !nalog.getAdresa().isEmpty()
                                    ? nalog.getAdresa() : ""));

                            jTextField_Model.setText(nalog.getFamilija());
                            jTextField_RegBroj.setText(nalog.getRegistrac());
                            jTextField_Tip.setText(nalog.getTip());

                            jTextField_Sasija.setText(nalog.getSasija());

                            jTextField_Sifra_Fakturisanja.setText(
                                    (nalog.getKupac() != null && !nalog.getKupac().isEmpty()
                                    ? nalog.getKupac() : ""));

                            try {
                                jTextField_Naziv_Faktura.setText(INFSistemQuery.KupacPoSifri(
                                                Integer.parseInt(nalog.getKupac())).getIme());
                            } catch (NumberFormatException | NullPointerException | NoResultException e) {
                                jTextField_Naziv_Faktura.setText("Ne postoji šifra kupca.");
                            }
                        }
                    }
                });

        odabraniDatumi = WindowManager
                .getDefault()
                .findTopComponent("PretrazivacTopComponent")
                .getLookup()
                .lookupResult(DatumSelektor.class);

        odabraniDatumi.addLookupListener(
                new LookupListener() {
                    @Override
                    public void resultChanged(LookupEvent le) {
                        Lookup.Result lr = (Lookup.Result) le.getSource();
                        Collection<DatumSelektor> datumi = lr.allInstances();

                        if (!datumi.isEmpty()) {
                            for (DatumSelektor ds1 : datumi) {
                                ds = ds1;
                            }
                        }
                    }
                });
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Auto generated">
    @Override
    public void componentClosed() {
        odabraniDatum.removeLookupListener(null);
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
    public ExplorerManager getExplorerManager() {
        return em;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="ClearUIComponents">
    private void clearUIComponents() {
        jTextField_Naziv.setText("");
        jTextField_Adresa.setText("");
        jCheckBox_FizickoLice.setSelected(false);
        jTextField_Sifra_Fakturisanja.setText("");

        jTextField_UKBrojServisa.setText("");
        jTextField_UKPromet.setText("");

        jTextArea_RADOVI.setText("");
        jTextField_Sasija.setText("");
        jTextField_RegBroj.setText("");
        jTextField_KM.setText("");
        jTextField_Tip.setText("");
        jTextField_Model.setText("");

        jCheckBox_Storno.setSelected(false);
        jCheckBox_RN_Zatvoren.setSelected(false);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="PodesavanjeSifre">
    private String PodesavanjeSifre(String sifraKlijenta) {
        // Brljotina plavih programa : Šifarnik ima šifre kao integer,
        // Nald001 kao string dužine 6,...

        int duzinaSifreINFSISTEM = 6;
        String novaSifra = "000000";
        novaSifra += sifraKlijenta;

        novaSifra = novaSifra.substring(novaSifra.length() - duzinaSifreINFSISTEM);
        return novaSifra;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="jLabeles_Visible">
    private void jLabeles_Visible(boolean visible) {
        jLabel_Fakturisano.setVisible(visible);
        jLabel_Fakturisano_od.setVisible(visible);
        jLabel_UkRadnihNaloga.setVisible(visible);
        jLabel_fakt1.setVisible(visible);
        jLabel_Procenat_Fakturisanosti.setVisible(visible);
    }
    //</editor-fold>

    @Override
    public void resultChanged(LookupEvent le) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void node_setIconBaseWithExtension(String URL) {
        RNRoot.setIconBaseWithExtension(URL);
    }

    private void QSSifarnikPretraga() {
        if ((sifra = ServisiKlijenataQS.getSifra()) != null) {

            jRadioButton_PoSifriKlijenta.setSelected(true);
            jTextField_PojamZaPretragu.setText(Integer.toString(sifra.getKupac()));

            jButton_TraziActionPerformed(null);

            clearUIComponents();

            jTextField_Naziv.setText(sifra.getIme());
            jTextField_Adresa.setText(sifra.getAdresa());
            jCheckBox_FizickoLice.setSelected(
                    sifra.getFizickoLice() ? sifra.getFizickoLice() : false);
            jTextField_Sifra_Fakturisanja.setText(sifra.getKupac().toString());

            jTextField_Naziv_Faktura.setText(sifra.getIme());
        }
    }

    private void QSNalogOpisPretraga() {
        if ((nalog = NalogDelimicanOpisQS.getNalog()) != null) {

            jRadioButton_Nalog.setSelected(true);
            jRadioButton_Pretraga_Kompletna.setSelected(true);
            jRadioButton_Pretraga_BezDatuma.setSelected(true);
            jTextField_PojamZaPretragu.setText(nalog.getRadnal());

            jButton_TraziActionPerformed(null);

            // clearUIComponents();
        }
    }

    @Override
    public void requestActive() {
        QSSifarnikPretraga();
        QSNalogOpisPretraga();
    }
}
