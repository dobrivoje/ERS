/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package node_klase.infsistem.radninalozi;

import INFSYS.queries.INFSistemQuery;
import ent.infsistem.Nald001;
import java.beans.IntrospectionException;
import java.util.List;
import node_klase.radninalog.RadniNalogBean;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;

/**
 *
 * @author dobri
 */
public class RN_Faktura_ChildFactory extends ChildFactory<Nald001> {

    private String rn_faktura;
    private final boolean radniNalog;
    private boolean samoStornoFakture;
    private final boolean saDatumom;
    //
    private String DatumOD;
    private String DatumDO;
    //
    private int ukupno;

    //<editor-fold defaultstate="collapsed" desc="Konstruktori">
    public RN_Faktura_ChildFactory(String rn_faktura) {
        this.rn_faktura = rn_faktura;
        this.radniNalog = true;
        this.saDatumom = false;
    }

    public RN_Faktura_ChildFactory(String rn_faktura, boolean radniNalog, boolean saDatumom) {
        this.rn_faktura = rn_faktura;
        this.radniNalog = radniNalog;
        this.saDatumom = saDatumom;
    }

    public RN_Faktura_ChildFactory(String DatumOD, String DatumDO, boolean radniNalog) {
        this.DatumOD = DatumOD;
        this.DatumDO = DatumDO;
        this.saDatumom = true;
        this.radniNalog = radniNalog;
    }

    public RN_Faktura_ChildFactory(String DatumOD, String DatumDO, boolean samoStornoFakture, boolean saDatumom) {
        this.DatumOD = DatumOD;
        this.DatumDO = DatumDO;
        this.saDatumom = saDatumom;
        this.samoStornoFakture = samoStornoFakture;
        this.radniNalog = false;
    }
    //</editor-fold>

    @Override
    protected boolean createKeys(List<Nald001> list) {
        if (saDatumom && !DatumOD.isEmpty() && !DatumDO.isEmpty()) {
            if (samoStornoFakture) {
                list.addAll(INFSistemQuery.storno_Fakture_ZaPeriod(DatumOD, DatumDO));
            } else if (!radniNalog) {
                list.addAll(INFSistemQuery.fakture_ZaPeriod(DatumOD, DatumDO));
            } else if (radniNalog) {
                list.addAll(INFSistemQuery.radniNalozi_U_Periodu(DatumOD, DatumDO));
            }
        } else {
            if (radniNalog) {
                list.addAll(INFSistemQuery.pretragaPoRadnomNalogu(rn_faktura));
            } else {
                list.addAll(INFSistemQuery.pretragaPoFakturi(rn_faktura));
            }
        }

        return true;
    }

    @Override
    protected Node createNodeForKey(Nald001 key) {
        try {
            return new RadniNalogBean(key);
        } catch (IntrospectionException ex) {
            return null;
        }
    }

    public int getUkupno() {

        if (saDatumom && !DatumOD.isEmpty() && !DatumDO.isEmpty()) {
            if (samoStornoFakture) {
                ukupno = INFSistemQuery.storno_Fakture_ZaPeriod(DatumOD, DatumDO).size();
            } else if (!radniNalog) {
                ukupno = INFSistemQuery.fakture_ZaPeriod(DatumOD, DatumDO).size();
            } else if (radniNalog) {
                ukupno = INFSistemQuery.radniNalozi_U_Periodu(DatumOD, DatumDO).size();
            }
        } else {
            if (radniNalog) {
                ukupno = INFSistemQuery.pretragaPoRadnomNalogu(rn_faktura).size();
            } else {
                ukupno = INFSistemQuery.pretragaPoFakturi(rn_faktura).size();
            }
        }

        return ukupno;
    }

    public String getUkupnoString() {
        return Integer.toString(getUkupno());
    }
}
