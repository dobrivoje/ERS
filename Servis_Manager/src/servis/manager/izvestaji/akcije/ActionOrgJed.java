/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servis.manager.izvestaji.akcije;

import com.dobrivoje.utilities.TopCompoment.TopComponentUtils;
import ent.Orgjed;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import node_klase.interfejsi.actionable.IActionableOrgJed;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.awt.StatusDisplayer;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.WindowManager;

@ActionID(
        category = "Menu/Akcije/Organizaciona Jedinica",
        id = "pretrazivac.ActionOrgJed")
@ActionRegistration(
        iconBase = "ikonice/izvestaji/plava.gif",
        displayName = "#CTL_ActionOrgJed")
@ActionReferences({
    @ActionReference(path = "Menu/Akcije/Organizaciona Jedinica/Izveštaji", position = 3300),
    @ActionReference(path = "Toolbars/a1_Izvestaji", position = 31000)
})
@Messages("CTL_ActionOrgJed=Izveštaji - Org. Jedinica")
public final class ActionOrgJed implements ActionListener {

    private final IActionableOrgJed context;
    private Lookup.Result<Orgjed> result = null;

    public ActionOrgJed(IActionableOrgJed context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        result = WindowManager
                .getDefault()
                .findTopComponent("PretrazivacTopComponent")
                .getLookup()
                .lookupResult(Orgjed.class);

        result.addLookupListener(new LookupListener() {
            @Override
            public void resultChanged(LookupEvent le) {
                Lookup.Result result = (Lookup.Result) le.getSource();
                Collection<Orgjed> orgJedinice = result.allInstances();

                if (!orgJedinice.isEmpty()) {
                    for (Orgjed oj : orgJedinice) {
                        StatusDisplayer.getDefault().setStatusText(oj.getNaziv());
                    }
                }
            }
        });

        TopComponentUtils.OpenTopComponent("ManagementPodatakaTopComponent");
    }
}
