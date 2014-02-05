/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ERS.SearchProviders.Sifarnik;

import INFSYS.queries.INFSistemQuery;
import ent.infsistem.Sifarnik;
import java.util.ArrayList;
import java.util.List;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 *
 * @author dobri
 */
public class SifarnikLookupProvider implements Lookup.Provider {

    private final InstanceContent ic;
    private final Lookup lookup;
    private final List<Sifarnik> sifre;

    public SifarnikLookupProvider() {
        ic = new InstanceContent();
        lookup = new AbstractLookup(ic);
        sifre = new ArrayList<>();
    }

    public void setUp(String pretraga) {

        ic.add(new ISearchSifarnici() {

            @Override
            public List<Sifarnik> getSifra(String pojam) {
                try {
                    for (Sifarnik s : INFSistemQuery.KupciPoNazivu(pojam)) {
                        sifre.add(s);
                    }

                    return sifre;

                } catch (NullPointerException npe) {
                    return null;
                }
            }
        });
    }

    public List<Sifarnik> getSifre() {
        return sifre;
    }

    @Override
    public Lookup getLookup() {
        return lookup;
    }
}
