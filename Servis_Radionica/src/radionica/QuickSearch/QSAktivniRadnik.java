/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package radionica.QuickSearch;

import ERS.queries.ERSQuery;
import com.dobrivoje.utilities.TopCompoment.TopComponentUtils;
import com.dobrivoje.utilities.warnings.Display;
import ent.Radnik;
import org.netbeans.spi.quicksearch.SearchProvider;
import org.netbeans.spi.quicksearch.SearchRequest;
import org.netbeans.spi.quicksearch.SearchResponse;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IRadnik.class)
public class QSAktivniRadnik implements SearchProvider, IRadnik {

    private static Radnik radnik;

    @Override
    public void evaluate(SearchRequest request, SearchResponse response) {

        try {
            for (Radnik r : ERSQuery.SviAktivniRadniciFirme(request.getText().toLowerCase())) {
                if (!response.addResult(new RezultatPretrage(r),
                        r.getIme() + " " + r.getPrezime()
                        + ", ID-" + r.getIDRadnik()
                        + ", ID INF.SYS-" + r.getSifraINFSISTEM()
                        + ", " + r.getFKIDOrgjed().getFKIDFirma().getNaziv())) {
                    return;
                }
            }
        } catch (NullPointerException npe) {
        }
    }

    @Override
    public Radnik getRadnik() {
        return QSAktivniRadnik.radnik;
    }

    private static class RezultatPretrage implements Runnable {

        private final Radnik radnik;

        public RezultatPretrage(Radnik r) {
            this.radnik = r;
        }

        @Override
        public void run() {
            try {
                QSAktivniRadnik.radnik = this.radnik;
                TopComponentUtils.OpenTopComponent("RadnikTopComponent");
            } catch (NullPointerException e) {
                Display.obavestenjeBaloncic("Greška.", e.getMessage(), Display.TIP_OBAVESTENJA.GRESKA);
            } catch (Exception e) {
                Display.obavestenjeBaloncic("Greška.", e.getMessage(), Display.TIP_OBAVESTENJA.GRESKA);
            }
        }
    }
}
