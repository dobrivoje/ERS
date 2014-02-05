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
public class RadniNaloziZaStrankuChildFactory extends ChildFactory<Nald001> {

    private final String sifraKupca;

    public RadniNaloziZaStrankuChildFactory(String sifraKupca) {
        this.sifraKupca = sifraKupca;
    }

    @Override
    protected boolean createKeys(List<Nald001> list) {
        list.addAll(INFSistemQuery.radniNaloziZaSifruKupca(sifraKupca));
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
}
