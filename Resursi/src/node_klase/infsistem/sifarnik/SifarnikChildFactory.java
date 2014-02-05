/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package node_klase.infsistem.sifarnik;

import INFSYS.queries.INFSistemQuery;
import com.dobrivoje.utilities.icons.icon_renderer.INodeIconRenderer;
import com.dobrivoje.utilities.icons.icon_renderer.IconRenderer;
import static com.dobrivoje.utilities.icons.icon_renderer.IconType.Univerzalne.STRANKE;
import com.dobrivoje.utilities.infsistem.Pretraga.SIFARNIK_PRETRAGA;
import ent.infsistem.Sifarnik;
import java.beans.IntrospectionException;
import java.util.List;
import javax.persistence.NoResultException;
import node_klase.infsistem.interfejsi.IActionableSifarnik;
import org.openide.nodes.BeanNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author dobri
 */
public class SifarnikChildFactory extends ChildFactory<Sifarnik> {

    private String kupac;
    private int sifra;
    private final SIFARNIK_PRETRAGA nacinPretrage;
    //
    private int ukupno = 0;

    private class SifraBeanNode extends BeanNode<Sifarnik>
            implements IActionableSifarnik {

        private final IconRenderer ir = IconRenderer.getDefault();

        public SifraBeanNode(Sifarnik sifra) throws IntrospectionException {
            super(sifra, Children.LEAF, Lookups.singleton(sifra));

            setShortDescription(sifra.getIme() + "," + sifra.getAdresa());
            setDisplayName(sifra.getIme() + "," + sifra.getAdresa() + ", šifra: " + sifra.getKupac());

            ir.generateIcons(STRANKE, new INodeIconRenderer() {
                @Override
                public void node_setIconBaseWithExtension(String URL) {
                    setIconBaseWithExtension(URL);
                }
            });

        }
    }

    public SifarnikChildFactory(String kupac, SIFARNIK_PRETRAGA nacinPretrage) {
        this.kupac = kupac;
        this.nacinPretrage = nacinPretrage;
    }

    public SifarnikChildFactory(int sifra, SIFARNIK_PRETRAGA nacinPretrage) {
        this.sifra = sifra;
        this.nacinPretrage = nacinPretrage;
    }

    @Override
    protected boolean createKeys(List<Sifarnik> list) {
        try {
            switch (nacinPretrage) {
                case KUPAC_PO_NAZIVU:
                    list.addAll(INFSistemQuery.KupciPoNazivu(kupac));
                    break;
                case KUPAC_PO_SIFRI:
                    list.add(INFSistemQuery.KupacPoSifri(sifra));
                    break;
                case KUPAC_PO_PIB:
                    list.addAll(INFSistemQuery.KupacPoPIB(kupac));
                    break;
                case KUPAC_PO_MATBR:
                    list.add(INFSistemQuery.KupacPoMatBr(kupac));
                    break;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    protected Node createNodeForKey(Sifarnik key) {
        try {
            return new SifraBeanNode(key);
        } catch (IntrospectionException ex) {
            return null;
        }
    }

    public int getUkupno() {
        try {
            switch (nacinPretrage) {
                case KUPAC_PO_NAZIVU:
                    ukupno = INFSistemQuery.KupciPoNazivu(kupac).size();
                    break;
                case KUPAC_PO_SIFRI:
                case KUPAC_PO_MATBR:
                    ukupno = 1;
                    break;
                case KUPAC_PO_PIB:
                    ukupno = INFSistemQuery.KupacPoPIB(kupac).size();
                    break;
            }
        } catch (NullPointerException | NoResultException e) {
        }

        return ukupno;
    }

    public String getUkupnoString() {
        return String.valueOf(getUkupno());
    }
}
