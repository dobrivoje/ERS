/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package node_klase.infsistem.klijent.servisi;

import INFSYS.queries.INFSistemQuery;
import com.dobrivoje.utilities.cars.icon_renderer.ChasisIconRenderer;
import com.dobrivoje.utilities.cars.icon_renderer.INodeIconRenderer;
import java.beans.IntrospectionException;
import java.util.List;
import node_klase.infsistem.interfejsi.IActionableSifarnik;
import node_klase.infsistem.radninalozi.RadniNaloziZaSasijuChildFactory;
import org.openide.nodes.BeanNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 *
 * @author dobri
 */
public class VozilaKlijentaChildFactory extends ChildFactory<String> {

    private final String sifraKlijenta;

    private class VozilaKlijentaBeanNode extends BeanNode<String>
            implements IActionableSifarnik {

        private final ChasisIconRenderer cir = ChasisIconRenderer.getDefault();

        public VozilaKlijentaBeanNode(String sasija) throws IntrospectionException {
            super(sasija, Children.create(new RadniNaloziZaSasijuChildFactory(sasija), true));

            setDisplayName(" " + sasija);
            setShortDescription(" " + sasija);

            cir.generateIcons(sasija, new INodeIconRenderer() {
                @Override
                public void node_setIconBaseWithExtension(String URL) {
                    setIconBaseWithExtension(URL);
                }
            });
        }
    }

    public VozilaKlijentaChildFactory(String sifraKlijenta) {
        this.sifraKlijenta = sifraKlijenta;
    }

    @Override
    protected boolean createKeys(List<String> list) {
        list.addAll(INFSistemQuery.VozilaKlijenta(sifraKlijenta));

        return true;
    }

    @Override
    protected Node createNodeForKey(String sifraKlijenta) {
        try {
            return new VozilaKlijentaBeanNode(sifraKlijenta);
        } catch (IntrospectionException ex) {
            return null;
        }
    }

    public int getUkupno() {
        return INFSistemQuery.VozilaKlijenta(sifraKlijenta).size();
    }

    public String getUkupnoString() {
        return String.valueOf(getUkupno());
    }
}
