/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servis.manager.izvestaji.akcije;

import ent.Firma;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import node_klase.interfejsi.actionable.IActionableFirma;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.awt.StatusDisplayer;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import pretrazivac.PretrazivacTopComponent;

@ActionID(
        category = "Menu/Akcije/Firma",
        id = "pretrazivac.akcije.FirmaRefreshAction")
@ActionRegistration(
        iconBase = "ikonice/refresh/crna.gif",
        displayName = "#CTL_FirmaRefreshAction")
@ActionReferences({
    @ActionReference(path = "Menu/Akcije/Firma", position = 3210, separatorBefore = 3209, separatorAfter = 3211),
    @ActionReference(path = "Toolbars/b1_Refresh", position = 40000)
})
@Messages("CTL_FirmaRefreshAction=Sinhro. Podataka Firme")
public final class ActionFirmaRefresh implements ActionListener {

    private final IActionableFirma context;
    //
    private Lookup.Result<Firma> resultFirme;
    private Lookup.Result<String> resultDatumi;
    //
    private String datum = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    public ActionFirmaRefresh(IActionableFirma context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        resultFirme = Utilities
                .actionsGlobalContext()
                .lookupResult(Firma.class);

        resultDatumi = WindowManager
                .getDefault()
                .findTopComponent("PretrazivacTopComponent")
                .getLookup()
                .lookupResult(String.class);

        resultFirme.addLookupListener(new LookupListener() {
            @Override
            public void resultChanged(LookupEvent le) {
                Lookup.Result lr = (Lookup.Result) le.getSource();
                Collection<? extends Firma> firme = lr.allInstances();
                for (Firma f1 : firme) {
                    StatusDisplayer.getDefault().setStatusText(f1.toString());
                }
            }
        });

        resultDatumi.addLookupListener(new LookupListener() {
            @Override
            public void resultChanged(LookupEvent le) {
                Lookup.Result lr = (Lookup.Result) le.getSource();
                Collection<String> datumi = lr.allInstances();
                for (String d1 : datumi) {
                    datum = d1;
                    StatusDisplayer.getDefault().setStatusText(d1);
                }
            }
        });

        TopComponent tc = WindowManager
                .getDefault()
                .findTopComponent("PretrazivacTopComponent");
        ((PretrazivacTopComponent) tc).aktivnaFirmaRadniciZaDatum(datum);
    }
}
