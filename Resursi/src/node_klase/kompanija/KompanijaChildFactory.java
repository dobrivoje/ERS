/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package node_klase.kompanija;

import static ERS.queries.ERSQuery.listaSvihKompanija;
import com.dobrivoje.utilities.icons.icon_renderer.INodeIconRenderer;
import com.dobrivoje.utilities.icons.icon_renderer.IconRenderer;
import static com.dobrivoje.utilities.icons.icon_renderer.IconType.ProfCentar.KOMPANIJA;
import ent.Kompanija;
import java.beans.IntrospectionException;
import java.util.List;
import node_klase.firma.FirmaChildFactory;
import org.openide.nodes.BeanNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author dobri
 */
public class KompanijaChildFactory extends ChildFactory<Kompanija> {

    private class KompanijaBeanNode extends BeanNode<Kompanija> {

        private final IconRenderer iconRenderer = IconRenderer.getDefault();

        public KompanijaBeanNode(Kompanija bean) throws IntrospectionException {
            super(bean, Children.create(new FirmaChildFactory(bean), true), Lookups.singleton(bean));

            setDisplayName(bean.getNazivKompanije() + ", "
                    + bean.getAdresa() + ", "
                    + bean.getGrad() + ", "
                    + bean.getVlasnik());

            iconRenderer.generateIcons(KOMPANIJA, new INodeIconRenderer() {

                @Override
                public void node_setIconBaseWithExtension(String URL) {
                    setIconBaseWithExtension(URL);
                }
            });
        }
    }

    @Override
    protected boolean createKeys(List<Kompanija> lista) {
        lista.addAll(listaSvihKompanija());
        return true;
    }

    @Override
    protected Node createNodeForKey(Kompanija key) {
        try {
            return new KompanijaBeanNode(key);
        } catch (IntrospectionException ex) {
            return null;
        }
    }
}
