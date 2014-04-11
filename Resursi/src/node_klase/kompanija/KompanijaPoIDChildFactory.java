/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package node_klase.kompanija;

import static ERS.queries.ERSQuery.kompanijaPoID;
import com.dobrivoje.utilities.icons.icon_renderer.INodeIconRenderer;
import com.dobrivoje.utilities.icons.icon_renderer.IconRenderer;
import static com.dobrivoje.utilities.icons.icon_renderer.IconType.ProfCentar.KOMPANIJA;
import node_klase.firma.FirmaChildFactory;
import ent.Kompanija;
import java.beans.IntrospectionException;
import java.util.List;
import org.openide.nodes.BeanNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author dobri
 */
public class KompanijaPoIDChildFactory extends ChildFactory<Kompanija> {

    private final int IDK;

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

    public KompanijaPoIDChildFactory(int IDK) {
        this.IDK = IDK;
    }

    @Override
    protected boolean createKeys(List<Kompanija> lista) {
        lista.add(kompanijaPoID(IDK));
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
