/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package radionica.akcije;

import com.dobrivoje.utilities.TopCompoment.TopComponentUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import node_klase.interfejsi.actionable.IActionableRadnik;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import radionica.UI.AktivniRadniciTopComponent;

@ActionID(
        category = "Menu/Akcije/Radnik",
        id = "akcije.AktivniRadniciRefreshAction")
@ActionRegistration(
        iconBase = "ikonice/refresh/crvena.gif",
        displayName = "#CTL_AktivniRadniciRefreshAction")
@ActionReferences({
    @ActionReference(path = "Menu/Akcije/Radnik", position = 3240, separatorBefore = 3230, separatorAfter = 3241),
    @ActionReference(path = "Toolbars/b1_Refresh", position = 42000)
})
@Messages("CTL_AktivniRadniciRefreshAction=Aktivni Radnici")
public final class AktivniRadniciRefreshAction implements ActionListener {

    private final IActionableRadnik context;

    public AktivniRadniciRefreshAction(IActionableRadnik context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {

        TopComponent tc = WindowManager
                .getDefault()
                .findTopComponent("AktivniRadniciTopComponent");
        ((AktivniRadniciTopComponent) tc).refreshAktivniRadnici();
        
        TopComponentUtils.OpenTopComponent("AktivniRadniciTopComponent");
    }
}
