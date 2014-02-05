/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package node_klase.infsistem.sifarnik;

import INFSYS.queries.INFSistemQuery;
import com.dobrivoje.utilities.icons.icon_renderer.INodeIconRenderer;
import com.dobrivoje.utilities.icons.icon_renderer.IconRenderer;
import static com.dobrivoje.utilities.icons.icon_renderer.IconType.Univerzalne.STRANKE;
import ent.infsistem.Sifarnik;
import java.beans.IntrospectionException;
import java.util.List;
import node_klase.infsistem.interfejsi.IActionableSifarnik;
import node_klase.infsistem.klijent.servisi.VozilaKlijentaChildFactory;
import org.openide.nodes.BeanNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author dobri
 */
public class SifraSasijeChildFactory extends ChildFactory<Sifarnik> {

    private String kupac;
    private int sifra;
    private final int varijanta;

    private class SifraSasijeBeanNode extends BeanNode<Sifarnik>
            implements IActionableSifarnik {

        private final IconRenderer ir = IconRenderer.getDefault();

        public SifraSasijeBeanNode(Sifarnik sifra) throws IntrospectionException {
            super(sifra,
                    Children.create(new VozilaKlijentaChildFactory(kupac), true),
                    Lookups.singleton(sifra));

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

    public SifraSasijeChildFactory(String kupac, int varijanta) {
        this.kupac = kupac;
        this.varijanta = varijanta;
    }

    public SifraSasijeChildFactory(int sifra, int varijanta) {
        this.sifra = sifra;
        this.varijanta = varijanta;
    }

    @Override
    protected boolean createKeys(List<Sifarnik> list) {
        try {
            switch (varijanta) {
                case 1:
                    list.addAll(INFSistemQuery.KupciPoNazivu(kupac));
                    break;
                case 2:

                    list.add(INFSistemQuery.KupacPoSifri(sifra));

                    break;
                case 3:
                    list.addAll(INFSistemQuery.KupacPoPIB(kupac));
                    break;
                case 4:
                    list.add(INFSistemQuery.KupacPoMatBr(kupac));
                    break;
            }
        } catch (Exception e) {
        }
        return true;
    }

    @Override
    protected Node createNodeForKey(Sifarnik key) {
        try {
            return new SifraSasijeBeanNode(key);
        } catch (IntrospectionException ex) {
            return null;
        }
    }
}
