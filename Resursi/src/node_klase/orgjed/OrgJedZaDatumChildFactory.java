/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package node_klase.orgjed;

import static ERS.queries.ERSQuery.orgJedFirme;
import com.dobrivoje.utilities.icons.icon_renderer.INodeIconRenderer;
import com.dobrivoje.utilities.icons.icon_renderer.IconRenderer;
import static com.dobrivoje.utilities.icons.icon_renderer.IconType.ProfCentar.ORGJED;
import ent.Firma;
import ent.Orgjed;
import java.beans.IntrospectionException;
import java.util.List;
import javax.swing.Action;
import node_klase.interfejsi.actionable.IActionableOrgJed;
import node_klase.radnici.RadniciZaDatumChildFactory;
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
public class OrgJedZaDatumChildFactory extends ChildFactory<Orgjed> {

    private final Firma firma;
    private final String datum;

    private class OrgJedChildBeanNode extends BeanNode<Orgjed>
            implements IActionableOrgJed {

        private final IconRenderer iconRenderer = IconRenderer.getDefault();

        public OrgJedChildBeanNode(Orgjed orgjed) throws IntrospectionException {
            super(orgjed,
                    Children.create(new RadniciZaDatumChildFactory(orgjed, datum), true),
                    Lookups.singleton(orgjed));

            setDisplayName(orgjed.getNaziv());
            iconRenderer.generateIcons(ORGJED, new INodeIconRenderer() {

                @Override
                public void node_setIconBaseWithExtension(String URL) {
                    setIconBaseWithExtension(URL);
                }
            });
        }

        @Override
        public Action[] getActions(boolean context) {
            List<? extends Action> akcijeIzvestajiZaOrgJed
                    = Utilities.actionsForPath("Actions/Menu/Akcije/Organizaciona Jedinica");

            return akcijeIzvestajiZaOrgJed.toArray(new Action[akcijeIzvestajiZaOrgJed.size()]);
        }
    }

    public OrgJedZaDatumChildFactory(Firma firma, String datum) {
        this.firma = firma;
        this.datum = datum;
    }

    @Override
    protected boolean createKeys(List<Orgjed> list) {
        list.addAll(orgJedFirme(firma));
        return true;

        // kod ispod, je blokirao aplikaciju ukoliko firma nema
        // org.jedinicu ispod sebe, a nema je kad se pravi nova
        // firma !!!
        /*if (!orgJedFirme(firma).isEmpty()) {
         list.addAll(orgJedFirme(firma));
         return true;
         } else {
         return false;
         }*/
    }

    @Override
    protected Node createNodeForKey(Orgjed orgjed) {
        try {
            return new OrgJedChildBeanNode(orgjed);
        } catch (IntrospectionException ex) {
            return null;
        }
    }
}
