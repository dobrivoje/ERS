/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servis.manager.izvestaji.akcije;

import ent.Kompanija;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import node_klase.interfejsi.actionable.IActionableKompanija;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.WindowManager;

@ActionID(
        category = "Menu/Akcije/Kompanija",
        id = "pretrazivac.akcije.ActionKompanijaRefresh")
@ActionRegistration(
        displayName = "#CTL_ActionKompanijaRefresh")
@ActionReference(path = "Menu/Akcije/Kompanija", position = 3000, separatorBefore = 2990, separatorAfter = 3010)
@Messages("CTL_ActionKompanijaRefresh=Sinhro. podataka Firme")
public final class ActionKompanijaRefresh implements ActionListener {

    private final IActionableKompanija context;
    //
    private Lookup.Result<Kompanija> resultKompanija;
    private Kompanija kompanijaLookup;

    public ActionKompanijaRefresh(IActionableKompanija context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        resultKompanija = WindowManager
                .getDefault()
                .findTopComponent("PretrazivacTopComponent")
                .getLookup()
                .lookupResult(Kompanija.class);

        resultKompanija.addLookupListener(new LookupListener() {
            @Override
            public void resultChanged(LookupEvent le) {
                Lookup.Result lr = (Lookup.Result) le.getSource();
                Collection<Kompanija> kompanije = lr.allInstances();

                for (Kompanija k1 : kompanije) {
                    kompanijaLookup = k1;
                }
            }
        });
    }
}
