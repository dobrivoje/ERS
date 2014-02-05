/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package radionica.akcije;

import static com.dobrivoje.utilities.TopCompoment.TopComponentUtils.OpenTopComponent;
import ent.Radnik;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import node_klase.interfejsi.actionable.IActionableRadnik;
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
        category = "Menu/Akcije/Radnik",
        id = "pretrazivac.ActionRadnik")
@ActionRegistration(
        iconBase = "ikonice/izvestaji/crvena.gif",
        displayName = "#CTL_ActionRadnik")
@ActionReferences({
    @ActionReference(path = "Menu/Akcije/Radnik", position = 3400, separatorBefore = 3390, separatorAfter = 3410),
    @ActionReference(path = "Toolbars/a1_Izvestaji", position = 32000)
})
@Messages("CTL_ActionRadnik=Izveštaji")
public final class ActionRadnik implements ActionListener {

    private final IActionableRadnik context;

    private Lookup.Result<Radnik> result = null;

    public ActionRadnik(IActionableRadnik context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        result = WindowManager
                .getDefault()
                .findTopComponent("PretrazivacTopComponent")
                .getLookup()
                .lookupResult(Radnik.class);

        result.addLookupListener(new LookupListener() {
            @Override
            public void resultChanged(LookupEvent le) {
                Lookup.Result r = (Lookup.Result) le.getSource();
                Collection<Radnik> radnici = r.allInstances();

                if (!radnici.isEmpty()) {
                    for (Radnik r1 : radnici) {
                        StatusDisplayer.getDefault().setStatusText(
                                r1.getIme()
                                + " "
                                + r1.getPrezime());
                    }
                }
            }
        });

        OpenTopComponent("IzvestajiTopComponent");
    }
}
