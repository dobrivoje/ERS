/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servis.manager.akcije;

import com.dobrivoje.utilities.TopCompoment.TopComponentUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import node_klase.interfejsi.actionable.IActionableOrgJed;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Menu/Akcije/Organizaciona Jedinica",
        id = "servis.manager.akcije.Management_ORGJED"
)
@ActionRegistration(
        iconBase = "ikonice/izvestaji/crna.gif",
        displayName = "#CTL_Management_ORGJED"
)
@ActionReferences({
    @ActionReference(
            path = "Menu/Akcije/Organizaciona Jedinica/Management",
            position = 4100, separatorBefore = 4090, separatorAfter = 4110)
})
@Messages("CTL_Management_ORGJED=Management Podataka")
public final class Management_ORGJED implements ActionListener {

    private final IActionableOrgJed context;

    public Management_ORGJED(IActionableOrgJed context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TopComponentUtils.OpenTopComponent("ManagementPodatakaTopComponent");
    }
}
