/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servis.manager.QuickSearch;

import ERS.queries.ERSQuery;
import ent.Firma;
import org.netbeans.spi.quicksearch.SearchProvider;
import org.netbeans.spi.quicksearch.SearchRequest;
import org.netbeans.spi.quicksearch.SearchResponse;
import org.openide.awt.StatusDisplayer;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

public class QSERSFirma implements SearchProvider {

    @Override
    public void evaluate(SearchRequest request, SearchResponse response) {
        try {
            for (Firma f : ERSQuery.listaSvihFirmi()) {

                if (f.getNaziv().toLowerCase().contains(request.getText().toLowerCase())) {
                    if (!response.addResult(new RezultatPretrage(f), f.getNaziv())) {
                        return;
                    }
                }
            }
        } catch (NullPointerException npe) {
        }
    }

    private static class RezultatPretrage implements Runnable, Lookup.Provider {

        private final Firma firma;
        private final InstanceContent ic = new InstanceContent();
        private final Lookup lookup = new AbstractLookup(ic);

        public RezultatPretrage(Firma f) {
            this.firma = f;
        }

        @Override
        public void run() {
            try {
                ic.add(firma);
                
                StatusDisplayer.getDefault().setStatusText(
                        firma.getNaziv()
                        + ", " + firma.getAdresa()
                        + ", " + firma.getGrad());

            } catch (NullPointerException npe) {
            }
        }

        @Override
        public Lookup getLookup() {
            return lookup;
        }
    }
}
