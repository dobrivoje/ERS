/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TerminPlaner.QuickSearch;

import INFSYS.Queries.INFSistemQuery;
import static com.dobrivoje.utilities.TopCompoment.TopComponentUtils.OpenTopComponent;
import com.dobrivoje.utilities.warnings.Display;
import ent.infsistem.Sifarnik;
import org.netbeans.spi.quicksearch.SearchProvider;
import org.netbeans.spi.quicksearch.SearchRequest;
import org.netbeans.spi.quicksearch.SearchResponse;
import org.openide.awt.StatusDisplayer;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = ISifra.class)
public class QS_ServisiKlijenata implements SearchProvider, ISifra {

    private static Sifarnik sifra;
    private static final String topComponentID = "RadoviNaAutomobiluTopComponent";

    @Override
    public void evaluate(SearchRequest request, SearchResponse response) {

        try {
            for (Sifarnik s : INFSistemQuery.KupciPoNazivu(request.getText())) {
                if (!response.addResult(new RezultatPretrage(s, topComponentID),
                        s.getIme() + " ,Šifra: " + s.getKupac()
                        + (s.getAdresa() != null ? ", " + s.getAdresa() : ""))) {
                    return;
                }
            }

        } catch (NullPointerException npe) {
            StatusDisplayer.getDefault().setStatusText("Greška u pretraživanju. " + npe.getMessage());
        }
    }

    @Override
    public Sifarnik getSifra() {
        return QS_ServisiKlijenata.sifra;
    }

    private static class RezultatPretrage implements Runnable {

        private static Sifarnik sifra;
        private final String topComponentID;

        public RezultatPretrage(Sifarnik sifra, String topComponentID) {
            RezultatPretrage.sifra = sifra;
            this.topComponentID = topComponentID;
        }

        @Override
        public void run() {
            try {
                QS_ServisiKlijenata.sifra = sifra;

                OpenTopComponent(this.topComponentID);
            } catch (NullPointerException npe) {
                Display.obavestenjeBaloncic("Greška.", npe.getMessage(), Display.TIP_OBAVESTENJA.GRESKA);
            }
        }
    }
}
