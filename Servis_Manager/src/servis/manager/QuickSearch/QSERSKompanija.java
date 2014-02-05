/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servis.manager.QuickSearch;

import ERS.queries.ERSQuery;
import ent.Kompanija;
import org.netbeans.spi.quicksearch.SearchProvider;
import org.netbeans.spi.quicksearch.SearchRequest;
import org.netbeans.spi.quicksearch.SearchResponse;
import org.openide.awt.StatusDisplayer;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

public class QSERSKompanija implements SearchProvider {

    @Override
    public void evaluate(SearchRequest request, SearchResponse response) {
        try {
            for (Kompanija k : ERSQuery.listaSvihKompanija()) {

                if (k.getNazivKompanije().toLowerCase().contains(request.getText().toLowerCase())) {
                    if (!response.addResult(new RezultatPretrage(k), k.getNazivKompanije())) {
                        return;
                    }
                }
            }
        } catch (NullPointerException npe) {
        }
    }

    private static class RezultatPretrage implements Runnable, Lookup.Provider {

        private final Kompanija kompanija;
        private final InstanceContent ic = new InstanceContent();
        private final Lookup lookup = new AbstractLookup(ic);

        public RezultatPretrage(Kompanija k) {
            this.kompanija = k;
        }

        @Override
        public void run() {
            try {
                ic.add(kompanija);
                
                StatusDisplayer.getDefault().setStatusText(
                        kompanija.getNazivKompanije()
                        + ", " + kompanija.getAdresa()
                        + ", " + kompanija.getGrad());
            } catch (NullPointerException npe) {
            }
        }

        @Override
        public Lookup getLookup() {
            return lookup;
        }
    }
}
