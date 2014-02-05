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
public class RadniNaloziRegBrojChildFactory extends ChildFactory<Nald001> {

    private final String regBroj;
    private final boolean delimicno;

    private int ukupno = 0;

    public RadniNaloziRegBrojChildFactory(String regBroj, boolean delimicno) {
        this.regBroj = regBroj;
        this.delimicno = delimicno;
    }

    @Override
    protected boolean createKeys(List<Nald001> list) {
        list.addAll(delimicno
                ? INFSistemQuery.radniNaloziDelimicnaRegistracija(regBroj)
                : INFSistemQuery.radniNaloziZaRegistraciju(regBroj));

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
        ukupno = (delimicno
                ? INFSistemQuery.radniNaloziDelimicnaRegistracija(regBroj).size()
                : INFSistemQuery.radniNaloziZaRegistraciju(regBroj).size());

        return ukupno;
    }

    public String getUkupnoString() {
        return String.valueOf(ukupno);
    }
}
