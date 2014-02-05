/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TerminPlaner.QuickSearch;

import INFSYS.queries.INFSistemQuery;
import static com.dobrivoje.utilities.TopCompoment.TopComponentUtils.OpenTopComponent;
import ent.infsistem.Nald001;
import java.util.List;
import org.netbeans.spi.quicksearch.SearchProvider;
import org.netbeans.spi.quicksearch.SearchRequest;
import org.netbeans.spi.quicksearch.SearchResponse;
import org.openide.awt.StatusDisplayer;

public class NalogDelimicanOpisQS implements SearchProvider {

    private static Nald001 nalog = null;
    private static final int MAX_BROJ_NALOGA = 30;

    @Override
    public void evaluate(SearchRequest request, SearchResponse response) {
        List<Nald001> nalozi = INFSistemQuery.RNPoDelimicnomOpisu2(request.getText());;

        try {
            for (Nald001 n : nalozi) {
                if (!response.addResult(new RezultatPretrage(n),
                        n.getRadnal() + " ,Stranka: " + n.getKupac()
                        + (n.getBrFakt() != null ? "/" + n.getBrFakt() : ""))) {
                    return;
                }
            }
        } catch (NullPointerException npe) {
            StatusDisplayer.getDefault().setStatusText("Greška u pretraživanju. " + npe.getMessage());
        }
    }

    public static Nald001 getNalog() {
        return nalog;

    }

    private static class RezultatPretrage implements Runnable {

        private final Nald001 nalog;

        public RezultatPretrage(Nald001 nalog) {
            this.nalog = nalog;
        }

        @Override
        public void run() {
            try {
                NalogDelimicanOpisQS.nalog = this.nalog;

                OpenTopComponent("RadoviNaAutomobiluTopComponent");
            } catch (NullPointerException npe) {
            }
        }
    }
}
