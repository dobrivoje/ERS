/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package node_klase.radnici;

import ERS.queries.ERSQuery;
import com.dobrivoje.utilities.icons.icon_renderer.INodeIconRenderer;
import com.dobrivoje.utilities.icons.icon_renderer.IconRenderer;
import static com.dobrivoje.utilities.icons.icon_renderer.IconType.ProfCentar.RADNIK;
import ent.Firma;
import ent.Radnik;
import java.beans.IntrospectionException;
import java.util.List;
import javax.swing.Action;
import node_klase.interfejsi.actionable.IActionableRadnik;
import org.openide.nodes.BeanNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Utilities;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author dobri
 */
public class AktivniRadniciFirmeChildFactory extends ChildFactory<Radnik> {

    private final Firma aktivnaFirma;

    private class AktivniRadnikBeanNode extends BeanNode<Radnik> implements IActionableRadnik {

        private final IconRenderer iconRenderer = IconRenderer.getDefault();

        public AktivniRadnikBeanNode(Radnik bean, String PodatakZaPrikazivanje) throws IntrospectionException {
            super(bean, Children.LEAF, Lookups.singleton(bean));

            iconRenderer.generateIcons(RADNIK, new INodeIconRenderer() {

                @Override
                public void node_setIconBaseWithExtension(String URL) {
                    setIconBaseWithExtension(URL);
                }
            });
            
            setShortDescription(PodatakZaPrikazivanje);
        }

        @Override
        public Action[] getActions(boolean context) {
            List<? extends Action> akcijeZaRadnika
                    = Utilities.actionsForPath("Actions/Menu/Akcije/Radnik");

            return akcijeZaRadnika.toArray(new Action[akcijeZaRadnika.size()]);
        }
    }

    public AktivniRadniciFirmeChildFactory(Firma aktivnaFirma) {
        this.aktivnaFirma = aktivnaFirma;
    }

    @Override
    protected boolean createKeys(List<Radnik> list) {
        list.addAll(ERSQuery.aktivniRadniciFirme(aktivnaFirma));
        return true;
    }

    @Override
    protected Node createNodeForKey(Radnik key) {
        try {
            AktivniRadnikBeanNode n = new AktivniRadnikBeanNode(key, "");
            n.setName(key.getIme() + " " + key.getPrezime());
            n.setIconBaseWithExtension("ikonice/users/user4.24x24.gif");

            return n;
        } catch (IntrospectionException ex) {
            return null;
        }
    }
}
