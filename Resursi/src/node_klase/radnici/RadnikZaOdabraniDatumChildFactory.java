/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package node_klase.radnici;

import ERS.queries.ERSQuery;
import com.dobrivoje.utilities.icons.icon_renderer.INodeIconRenderer;
import com.dobrivoje.utilities.icons.icon_renderer.IconRenderer;
import static com.dobrivoje.utilities.icons.icon_renderer.IconType.ProfCentar.RADNIK;
import ent.Orgjed;
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
public class RadnikZaOdabraniDatumChildFactory extends ChildFactory<Radnik> {

    private final Orgjed orgJedRadnika;
    private final String ZaOdabraniDatum;

    private class RadnikBeanNode extends BeanNode<Radnik> implements IActionableRadnik {

        private final IconRenderer iconRenderer = IconRenderer.getDefault();

        public RadnikBeanNode(Radnik r, String PodatakZaPrikazivanje) throws IntrospectionException {
            super(r, Children.LEAF, Lookups.singleton(r));

            setName(r.getIme() + " " + r.getPrezime());
            setShortDescription(PodatakZaPrikazivanje);

            iconRenderer.generateIcons(RADNIK, new INodeIconRenderer() {

                @Override
                public void node_setIconBaseWithExtension(String URL) {
                    setIconBaseWithExtension(URL);
                }
            });
        }

        @Override
        public Action[] getActions(boolean context) {
            List<? extends Action> akcijeZaRadnika
                    = Utilities.actionsForPath("Actions/Menu/Akcije/Radnik");

            return akcijeZaRadnika.toArray(new Action[akcijeZaRadnika.size()]);
        }
    }

    public RadnikZaOdabraniDatumChildFactory(Orgjed orgJedRadnika, String ZaOdabraniDatum) {
        this.orgJedRadnika = orgJedRadnika;
        this.ZaOdabraniDatum = ZaOdabraniDatum;
    }

    @Override
    protected boolean createKeys(List<Radnik> list) {
        list.addAll(ERSQuery.radniciZaDatum(ZaOdabraniDatum));
        return true;
    }

    @Override
    protected Node createNodeForKey(Radnik key) {
        try {
            return new RadnikBeanNode(key, "");
        } catch (IntrospectionException e) {
            return null;
        }
    }
}
