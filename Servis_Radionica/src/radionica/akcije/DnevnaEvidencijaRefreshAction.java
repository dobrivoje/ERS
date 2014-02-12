/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package radionica.akcije;

import ERS.queries.ERSQuery;
import com.dobrivoje.utilities.TopCompoment.TopComponentUtils;
import com.dobrivoje.utilities.datumi.DatumSelektor;
import ent.Firma;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import node_klase.interfejsi.actionable.IActionableDnevnaEvidencija;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.awt.StatusDisplayer;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import radionica.UI.DnevnaEvidencijaTopComponent;

@ActionID(
        category = "Menu/Akcije/Dnevna Evidencija",
        id = "akcije.RefreshDnevnaEvidencija")
@ActionRegistration(
        iconBase = "ikonice/refresh/plava.gif",
        displayName = "#CTL_RefreshDnevnaEvidencija")
@ActionReferences({
    @ActionReference(path = "Toolbars/b1_Refresh", position = 43000)
})
@Messages("CTL_RefreshDnevnaEvidencija=Dnevna Evidencija Majstora")
public final class DnevnaEvidencijaRefreshAction implements ActionListener {

    private final IActionableDnevnaEvidencija context;
    //
    private Lookup.Result<DatumSelektor> datumi = null;
    private Lookup.Result<Firma> firma = null;
    //
    private static DatumSelektor datumSelektorLookup = DatumSelektor.getDafault();
    private static Firma firmaLookup;

    public DnevnaEvidencijaRefreshAction(IActionableDnevnaEvidencija context) {
        this.context = context;
        firmaLookup = ERSQuery.AktivneFirme(true).iterator().next();
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        datumi = WindowManager
                .getDefault()
                .findTopComponent("DnevnaEvidencijaTopComponent")
                .getLookup()
                .lookupResult(DatumSelektor.class);

        firma = WindowManager
                .getDefault()
                .findTopComponent("DnevnaEvidencijaTopComponent")
                .getLookup()
                .lookupResult(Firma.class);

        datumi.addLookupListener(
                new LookupListener() {
            @Override
            public void resultChanged(LookupEvent le) {
                Lookup.Result lr = (Lookup.Result) le.getSource();
                Collection<DatumSelektor> datumi = lr.allInstances();

                if (!datumi.isEmpty()) {
                    for (DatumSelektor d : datumi) {
                        datumSelektorLookup = d;
                    }
                }
            }
        });

        firma.addLookupListener(new LookupListener() {
            @Override
            public void resultChanged(LookupEvent le) {
                Lookup.Result lr = (Lookup.Result) le.getSource();
                Collection<Firma> firme = lr.allInstances();

                if (!firme.isEmpty()) {
                    for (Firma f : firme) {
                        firmaLookup = f;
                    }
                }
            }
        });

        TopComponent tc = WindowManager
                .getDefault()
                .findTopComponent("DnevnaEvidencijaTopComponent");
        ((DnevnaEvidencijaTopComponent) tc).refreshAktivniRadnici();
        TopComponentUtils.OpenTopComponent("DnevnaEvidencijaTopComponent");

        StatusDisplayer.getDefault().setStatusText(
                datumSelektorLookup.getYMDDatumOD() + " - " + datumSelektorLookup.getYMDDatumDO() + firmaLookup.getNaziv());

    }
}
