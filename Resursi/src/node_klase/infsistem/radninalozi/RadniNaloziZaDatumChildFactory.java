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
public class RadniNaloziZaDatumChildFactory extends ChildFactory<Nald001> {

    private final String datum;
    private int fakturisano = 0;
    private int ukupno = 0;

    public RadniNaloziZaDatumChildFactory(String datum) {
        this.datum = datum;
    }

    @Override
    protected boolean createKeys(List<Nald001> list) {
        list.addAll(INFSistemQuery.radniNaloziZaDatum(datum));

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

    //<editor-fold defaultstate="collapsed" desc="ukupno,...">
    public int getUkupno() {
        ukupno = INFSistemQuery.radniNaloziZaDatum(datum).size();
        return ukupno;
    }

    public String getUkupnoString() {
        return Integer.toString(getUkupno());
    }

    public int getUkFakturisano() {
        fakturisano = INFSistemQuery.faktureZaDatum(datum).size();
        return fakturisano;
    }

    public String getUkFakturisanoString() {
        return String.valueOf(getUkFakturisano());
    }

    public float getFakturisanost() {
        try {
            return 100 * getUkFakturisano() / getUkupno();
        } catch (ArithmeticException e) {
            return 0;
        }
    }

    public String getFakturisanostString() {
        try {
            return Float.toString(getFakturisanost()) + "%";
        } catch (ArithmeticException e) {
            return "";
        }
    }
    //</editor-fold>
}
