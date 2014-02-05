/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package node_klase.firma;

import static ERS.queries.ERSQuery.AktivneFirme;
import com.dobrivoje.utilities.icons.icon_renderer.INodeIconRenderer;
import com.dobrivoje.utilities.icons.icon_renderer.IconRenderer;
import static com.dobrivoje.utilities.icons.icon_renderer.IconType.ProfCentar.FIRMA;
import ent.Firma;
import java.beans.IntrospectionException;
import java.util.List;
import javax.swing.Action;
import node_klase.interfejsi.actionable.IActionableFirma;
import node_klase.orgjed.OrgJedAktivniRadniciChildFactory;
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
public class AktivnaFirmaChildFactory extends ChildFactory<Firma> {

    private final boolean aktivna;

    private class FirmaBeanNode extends BeanNode<Firma>
            implements IActionableFirma {

        private final IconRenderer iconRenderer = IconRenderer.getDefault();

        public FirmaBeanNode(Firma bean) throws IntrospectionException {
            super(bean,
                    Children.create(new OrgJedAktivniRadniciChildFactory(bean), true),
                    Lookups.singleton(bean));

            setDisplayName(bean.getNaziv());

            setShortDescription(bean.getNaziv() + ", "
                    + bean.getPib() + ", "
                    + bean.getMatbr() + ", "
                    + bean.getAdresa() + ", "
                    + bean.getPostanskiBroj() + ", "
                    + bean.getGrad());

            iconRenderer.generateIcons(FIRMA, new INodeIconRenderer() {

                @Override
                public void node_setIconBaseWithExtension(String URL) {
                    setIconBaseWithExtension(URL);
                }
            });
        }

        @Override
        public Action[] getActions(boolean context) {
            List<? extends Action> akcijeZaFirmu
                    = Utilities.actionsForPath("Actions/Menu/Akcije/Firma");

            return akcijeZaFirmu.toArray(new Action[akcijeZaFirmu.size()]);
        }
    }

    public AktivnaFirmaChildFactory(boolean aktivna) {
        this.aktivna = aktivna;
    }

    @Override
    protected boolean createKeys(List<Firma> list) {
        list.addAll(AktivneFirme(aktivna));
        return true;
    }

    @Override
    protected Node createNodeForKey(Firma key) {
        try {
            return new FirmaBeanNode(key);
        } catch (IntrospectionException ex) {
            return null;
        }
    }
}
