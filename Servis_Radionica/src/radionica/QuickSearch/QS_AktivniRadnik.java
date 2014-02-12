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
public class QS_AktivniRadnik implements SearchProvider, IRadnik {

    private static Radnik radnik;
    private static final String topComponentID = "RadnikTopComponent";

    @Override
    public void evaluate(SearchRequest request, SearchResponse response) {

        try {
            for (Radnik r : ERSQuery.SviAktivniRadniciFirme(request.getText().toLowerCase())) {
                if (!response.addResult(new RezultatPretrage(r, topComponentID),
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
        return QS_AktivniRadnik.radnik;
    }

    private static class RezultatPretrage implements Runnable {

        private final Radnik radnik;
        private String topComponentID;

        public RezultatPretrage(Radnik r) {
            this.radnik = r;
        }

        public RezultatPretrage(Radnik r, String topComponentID) {
            this.radnik = r;
            this.topComponentID = topComponentID;
        }

        @Override
        public void run() {
            try {
                QS_AktivniRadnik.radnik = this.radnik;

                TopComponentUtils.OpenTopComponent(topComponentID);
            } catch (NullPointerException e1) {
                Display.obavestenjeBaloncic("Greška.", e1.toString(), Display.TIP_OBAVESTENJA.GRESKA);
            } catch (Exception e2) {
                Display.obavestenjeBaloncic("Greška.", e2.toString(), Display.TIP_OBAVESTENJA.GRESKA);
            }
        }
    }
}
