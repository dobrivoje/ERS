/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package node_klase.radninalog;

import static INFSYS.queries.INFSistemQuery.pretragaPoFakturi;
import com.dobrivoje.utilities.icons.icon_renderer.INodeIconRenderer;
import com.dobrivoje.utilities.icons.icon_renderer.IconRenderer;
import static com.dobrivoje.utilities.icons.icon_renderer.IconType.RadniNalog.FAKTURA;
import static com.dobrivoje.utilities.icons.icon_renderer.IconType.RadniNalog.RADNINALOG;
import ent.infsistem.Nald001;
import java.awt.event.ActionEvent;
import java.beans.IntrospectionException;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import node_klase.interfejsi.actionable.IActionableRN;
import org.openide.awt.StatusDisplayer;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.Exceptions;
import org.openide.util.Utilities;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author dobri
 */
public class RadniNalogBean extends BeanNode<Nald001>
        implements IActionableRN {

    private final Nald001 tekuciNalog;
    private final List<Nald001> nalozi;

    private String opis;
    private String nc;

    public RadniNalogBean(Nald001 nalog) throws IntrospectionException {
        super(nalog, Children.LEAF, Lookups.singleton(nalog));

        IconRenderer ir = IconRenderer.getDefault();

        tekuciNalog = getLookup().lookup(Nald001.class);
        nalozi = pretragaPoFakturi(tekuciNalog.getBrFakt());
        //
        ir.generateIcons(nalog.getUkupno() != null && nalog.getUkupno() != 0 ? FAKTURA : RADNINALOG,
                new INodeIconRenderer() {
                    @Override
                    public void node_setIconBaseWithExtension(String URL) {
                        setIconBaseWithExtension(URL);
                    }
                }
        );
        nc = "[" + nalog.getRadnal() + "], [" + nalog.getTip() + "], [" + nalog.getDatum() + "], [Km. " + nalog.getKm() + "]";

        setDisplayName(nc);
        setShortDescription(opis);
    }

    //<editor-fold defaultstate="collapsed" desc="getHtmlDisplayName">
    @Override

    public String getHtmlDisplayName() {
        String s = null;
        boolean daLiJeStorno = tekuciNalog.getBrFakt() != null && tekuciNalog.getUkupno() < 0;

        nc = "[" + tekuciNalog.getRadnal()
                + (daLiJeStorno ? "/" + tekuciNalog.getBrFakt() : "") + "]"
                + (daLiJeStorno ? "" : ", [" + tekuciNalog.getTip() + "]")
                + ", [" + tekuciNalog.getDatum() + "]"
                + (daLiJeStorno ? "" : ", [Km. " + tekuciNalog.getKm() + "]");

        switch (nalozi.size()) {
            case 0:
            case 1:
                try {
                    if (tekuciNalog.getKupac().equals("000052")) {
                        s = "<font color='#054ABC'>"
                                + nc
                                + " - [G]</font>";
                    } else {
                        s = null;
                    }
                } catch (Exception e) {
                }

                break;
            default:
                // ako je postoji storno, formatiraj ih !
                try {
                    s = (tekuciNalog.getUkupno() < 0
                            ? "<font color='#FF0606'>" + nc + " - [Storno]</font>"
                            : "<font color='#DF00DF'>" + nc + " - [Storniran !]</font>");
                } catch (NullPointerException npe) {
                    return null;
                }
        }
        return s;
    }

    //</editor-fold>
    //
    //<editor-fold defaultstate="collapsed" desc="createSheet">
    @Override
    protected Sheet createSheet() {
        Sheet glavniSheet = super.createSheet();

        Sheet.Set klijentSheet = Sheet.createPropertiesSet();
        Sheet.Set nalogSheet = Sheet.createPropertiesSet();
        Sheet.Set finSheet = Sheet.createPropertiesSet();

        nalogSheet.setName("nalogSheet");
        nalogSheet.setDisplayName("Nalog");

        klijentSheet.setName("klijentSheet");
        klijentSheet.setDisplayName("Klijent");

        finSheet.setName("finSheet");
        finSheet.setDisplayName("Finansijski Deo");

        Nald001 nalog = getLookup().lookup(Nald001.class);

        try {
            Node.Property<String> propIme = new PropertySupport.Reflection<>(nalog, String.class, "ime");
            Node.Property<String> propPrezime = new PropertySupport.Reflection<>(nalog, String.class, "prezime");
            Node.Property<String> propSifraKupca = new PropertySupport.Reflection<>(nalog, String.class, "kupac");
            Node.Property<String> propTelKupca = new PropertySupport.Reflection<>(nalog, String.class, "telefon");
            Node.Property<String> propAdresa = new PropertySupport.Reflection<>(nalog, String.class, "adresa");
            Node.Property<String> propMesto = new PropertySupport.Reflection<>(nalog, String.class, "mesto");

            propIme.setName("Ime");
            propPrezime.setName("Prezime");
            propSifraKupca.setName("Šifra Kupca");
            propTelKupca.setName("Telefon");
            propAdresa.setName("Adresa");
            propMesto.setName("Mesto");

            klijentSheet.put(propIme);
            klijentSheet.put(propPrezime);
            klijentSheet.put(propSifraKupca);
            klijentSheet.put(propTelKupca);
            klijentSheet.put(propAdresa);
            klijentSheet.put(propMesto);

            //////////////////////////////////////
            Node.Property<String> propProfCentar = new PropertySupport.Reflection<>(nalog, String.class, "pcentar");
            Node.Property<String> propNalog = new PropertySupport.Reflection<>(nalog, String.class, "radnal");
            Node.Property<String> propDatumNaloga = new PropertySupport.Reflection<>(nalog, String.class, "datum");
            Node.Property<String> propServisniSavetnik = new PropertySupport.Reflection<>(nalog, String.class, "primio");
            // Property propImeServisnogSavetnika = new PropertySupport.Reflection<>(nalog, String.class, "primio");
            // Property propPrezimeServisnogSavetnika = new PropertySupport.Reflection<>(nalog, String.class, "primio");
            Node.Property<String> propFaktura = new PropertySupport.Reflection<>(nalog, String.class, "brFakt");
            Node.Property<String> propDatumFakture = new PropertySupport.Reflection<>(nalog, String.class, "fakturisan");
            Node.Property<String> propSasija = new PropertySupport.Reflection<>(nalog, String.class, "sasija");
            Node.Property<String> propBrojMotora = new PropertySupport.Reflection<>(nalog, String.class, "motor");
            Node.Property<String> propRegBroj = new PropertySupport.Reflection<>(nalog, String.class, "registrac");
            Node.Property<String> propMarka = new PropertySupport.Reflection<>(nalog, String.class, "marka");
            Node.Property<String> propTip = new PropertySupport.Reflection<>(nalog, String.class, "tip");

            propProfCentar.setName("Prof. Centar");
            propNalog.setName("Nalog");
            propDatumNaloga.setName("Datum Naloga");
            propServisniSavetnik.setName("Šifra Savetnika");
            propFaktura.setName("Faktura");
            propDatumFakture.setName("Datum Fakture");
            propSasija.setName("Šasija");
            propBrojMotora.setName("Broj Motora");
            propRegBroj.setName("Registarski Broj");
            propMarka.setName("Marka");
            propTip.setName("Tip");

            nalogSheet.put(propProfCentar);
            nalogSheet.put(propNalog);
            nalogSheet.put(propDatumNaloga);
            nalogSheet.put(propServisniSavetnik);
            nalogSheet.put(propFaktura);
            nalogSheet.put(propDatumFakture);
            nalogSheet.put(propSasija);
            nalogSheet.put(propBrojMotora);
            nalogSheet.put(propRegBroj);
            nalogSheet.put(propMarka);
            nalogSheet.put(propTip);

            //////////////////////////////////////
            Node.Property<Double> propMaterijal = new PropertySupport.Reflection<>(nalog, Double.class, "materijal");
            Node.Property<Double> propPopustMaterijal = new PropertySupport.Reflection<>(nalog, Double.class, "rabMat");
            Node.Property<Double> propServis = new PropertySupport.Reflection<>(nalog, Double.class, "rad");
            Node.Property<Double> propPopustSat = new PropertySupport.Reflection<>(nalog, Double.class, "rabSat");
            Node.Property<Double> propUkupno = new PropertySupport.Reflection<>(nalog, Double.class, "ukupno");

            propMaterijal.setName("Materijal");
            propPopustMaterijal.setName("Popust Materijal");
            propServis.setName("Servis");
            propPopustSat.setName("Popust Servis");
            propUkupno.setName("Ukupno");

            finSheet.put(propMaterijal);
            finSheet.put(propPopustMaterijal);
            finSheet.put(propServis);
            finSheet.put(propPopustSat);
            finSheet.put(propUkupno);

        } catch (NoSuchMethodException ex) {
            Exceptions.printStackTrace(ex);
        }
        glavniSheet.put(klijentSheet);
        glavniSheet.put(nalogSheet);
        glavniSheet.put(finSheet);

        return glavniSheet;
    }

    //</editor-fold>
    //
    @Override
    public Action getPreferredAction() {
        return new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                StatusDisplayer.getDefault().setStatusText(
                        "Akcija! Datum Naloga - "
                        + tekuciNalog.getRadnal() + "/" + tekuciNalog.getBrFakt()
                        + " [" + tekuciNalog.getDatnal() + "]");
            }
        };
    }

    @Override
    public Action[] getActions(boolean context) {
        List<? extends Action> akcijeZaRN
                = Utilities.actionsForPath("Actions/Menu/Akcije/Radni Nalozi");

        return akcijeZaRN.toArray(new Action[akcijeZaRN.size()]);
    }
}
