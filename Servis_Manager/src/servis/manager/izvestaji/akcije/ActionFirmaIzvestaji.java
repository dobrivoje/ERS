/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servis.manager.izvestaji.akcije;

import com.dobrivoje.utilities.TopCompoment.TopComponentUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import node_klase.interfejsi.actionable.IActionableFirma;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Menu/Akcije/Firma",
        id = "pretrazivac.akcije.FirmaIzvestaji")
@ActionRegistration(
        iconBase = "ikonice/izvestaji/crna.gif",
        displayName = "#CTL_ActionFirmaIzvestaji")
@ActionReferences({
    @ActionReference(path = "Menu/Akcije/Firma", position = 3200, separatorBefore = 3190, separatorAfter = 3210),
    @ActionReference(path = "Toolbars/a1_Izvestaji", position = 30000)
})
@Messages("CTL_ActionFirmaIzvestaji=Izveštaji za Firmu")
public final class ActionFirmaIzvestaji implements ActionListener {

    private final IActionableFirma context;

    public ActionFirmaIzvestaji(IActionableFirma context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        TopComponentUtils.OpenTopComponent("ManagementPodatakaTopComponent");
    }
}
