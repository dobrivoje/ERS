/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TerminPlaner.QuickSearch;

import cenovnik_RD.ent.Cene;
import com.dobrivoje.utilities.warnings.Display;
import org.netbeans.spi.quicksearch.SearchProvider;
import org.netbeans.spi.quicksearch.SearchRequest;
import org.netbeans.spi.quicksearch.SearchResponse;
import org.openide.awt.StatusDisplayer;

public class CenovnikRD_QS implements SearchProvider {

    private static Cene cene = null;

    @Override
    public void evaluate(SearchRequest request, SearchResponse response) {

        if (request.getText().length() > 7) {
            try {
                for (Cene cena : cenovnik_RD.queries.CRDQuery.ceneKatBrojeva4(request.getText())) {
                    if (!response.addResult(new RezultatPretrage(cena),
                            cena.getKatbroj() + " ,cena: " + cena.getCena())) {
                        return;
                    }
                }

            } catch (NullPointerException npe) {
                StatusDisplayer.getDefault().setStatusText("Greška u pretraživanju. " + npe.getMessage());
            }
        }
    }

    public static Cene getSifra() {
        return cene;
    }

    private static class RezultatPretrage implements Runnable {

        private final Cene cene;

        public RezultatPretrage(Cene sifra) {
            this.cene = sifra;
        }

        @Override
        public void run() {
            try {
                CenovnikRD_QS.cene = this.cene;
                //OpenTopComponent("RadoviNaAutomobiluTopComponent");
            } catch (NullPointerException npe) {
                Display.obavestenjeBaloncic("Greška.", npe.getMessage(), Display.TIP_OBAVESTENJA.GRESKA);
            }
        }
    }
}
