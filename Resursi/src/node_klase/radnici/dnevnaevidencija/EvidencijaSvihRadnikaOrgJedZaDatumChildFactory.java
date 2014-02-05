/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package node_klase.radnici.dnevnaevidencija;

import ERS.queries.ERSQuery;
import com.dobrivoje.utilities.icons.icon_renderer.INodeIconRenderer;
import com.dobrivoje.utilities.icons.icon_renderer.IconRenderer;
import static com.dobrivoje.utilities.icons.icon_renderer.IconType.ProfCentar.RADNIK;
import ent.Orgjed;
import ent.Raddan;
import java.beans.IntrospectionException;
import java.util.List;
import org.openide.nodes.BeanNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 *
 * @author dobri
 */
public class EvidencijaSvihRadnikaOrgJedZaDatumChildFactory extends ChildFactory<Raddan> {

    private final Orgjed orgjed;
    private final String datum;

    private class DnevnaEvidencijaSvihRadnikaBeanNode extends BeanNode<Raddan> {

        private final IconRenderer iconRenderer = IconRenderer.getDefault();

        public DnevnaEvidencijaSvihRadnikaBeanNode(Raddan raddan) throws IntrospectionException {
            super(raddan, Children.LEAF);

            setName(raddan.getStatus() + " " + raddan.getStatusZnacenje());
            iconRenderer.generateIcons(RADNIK, new INodeIconRenderer() {

                @Override
                public void node_setIconBaseWithExtension(String URL) {
                    setIconBaseWithExtension(URL);
                }
            });
        }
    }

    public EvidencijaSvihRadnikaOrgJedZaDatumChildFactory(Orgjed orgjed, String datum) {
        this.orgjed = orgjed;
        this.datum = datum;
    }

    @Override
    protected boolean createKeys(List<Raddan> list) {
        list.addAll(ERSQuery.evidencijaSvihRadnikaOrgJedZaDatum(orgjed, datum));
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
