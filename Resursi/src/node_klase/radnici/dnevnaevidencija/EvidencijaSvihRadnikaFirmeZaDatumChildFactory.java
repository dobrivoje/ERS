/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package node_klase.radnici.dnevnaevidencija;

import ERS.queries.ERSQuery;
import com.dobrivoje.utilities.icons.icon_renderer.INodeIconRenderer;
import com.dobrivoje.utilities.icons.icon_renderer.IconRenderer;
import static com.dobrivoje.utilities.icons.icon_renderer.IconType.ProfCentar.RADNIK;
import ent.Firma;
import ent.Raddan;
import java.beans.IntrospectionException;
import java.util.List;
import javax.swing.Action;
import node_klase.interfejsi.actionable.IActionableDnevnaEvidencija;
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
public class EvidencijaSvihRadnikaFirmeZaDatumChildFactory extends ChildFactory<Raddan> {

    private final Firma firma;
    private final String datum;

    private class DnevnaEvidencijaSvihRadnikaBeanNode extends BeanNode<Raddan>
            implements IActionableDnevnaEvidencija {

        private final IconRenderer iconRenderer = IconRenderer.getDefault();

        public DnevnaEvidencijaSvihRadnikaBeanNode(Raddan raddan) throws IntrospectionException {
            super(raddan, Children.LEAF, Lookups.singleton(raddan));

            setName(raddan.getStatus() + " " + raddan.getStatusZnacenje());
            iconRenderer.generateIcons(RADNIK, new INodeIconRenderer() {

                @Override
                public void node_setIconBaseWithExtension(String URL) {
                    setIconBaseWithExtension(URL);
                }
            });
        }

        @Override
        public Action[] getActions(boolean context) {
            List<? extends Action> akcijeZaDnevnuEvidenciju
                    = Utilities.actionsForPath("Actions/Menu/Akcije/Dnevna Evidencija");
            return akcijeZaDnevnuEvidenciju.toArray(new Action[akcijeZaDnevnuEvidenciju.size()]);
        }
    }

    public EvidencijaSvihRadnikaFirmeZaDatumChildFactory(Firma firma, String datum) {
        this.firma = firma;
        this.datum = datum;
    }

    @Override
    protected boolean createKeys(List<Raddan> list) {
        list.addAll(ERSQuery.evidencijaSvihRadnikaFirmeZaDatum(firma, datum));
        return true;
    }

    @Override
    protected Node createNodeForKey(Raddan key) {
        try {
            return new DnevnaEvidencijaSvihRadnikaBeanNode(key);
        } catch (IntrospectionException ex) {
            return null;
        }
    }
}
