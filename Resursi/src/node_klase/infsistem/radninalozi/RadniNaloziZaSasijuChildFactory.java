/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package node_klase.infsistem.radninalozi;

import INFSYS.Queries.INFSistemQuery;
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
public class RadniNaloziZaSasijuChildFactory extends ChildFactory<Nald001> {

    private final String sasija;

    public RadniNaloziZaSasijuChildFactory(String sasija) {
        this.sasija = sasija;
    }

    @Override
    protected boolean createKeys(List<Nald001> list) {
        list.addAll(INFSistemQuery.radniNaloziZaSasiju(sasija));

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
        return (INFSistemQuery.radniNaloziZaSasiju(sasija).size());
    }

    public String getUkupnoString() {
        return String.valueOf(getUkupno());
    }
}
