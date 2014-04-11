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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class RadniciZaDatumChildFactory extends ChildFactory<Radnik> {

    private final Orgjed orgjed;
    private final String datum;
    //pomocne 
    private Date danasnjiDatum;
    private Date datum1;

    private class RadnikBeanNode extends BeanNode<Radnik>
            implements IActionableRadnik {

        private final IconRenderer iconRenderer = IconRenderer.getDefault();

        public RadnikBeanNode(Radnik radnik) throws IntrospectionException {
            super(radnik, Children.LEAF, Lookups.singleton(radnik));

            setName(radnik.getIme() + " " + radnik.getPrezime());

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

    public RadniciZaDatumChildFactory(Orgjed orgjed, String datum) {
        this.orgjed = orgjed;
        this.datum = datum;

        try {
            String dd = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            danasnjiDatum = new SimpleDateFormat("yyyy-MM-dd").parse(dd);

            // datum1 je odabrani datum ali kao Date podatak, moramo ga pretvoriti u Date
            // da bi smo ga mogli upoređivati sa današnjim datumom !
            datum1 = new SimpleDateFormat("yyyy-MM-dd").parse(datum);
        } catch (ParseException ex) {
        }
    }

    @Override
    protected boolean createKeys(List<Radnik> list) {
        if (datum1.before(danasnjiDatum)) {
            list.addAll(ERSQuery.radniciOrgJedZaDatum(orgjed, datum));
        } else {
            list.addAll(ERSQuery.aktivniRadniciOrgJed(orgjed, true));
        }

        return true;
    }

    @Override
    protected Node createNodeForKey(Radnik key) {
        try {
            return new RadnikBeanNode(key);
        } catch (IntrospectionException ex) {
            return null;
        }
    }
}
