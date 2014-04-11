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
import node_klase.orgjed.OrgJedZaDatumChildFactory;
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
public class AktivnaFirmaZaDatumChildFactory extends ChildFactory<Firma> {

    private final boolean aktivna;
    private final String datum;

    private class FirmaBeanNode extends BeanNode<Firma> implements IActionableFirma {

        private final IconRenderer iconRenderer = IconRenderer.getDefault();

        public FirmaBeanNode(Firma firma) throws IntrospectionException {
            super(firma,
                    Children.create(
                            new OrgJedZaDatumChildFactory(firma, datum), true),
                    Lookups.singleton(firma));

            setDisplayName(firma.getNaziv());

            setShortDescription(firma.getNaziv() + ", "
                    + firma.getPib() + ", "
                    + firma.getMatbr() + ", "
                    + firma.getAdresa() + ", "
                    + firma.getPostanskiBroj() + ", "
                    + firma.getGrad());

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

    public AktivnaFirmaZaDatumChildFactory(boolean aktivna, String datum) {
        this.aktivna = aktivna;
        this.datum = datum;
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
