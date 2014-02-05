/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TerminPlaner.QuickSearch;

import INFSYS.queries.INFSistemQuery;
import static com.dobrivoje.utilities.TopCompoment.TopComponentUtils.OpenTopComponent;
import com.dobrivoje.utilities.warnings.Display;
import ent.infsistem.Sifarnik;
import org.netbeans.spi.quicksearch.SearchProvider;
import org.netbeans.spi.quicksearch.SearchRequest;
import org.netbeans.spi.quicksearch.SearchResponse;
import org.openide.awt.StatusDisplayer;

public class ServisiKlijenataQS implements SearchProvider {

    private static Sifarnik sifra = null;

    @Override
    public void evaluate(SearchRequest request, SearchResponse response) {

        try {
            for (Sifarnik s : INFSistemQuery.KupciPoNazivu(request.getText())) {
                if (!response.addResult(new RezultatPretrage(s),
                        s.getIme() + " ,Šifra: " + s.getKupac()
                        + (s.getAdresa() != null ? ", " + s.getAdresa() : ""))) {
                    return;
                }
            }

        } catch (NullPointerException npe) {
            StatusDisplayer.getDefault().setStatusText("Greška u pretraživanju. " + npe.getMessage());
        }
    }

    public static Sifarnik getSifra() {
        return sifra;
    }

    private static class RezultatPretrage implements Runnable {

        private final Sifarnik sifra;

        public RezultatPretrage(Sifarnik sifra) {
            this.sifra = sifra;
        }

        @Override
        public void run() {
            try {
                ServisiKlijenataQS.sifra = this.sifra;

                OpenTopComponent("RadoviNaAutomobiluTopComponent");
            } catch (NullPointerException npe) {
                Display.obavestenjeBaloncic("Greška.", npe.getMessage(), Display.TIP_OBAVESTENJA.GRESKA);
            }
        }
    }
}
