/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package node_klase;

import ERS.queries.ERSQuery;
import com.dobrivoje.utilities.icons.icon_renderer.INodeIconRenderer;
import com.dobrivoje.utilities.icons.icon_renderer.IconRenderer;
import static com.dobrivoje.utilities.icons.icon_renderer.IconType.ProfCentar.RADDAN;
import ent.Raddan;
import ent.Radnik;
import java.beans.IntrospectionException;
import java.util.List;
import node_klase.interfejsi.actionable.IActionableEvidencijeRadnika;
import org.openide.nodes.BeanNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;

/**
 *
 * @author dobri
 */
public class RADDANZaDatumChildFactory extends ChildFactory<Raddan> {

    private final Radnik radnik;
    private String datum;

    private class RADDANZaDatumBean extends BeanNode<Raddan>
            implements IActionableEvidencijeRadnika {

        private final IconRenderer cir = IconRenderer.getDefault();

        public RADDANZaDatumBean(Raddan raddan) throws IntrospectionException {
            super(raddan);
            cir.generateIcons(RADDAN, new INodeIconRenderer() {

                @Override
                public void node_setIconBaseWithExtension(String URL) {
                    setIconBaseWithExtension(URL);
                }
            });
                    
        }
    }

    public RADDANZaDatumChildFactory(Radnik radnik, String datum) {
        this.radnik = radnik;
        this.datum = datum;
    }

    public RADDANZaDatumChildFactory(Radnik radnik) {
        this.radnik = radnik;
    }

    @Override
    protected boolean createKeys(List<Raddan> list) {
        list.addAll(ERSQuery.evidencijeRadnikaZaDatum(radnik, datum));
        return true;
    }

    @Override
    protected Node createNodeForKey(Raddan key) {
        try {
            return new RADDANZaDatumBean(key);
        } catch (IntrospectionException ex) {
            return null;
        }
    }
}
