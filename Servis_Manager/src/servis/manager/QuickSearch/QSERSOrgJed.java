/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servis.manager.QuickSearch;

import ERS.queries.ERSQuery;
import ent.Orgjed;
import org.netbeans.spi.quicksearch.SearchProvider;
import org.netbeans.spi.quicksearch.SearchRequest;
import org.netbeans.spi.quicksearch.SearchResponse;
import org.openide.awt.StatusDisplayer;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

public class QSERSOrgJed implements SearchProvider {

    @Override
    public void evaluate(SearchRequest request, SearchResponse response) {
        try {
            for (Orgjed o : ERSQuery.listaSvihORGJED()) {

                if (o.getNaziv().toLowerCase().contains(request.getText().toLowerCase())) {
                    if (!response.addResult(new RezultatPretrage(o), o.getNaziv())) {
                        return;
                    }
                }
            }
        } catch (NullPointerException npe) {
        }
    }

    private static class RezultatPretrage implements Runnable, Lookup.Provider {

        private final Orgjed orgjed;
        private final InstanceContent ic = new InstanceContent();
        private final Lookup lookup = new AbstractLookup(ic);

        public RezultatPretrage(Orgjed o) {
            this.orgjed = o;
        }

        @Override
        public void run() {
            try {
                ic.add(orgjed);
                
                StatusDisplayer.getDefault().setStatusText(
                        orgjed.getNaziv()
                        + ", " + orgjed.getNaziv()
                        + ", " + orgjed.getSifra());
            } catch (NullPointerException npe) {
            }
        }

        @Override
        public Lookup getLookup() {
            return lookup;
        }
    }
}
